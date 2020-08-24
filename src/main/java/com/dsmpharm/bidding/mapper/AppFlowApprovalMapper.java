package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.AppFlowApproval;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/** 
 * <br/>
 * Created by Grant on 2020/08/10
 */
public interface AppFlowApprovalMapper extends Mapper<AppFlowApproval> {
    //更新审批数据，仅保留一条
    int updateApp(@Param("userId") String userId, @Param("projectId") String projectId);
}