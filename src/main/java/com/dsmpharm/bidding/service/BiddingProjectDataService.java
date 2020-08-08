package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingProjectDataMapper;
import com.dsmpharm.bidding.pojo.BiddingProjectData;
import com.dsmpharm.bidding.utils.IdWorker;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/08
 */
@Service
public class BiddingProjectDataService {

	@Resource
	private BiddingProjectDataMapper biddingProjectDataMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingProjectData biddingProjectData){
		biddingProjectData.setId(idWorker.nextId() + "");
		biddingProjectDataMapper.insert(biddingProjectData);
	}

	public List<BiddingProjectData> selectAll() {
		return biddingProjectDataMapper.selectAll();
	}

	public BiddingProjectData findById(String id){
		return biddingProjectDataMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingProjectData biddingProjectData) {
		biddingProjectDataMapper.updateByPrimaryKey(biddingProjectData);
	}

	public void delete(String id) {
		biddingProjectDataMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingProjectData> list(BiddingProjectData biddingProjectData) {
		return biddingProjectDataMapper.select(biddingProjectData);
	}

    public List<BiddingProjectData> list(BiddingProjectData biddingProjectData, int currentPage, int pageSize) {
		biddingProjectDataMapper.selectCount(biddingProjectData);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingProjectDataMapper.selectByRowBounds(biddingProjectData, bounds);
    }

	public Integer selectCount(BiddingProjectData biddingProjectData){
		return biddingProjectDataMapper.selectCount(biddingProjectData);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}