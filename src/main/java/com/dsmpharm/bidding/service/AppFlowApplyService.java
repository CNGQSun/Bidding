package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.AppFlowApplyMapper;
import com.dsmpharm.bidding.pojo.AppFlowApply;
import com.dsmpharm.bidding.utils.IdWorker;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/10
 */
@Service
public class AppFlowApplyService {

	private static Logger log = LoggerFactory.getLogger(AppFlowApplyService.class);

	@Resource
	private AppFlowApplyMapper appFlowApplyMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(AppFlowApply appFlowApply){
		appFlowApply.setId(idWorker.nextId() + "");
		appFlowApplyMapper.insert(appFlowApply);
	}

	public List<AppFlowApply> selectAll() {
		return appFlowApplyMapper.selectAll();
	}

	public AppFlowApply findById(String id){
		return appFlowApplyMapper.selectByPrimaryKey(id);
	}

	public void updateById(AppFlowApply appFlowApply) {
		appFlowApplyMapper.updateByPrimaryKey(appFlowApply);
	}

	public void delete(String id) {
		appFlowApplyMapper.deleteByPrimaryKey(id);
	}

	public List<AppFlowApply> list(AppFlowApply appFlowApply) {
		return appFlowApplyMapper.select(appFlowApply);
	}

    public List<AppFlowApply> list(AppFlowApply appFlowApply, int currentPage, int pageSize) {
		appFlowApplyMapper.selectCount(appFlowApply);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return appFlowApplyMapper.selectByRowBounds(appFlowApply, bounds);
    }

	public Integer selectCount(AppFlowApply appFlowApply){
		return appFlowApplyMapper.selectCount(appFlowApply);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}