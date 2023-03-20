package io.metersphere.base.mapper.ext;

import io.metersphere.dto.CountMapDTO;
import io.metersphere.dto.TestCaseNodeDTO;
import io.metersphere.dto.TestReviewCaseDTO;
import io.metersphere.request.testreview.QueryCaseReviewRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ExtTestReviewCaseMapper {

    List<TestReviewCaseDTO> list(@Param("request") QueryCaseReviewRequest request);

    List<String> getStatusByReviewId(String reviewId);

    List<String> findRelateTestReviewId(@Param("userId") String userId, @Param("workspaceId") String workspaceId, @Param("projectId") String projectId);

    /**
     * 获取 TestReviewTestCase 详细信息
     *
     * @param id TestReviewTestCase id
     * @return TestReviewTestCase 详细信息
     */
    TestReviewCaseDTO get(@Param("id") String id);

    List<String> selectIds(@Param("request") QueryCaseReviewRequest request);

    List<String> selectTestCaseIds(@Param("request") QueryCaseReviewRequest request);

    List<TestReviewCaseDTO> listForMinder(@Param("request") QueryCaseReviewRequest request);

    List<String> selectReviewIds();

    List<String> getIdsOrderByUpdateTime(@Param("reviewId") String reviewId);

    Long getPreOrder(@Param("reviewId") String reviewId, @Param("baseOrder") Long baseOrder);

    Long getLastOrder(@Param("reviewId") String reviewId, @Param("baseOrder") Long baseOrder);

    List<CountMapDTO> getStatusMapByReviewId(@Param("reviewId") String reviewId);

    List<String> projectIdsByPlanId(@Param("reviewId") String reviewId);

    List<TestCaseNodeDTO> getTestReviewCountNodes(@Param("request") QueryCaseReviewRequest request);
}
