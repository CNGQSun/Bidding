package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingSettingsExtra;
import tk.mybatis.mapper.common.Mapper;

/** 
 * <br/>
 * Created by Grant on 2020/08/08
 */
public interface BiddingSettingsExtraMapper extends Mapper<BiddingSettingsExtra> {
    int updateByPhaseId(String id);
}