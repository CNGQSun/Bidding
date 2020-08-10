package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.AppFlowApprovalMapper;
import com.dsmpharm.bidding.pojo.AppFlowApproval;
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
public class AppFlowApprovalService {

	@Resource
	private AppFlowApprovalMapper appFlowApprovalMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(AppFlowApproval appFlowApproval){
		appFlowApproval.setId(idWorker.nextId() + "");
		appFlowApprovalMapper.insert(appFlowApproval);
	}

	public List<AppFlowApproval> selectAll() {
		return appFlowApprovalMapper.selectAll();
	}

	public AppFlowApproval findById(String id){
		return appFlowApprovalMapper.selectByPrimaryKey(id);
	}

	public void updateById(AppFlowApproval appFlowApproval) {
		appFlowApprovalMapper.updateByPrimaryKey(appFlowApproval);
	}

	public void delete(String id) {
		appFlowApprovalMapper.deleteByPrimaryKey(id);
	}

	public List<AppFlowApproval> list(AppFlowApproval appFlowApproval) {
		return appFlowApprovalMapper.select(appFlowApproval);
	}

    public List<AppFlowApproval> list(AppFlowApproval appFlowApproval, int currentPage, int pageSize) {
		appFlowApprovalMapper.selectCount(appFlowApproval);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return appFlowApprovalMapper.selectByRowBounds(appFlowApproval, bounds);
    }

	public Integer selectCount(AppFlowApproval appFlowApproval){
		return appFlowApprovalMapper.selectCount(appFlowApproval);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}