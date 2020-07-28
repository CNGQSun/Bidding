package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingUserFramework;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
public interface BiddingUserFrameworkMapper extends Mapper<BiddingUserFramework> {
    /**
     * 查询所有未删除的架构关系
     * @return
     */
    List<Map> selectAllNoDel(@Param("userId") String userId, @Param("parentId") String parentId, @Param("graParentId") String graParentId);

    Map selectById(@Param("id") String id);
}