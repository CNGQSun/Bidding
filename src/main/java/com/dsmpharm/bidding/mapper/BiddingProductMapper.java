package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingProduct;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
public interface BiddingProductMapper extends Mapper<BiddingProduct> {

    List<BiddingProduct> selectAllByNoDel(@Param("name") String name);
}