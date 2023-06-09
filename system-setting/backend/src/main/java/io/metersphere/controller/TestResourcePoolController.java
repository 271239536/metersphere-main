package io.metersphere.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.metersphere.commons.constants.OperLogConstants;
import io.metersphere.commons.constants.OperLogModule;
import io.metersphere.commons.utils.PageUtils;
import io.metersphere.commons.utils.Pager;
import io.metersphere.consul.CacheNode;
import io.metersphere.dto.TestResourcePoolDTO;
import io.metersphere.dto.UpdatePoolDTO;
import io.metersphere.log.annotation.MsAuditLog;
import io.metersphere.request.resourcepool.QueryResourcePoolRequest;
import io.metersphere.service.TestResourcePoolService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

@RequestMapping("testresourcepool")
@RestController

public class TestResourcePoolController {

    @Resource
    private TestResourcePoolService testResourcePoolService;

    @PostMapping("/add")
    @MsAuditLog(module = OperLogModule.SYSTEM_TEST_RESOURCE, type = OperLogConstants.CREATE, content = "#msClass.getLogDetails(#testResourcePoolDTO.id)", msClass = TestResourcePoolService.class)
    @CacheNode // 把监控节点缓存起来
    public TestResourcePoolDTO addTestResourcePool(@RequestBody TestResourcePoolDTO testResourcePoolDTO) {
        return testResourcePoolService.addTestResourcePool(testResourcePoolDTO);
    }

    @GetMapping("/delete/{testResourcePoolId}")
    @MsAuditLog(module = OperLogModule.SYSTEM_TEST_RESOURCE, type = OperLogConstants.DELETE, beforeEvent = "#msClass.getLogDetails(#testResourcePoolId)", msClass = TestResourcePoolService.class)
    @CacheNode // 把监控节点缓存起来
    public void deleteTestResourcePool(@PathVariable(value = "testResourcePoolId") String testResourcePoolId) {
        testResourcePoolService.deleteTestResourcePool(testResourcePoolId);
    }

    @PostMapping("/update")
    @MsAuditLog(module = OperLogModule.SYSTEM_TEST_RESOURCE, type = OperLogConstants.UPDATE, beforeEvent = "#msClass.getLogDetails(#testResourcePoolDTO.id)", content = "#msClass.getLogDetails(#testResourcePoolDTO.id)", msClass = TestResourcePoolService.class)
    @CacheNode // 把监控节点缓存起来
    public void updateTestResourcePool(@RequestBody TestResourcePoolDTO testResourcePoolDTO) {
        testResourcePoolService.updateTestResourcePool(testResourcePoolDTO);
    }

    @GetMapping("/update/{poolId}/{status}")
    @MsAuditLog(module = OperLogModule.SYSTEM_TEST_RESOURCE, type = OperLogConstants.UPDATE, beforeEvent = "#msClass.getLogDetails(#poolId)", content = "#msClass.getLogDetails(#poolId)", msClass = TestResourcePoolService.class)
    @CacheNode // 把监控节点缓存起来
    public void updateTestResourcePoolStatus(@PathVariable String poolId, @PathVariable String status) {
        testResourcePoolService.updateTestResourcePoolStatus(poolId, status);
    }

    @GetMapping("/check/use/{poolId}")
    public UpdatePoolDTO checkHaveTestUsePool(@PathVariable String poolId) {
        return testResourcePoolService.checkHaveTestUsePool(poolId);
    }

    @PostMapping("list/{goPage}/{pageSize}")
    public Pager<List<TestResourcePoolDTO>> listResourcePools(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody QueryResourcePoolRequest request) {
        Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, testResourcePoolService.listResourcePools(request));
    }

    @GetMapping("/list/quota/ws/valid/{workspaceId}")
    public List<TestResourcePoolDTO> listWsValidQuotaResourcePools(@PathVariable String workspaceId) {
        return testResourcePoolService.listWsValidQuotaResourcePools(workspaceId);
    }

}
