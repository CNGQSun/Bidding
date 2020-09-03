package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingProvince;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
public interface BiddingProvinceMapper extends Mapper<BiddingProvince> {
    List<BiddingProvince> selectAllNoDel();

    /**
     * 根据省份名 模糊查询省份信息
     * @param proName
     * @return
     */
    BiddingProvince selectByProName(@Param("proName") String proName);
}