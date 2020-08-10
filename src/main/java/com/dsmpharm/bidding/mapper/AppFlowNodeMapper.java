package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.AppFlowNode;
import com.dsmpharm.bidding.pojo.BiddingUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/** 
 * <br/>
 * Created by Grant on 2020/08/10
 */
public interface AppFlowNodeMapper extends Mapper<AppFlowNode> {
    /**
     * 查找下一个审批用户（招标专员）
     * @param flowId
     * @param currentNode
     * @return
     */
    BiddingUser selectAppUser(@Param("flowId") String flowId, @Param("currentNode")String currentNode);
    /**
     * 查找下一个审批用户（商务经理）
     * @param flowId
     * @param currentNode
     * @return
     */
    BiddingUser selectAppUserSw(@Param("flowId") String flowId, @Param("currentNode")String currentNode,@Param("userId") String userId);
}