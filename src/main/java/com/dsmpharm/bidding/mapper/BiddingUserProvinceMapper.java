package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingUserProvince;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
public interface BiddingUserProvinceMapper extends Mapper<BiddingUserProvince> {
    /**
     * 根据id查询用户省份信息
     * @param id
     * @return
     */
    Map selectById(@Param("id") String id);

    /**
     * 分页、条件查询所有用户省份信息
     * @param name
     * @param proId
     * @return
     */
    List<Map> selectList(@Param("name") String name, @Param("proId") String proId);

    /**
     * 根据userId查询用户所有省份
     * @param userId
     * @return
     */
    List<BiddingUserProvince> selectByUserId(@Param("userId") String userId);
}