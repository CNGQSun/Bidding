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

    /**
     * 查看大区经理是否空岗
     * @param graParentId
     * @return
     */
    List<BiddingUserFramework> selectByGraParent(@Param("graParentId") String graParentId);
    /**
     * 查看商务经理是否空岗
     * @param parentId
     * @return
     */
    List<BiddingUserFramework> selectByParent(@Param("parentId") String parentId);

    /**
     * 根据userId查询用户所在省份
     * @param userId
     * @return
     */
    List<BiddingUserFramework> selectByUserId(@Param("userId") String userId);
}