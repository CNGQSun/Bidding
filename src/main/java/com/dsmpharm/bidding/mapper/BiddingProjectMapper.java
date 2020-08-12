package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingProject;
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

}