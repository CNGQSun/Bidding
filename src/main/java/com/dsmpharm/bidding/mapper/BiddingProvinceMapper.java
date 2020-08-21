package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingProvince;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
public interface BiddingProvinceMapper extends Mapper<BiddingProvince> {
    List<BiddingProvince> selectAllNoDel();
}