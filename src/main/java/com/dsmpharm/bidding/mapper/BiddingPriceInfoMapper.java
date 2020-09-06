package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingPriceInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

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
    List<Map> selectByproductEn(@Param("productEnName") String productEnName);

    /**
     * 清空备份表
     * @return
     */
    int createBak();

    /**
     * 将数据插入备份表
     * @param biddingPriceInfo
     * @return
     */
     void insertBak(@Param("biddingPriceInfo") BiddingPriceInfo biddingPriceInfo);

    /**
     * 将数据从备份表拷贝到主表
     * @return
     */
     void copyData();

    int updateAll();

    List<BiddingPriceInfo> selectAllByNoDel();

    int selectCountNum();

    void deleteCountNum();
}