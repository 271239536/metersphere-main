package io.metersphere.notice;

import io.metersphere.base.domain.LoadTestReport;
import io.metersphere.base.domain.LoadTestReportWithBLOBs;
import io.metersphere.base.domain.Project;
import io.metersphere.base.mapper.LoadTestReportMapper;
import io.metersphere.base.mapper.ProjectMapper;
import io.metersphere.base.mapper.ext.ExtLoadTestReportMapper;
import io.metersphere.commons.constants.ReportKeys;
import io.metersphere.commons.exception.MSException;
import io.metersphere.commons.utils.CommonBeanFactory;
import io.metersphere.commons.utils.LogUtil;
import io.metersphere.consumer.LoadTestFinishEvent;
import io.metersphere.dto.VumProcessedStatus;
import io.metersphere.quota.service.BaseQuotaService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author lyh
 */

@Component
public class LoadTestVumEvent implements LoadTestFinishEvent {
    @Resource
    private LoadTestReportMapper loadTestReportMapper;
    @Resource
    private ExtLoadTestReportMapper extLoadTestReportMapper;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private BaseQuotaService baseQuotaService;

    private void handleVum(LoadTestReport report) {
        if (report == null) {
            return;
        }

        LoadTestReportWithBLOBs testReport = loadTestReportMapper.selectByPrimaryKey(report.getId());
        if (testReport == null) {
            return;
        }

        int bl = extLoadTestReportMapper.updateReportVumStatus(report.getId(),
                ReportKeys.VumProcessedStatus.name(),
                VumProcessedStatus.PROCESSED,
                VumProcessedStatus.NOT_PROCESSED);

        // 防止重复处理
        if (bl == 0) {
            return;
        }


        String projectId = report.getProjectId();
        Project project = projectMapper.selectByPrimaryKey(projectId);
        if (project == null || StringUtils.isBlank(project.getWorkspaceId())) {
            MSException.throwException("project is null or workspace_id of project is null. project id: " + projectId);
        }
        RLock lock = redissonClient.getLock(project.getWorkspaceId());
        try {
            lock.lock();
            BigDecimal toReduceVum = baseQuotaService.getReduceVumUsed(testReport);
            if (toReduceVum.compareTo(BigDecimal.ZERO) != 0) {
                baseQuotaService.updateVumUsed(projectId, toReduceVum.negate());
            }
        } finally {
            lock.unlock();
        }
    }

    public void execute(LoadTestReport report) {
        // 根据报告结束时间修正vum值
        this.handleVum(report);
    }
}
