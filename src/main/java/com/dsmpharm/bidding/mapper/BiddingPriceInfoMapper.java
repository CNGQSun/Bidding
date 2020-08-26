package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingPriceInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/21
 */
public interface BiddingPriceInfoMapper extends Mapper<BiddingPriceInfo> {
    /**
     * 根据enName查询价格信息
     * @param enName
     * @return
     */
    List<BiddingPriceInfo> selectByProduct(@Param("enName") String enName);

    /**
     * 根据enName、proId查询价格信息
     * @param enName
     * @param proId
     * @return
     */
    List<BiddingPriceInfo> selectByProductPro(@Param("enName") String enName, @Param("proId")Integer proId);

    /**
     * 根据enName、proId查询省份信息
     * @param productEnName
     * @return
     */
    List<BiddingPriceInfo> selectByproductEn(@Param("productEnName") String productEnName);
}