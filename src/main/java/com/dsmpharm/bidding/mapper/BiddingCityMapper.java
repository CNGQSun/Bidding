package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingCity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
public interface BiddingCityMapper extends Mapper<BiddingCity> {
    List<BiddingCity> selectAllByName(@Param("proId") String proId);
}