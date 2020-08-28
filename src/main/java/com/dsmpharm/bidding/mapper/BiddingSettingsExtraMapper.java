package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingSettingsExtra;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/08/08
 */
public interface BiddingSettingsExtraMapper extends Mapper<BiddingSettingsExtra> {
    int updateByPhaseId(@Param("id") String id);

    List<Map> selectByProjectId(@Param("projectId") String projectId);
}