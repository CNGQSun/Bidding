package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.AppFlowNodeMapper;
import com.dsmpharm.bidding.pojo.AppFlowNode;
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
public class AppFlowNodeService {
	private static Logger log = LoggerFactory.getLogger(AppFlowNodeService.class);

	@Resource
	private AppFlowNodeMapper appFlowNodeMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(AppFlowNode appFlowNode){
		appFlowNode.setId(idWorker.nextId() + "");
		appFlowNodeMapper.insert(appFlowNode);
	}

	public List<AppFlowNode> selectAll() {
		return appFlowNodeMapper.selectAll();
	}

	public AppFlowNode findById(String id){
		return appFlowNodeMapper.selectByPrimaryKey(id);
	}

	public void updateById(AppFlowNode appFlowNode) {
		appFlowNodeMapper.updateByPrimaryKey(appFlowNode);
	}

	public void delete(String id) {
		appFlowNodeMapper.deleteByPrimaryKey(id);
	}

	public List<AppFlowNode> list(AppFlowNode appFlowNode) {
		return appFlowNodeMapper.select(appFlowNode);
	}

    public List<AppFlowNode> list(AppFlowNode appFlowNode, int currentPage, int pageSize) {
		appFlowNodeMapper.selectCount(appFlowNode);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return appFlowNodeMapper.selectByRowBounds(appFlowNode, bounds);
    }

	public Integer selectCount(AppFlowNode appFlowNode){
		return appFlowNodeMapper.selectCount(appFlowNode);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}