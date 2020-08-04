package com.dsmpharm.bidding.mapper;

import com.dsmpharm.bidding.pojo.BiddingContentSettings;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/08/03
 */
public interface BiddingContentSettingsMapper extends Mapper<BiddingContentSettings> {
    /**
     * 根据ID查询内容设置详细信息
     * @param id
     * @return
     */
    Map selectByIdNoDel(@Param("id") String id);

    /**
     * 分页、条件查询所有内容设置
     * @param name
     * @param projectPhaseId
     * @return
     */
    List<Map> selectList(@Param("name") String name,@Param("projectPhaseId")String projectPhaseId);
}