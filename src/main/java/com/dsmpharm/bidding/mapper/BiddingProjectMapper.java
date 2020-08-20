package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingProject;
import com.dsmpharm.bidding.pojo.BiddingProjectBulid;
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
    BiddingProjectBulid selectGoStatus2(@Param("projectId")String projectId);
    BiddingProjectBulid selectGoStatus3(@Param("projectId")String projectId);
    BiddingProjectBulid selectGoStatus4(@Param("projectId")String projectId);
    BiddingProjectBulid selectGoStatus5(@Param("projectId")String projectId);
    BiddingProjectBulid selectGoStatus6(@Param("projectId")String projectId);
    BiddingProjectBulid selectGoStatus7(@Param("projectId")String projectId);

    /**
     *
     * @param userId
     * @return
     */
    List<String> selectAllPro(@Param("userId") String userId);
}