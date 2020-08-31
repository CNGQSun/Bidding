package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.entity.BiddingDocInterpretationDo;
import com.dsmpharm.bidding.entity.BiddingProjectBulidDo;
import com.dsmpharm.bidding.pojo.*;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/08/08
 */
public interface BiddingProjectMapper extends Mapper<BiddingProject> {
    /**
     * 查询所有登录用户创建的项目
     * @param name
     * @param status
     * @param userId
     * @return
     */
    List<Map> selectByNoDel(@Param("name") String name, @Param("status") String status,@Param("userId") String userId);

    /**
     * 查询所有登录用户创建的项目
     * @param name
     * @param status
     * @param userId
     * @return
     */
    List<Map> selectDealByNoDel(@Param("name") String name, @Param("status") String status,@Param("userId") String userId);

    /**
     * 查询build
     * @param projectId
     * @return
     */
    Map selectBuild(@Param("projectId") String projectId);
    /**
     * 查询content
     * @param projectId
     * @param projectPhaseId
     * @return
     */
    List<Map>  selectSetting(@Param("projectId") String projectId, @Param("projectPhaseId") String projectPhaseId);

    /**
     * 查询content_extra
     * @param projectId
     * @param projectBulidId
     * @return
     */
    List<Map> selectAddContent(@Param("projectId") String projectId, @Param("projectBulidId") String projectBulidId);

    /**
     * 查询文件解读
     * @param projectId
     * @return
     */
    Map selectInterpretation(@Param("projectId") String projectId);

    /**
     * 查询竞品收集
     * @param projectId
     * @return
     */
    Map selectProCollection(@Param("projectId") String projectId);

    /**
     * 查询策略分析
     * @param projectId
     * @return
     */
    Map selectStrategyAnalysis(@Param("projectId") String projectId);

    /**
     * 查询信息填报
     * @param projectId
     * @return
     */
    Map selectInfoFilling(@Param("projectId") String projectId);

    /**
     * 官方公告
     * @param projectId
     * @return
     */
    Map selectOfficialNotice(@Param("projectId")String projectId);

    /**
     * 项目总结
     * @param projectId
     * @return
     */
    Map selectProjectSummary(@Param("projectId")String projectId);

    BiddingProjectBulid selectGoStatus1(@Param("projectId") String projectId);
    BiddingDocInterpretation selectGoStatus2(@Param("projectId")String projectId);
    BiddingProductCollection selectGoStatus3(@Param("projectId")String projectId);
    BiddingStrategyAnalysis selectGoStatus4(@Param("projectId")String projectId);
    BiddingInfoFilling selectGoStatus5(@Param("projectId")String projectId);
    BiddingOfficialNotice selectGoStatus6(@Param("projectId")String projectId);
    BiddingProjectSummary selectGoStatus7(@Param("projectId")String projectId);

    /**
     *
     * @param userId
     * @return
     */
    List<String> selectAllPro(@Param("userId") String userId);

    List<Map> selectByDatebase(@Param("proId") String proId, @Param("cityId")String cityId,@Param("name")String name,@Param("proLabel")String proLabel,@Param("typeId")String typeId,@Param("startTime")Integer startTime,@Param("endTime")Integer endTime);

    List<Map> selectByAppeal(@Param("qualityLevel") String qualityLevel, @Param("proId") String proId, @Param("productId") String productId);

    /**
     * 查询立项
     * @param projectId
     * @return
     */
    List<BiddingProjectBulidDo> selectBuildList(@Param("projectId") String projectId);
    /**
     * 查询文件解读
     * @param projectId
     * @return
     */
    List<BiddingDocInterpretationDo> selectInterpretationList(@Param("projectId") String projectId);

    /**
     * 查询竞品收集
     * @param projectId
     * @return
     */
    List<BiddingProductCollection> selectProCollectionList(@Param("projectId") String projectId);

    /**
     * 查询策略分析
     * @param projectId
     * @return
     */
    List<BiddingStrategyAnalysis> selectStrategyAnalysisList(@Param("projectId") String projectId);

    /**
     * 查询信息填报
     * @param projectId
     * @return
     */
    List<BiddingInfoFilling> selectInfoFillingList(@Param("projectId") String projectId);

    /**
     * 官方公告
     * @param projectId
     * @return
     */
    List<BiddingOfficialNotice> selectOfficialNoticeList(@Param("projectId")String projectId);

    /**
     * 项目总结
     * @param projectId
     * @return
     */
    List<BiddingProjectSummary> selectProjectSummaryList(@Param("projectId")String projectId);
}