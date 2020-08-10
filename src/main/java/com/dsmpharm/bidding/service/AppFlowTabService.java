package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.AppFlowTabMapper;
import com.dsmpharm.bidding.pojo.AppFlowTab;
import com.dsmpharm.bidding.utils.IdWorker;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/10
 */
@Service
public class AppFlowTabService {

	@Resource
	private AppFlowTabMapper appFlowTabMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(AppFlowTab appFlowTab){
		appFlowTab.setId(idWorker.nextId() + "");
		appFlowTabMapper.insert(appFlowTab);
	}

	public List<AppFlowTab> selectAll() {
		return appFlowTabMapper.selectAll();
	}

	public AppFlowTab findById(String id){
		return appFlowTabMapper.selectByPrimaryKey(id);
	}

	public void updateById(AppFlowTab appFlowTab) {
		appFlowTabMapper.updateByPrimaryKey(appFlowTab);
	}

	public void delete(String id) {
		appFlowTabMapper.deleteByPrimaryKey(id);
	}

	public List<AppFlowTab> list(AppFlowTab appFlowTab) {
		return appFlowTabMapper.select(appFlowTab);
	}

    public List<AppFlowTab> list(AppFlowTab appFlowTab, int currentPage, int pageSize) {
		appFlowTabMapper.selectCount(appFlowTab);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return appFlowTabMapper.selectByRowBounds(appFlowTab, bounds);
    }

	public Integer selectCount(AppFlowTab appFlowTab){
		return appFlowTabMapper.selectCount(appFlowTab);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}