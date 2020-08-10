package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingUserRole;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
public interface BiddingUserRoleMapper extends Mapper<BiddingUserRole> {
    /**
     * 根据userId查询其角色
     * @param userId
     * @return
     */
    BiddingUserRole selectByUserId(@Param("userId") String userId);
}