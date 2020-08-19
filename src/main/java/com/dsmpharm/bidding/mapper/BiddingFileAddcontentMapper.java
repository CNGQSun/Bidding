package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingFileAddcontent;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/** 
 * <br/>
 * Created by Grant on 2020/08/15
 */
public interface BiddingFileAddcontentMapper extends Mapper<BiddingFileAddcontent> {
    /**
     * 项目编辑中接收新增的内容设置的文件类型
     * @param filePath
     * @return
     */
    Integer insertAddcontent(@Param("filePath") String filePath);
}