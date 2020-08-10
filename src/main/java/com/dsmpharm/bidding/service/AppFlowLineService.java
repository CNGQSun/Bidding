package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.AppFlowLineMapper;
import com.dsmpharm.bidding.pojo.AppFlowLine;
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
public class AppFlowLineService {

	@Resource
	private AppFlowLineMapper appFlowLineMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(AppFlowLine appFlowLine){
		appFlowLine.setId(idWorker.nextId() + "");
		appFlowLineMapper.insert(appFlowLine);
	}

	public List<AppFlowLine> selectAll() {
		return appFlowLineMapper.selectAll();
	}

	public AppFlowLine findById(String id){
		return appFlowLineMapper.selectByPrimaryKey(id);
	}

	public void updateById(AppFlowLine appFlowLine) {
		appFlowLineMapper.updateByPrimaryKey(appFlowLine);
	}

	public void delete(String id) {
		appFlowLineMapper.deleteByPrimaryKey(id);
	}

	public List<AppFlowLine> list(AppFlowLine appFlowLine) {
		return appFlowLineMapper.select(appFlowLine);
	}

    public List<AppFlowLine> list(AppFlowLine appFlowLine, int currentPage, int pageSize) {
		appFlowLineMapper.selectCount(appFlowLine);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return appFlowLineMapper.selectByRowBounds(appFlowLine, bounds);
    }

	public Integer selectCount(AppFlowLine appFlowLine){
		return appFlowLineMapper.selectCount(appFlowLine);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}