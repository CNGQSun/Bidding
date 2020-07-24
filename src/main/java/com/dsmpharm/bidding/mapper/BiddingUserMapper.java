package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
public interface BiddingUserMapper extends Mapper<BiddingUser> {

    /**
     * 查询所有未删除的用户
     * @param name
     * @param roleId
     * @return
     */
    List<Map> selectAllNoDel(@Param("name") String name,@Param("roleId") String roleId);
}