package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingContentBak;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/08/08
 */
public interface BiddingContentBakMapper extends Mapper<BiddingContentBak> {
    /**
     * 将内容设置的数据从内容设置表复制到内容设置备份表
     * @return
     */
    void copySetting(@Param("projectPhaseId") String projectPhaseId,@Param("versionNum") String versionNum);


    List<BiddingContentBak> selectByPhaseId(@Param("projectPhaseId") String projectPhaseId, @Param("versionNum") String versionNum);

    List<Map> selectByUpdate(@Param("projectPhaseId") String projectPhaseId, @Param("versionNum") String versionNum);
    List<Map> selectByUpdate1(@Param("projectPhaseId") String projectPhaseId, @Param("versionNum") String versionNum,@Param("projectId") String projectId);
}