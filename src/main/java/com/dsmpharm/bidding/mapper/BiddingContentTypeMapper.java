package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingContentType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/03
 */
public interface BiddingContentTypeMapper extends Mapper<BiddingContentType> {
    /**
     * 查询全部内容类型
     * @return
     */
    List<BiddingContentType> selectAllNoDel();
}