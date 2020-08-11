package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingProjectType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/03
 */
public interface BiddingProjectTypeMapper extends Mapper<BiddingProjectType> {
    /**
     * 获取所有未删除的项目类型
     * @return
     */
    List<BiddingProjectType> selectNoDel();
}