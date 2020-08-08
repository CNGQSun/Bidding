package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingStrategyAnalysisMapper;
import com.dsmpharm.bidding.pojo.BiddingStrategyAnalysis;
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
public class BiddingStrategyAnalysisService {

	@Resource
	private BiddingStrategyAnalysisMapper biddingStrategyAnalysisMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingStrategyAnalysis biddingStrategyAnalysis){
		biddingStrategyAnalysis.setId(idWorker.nextId() + "");
		biddingStrategyAnalysisMapper.insert(biddingStrategyAnalysis);
	}

	public List<BiddingStrategyAnalysis> selectAll() {
		return biddingStrategyAnalysisMapper.selectAll();
	}

	public BiddingStrategyAnalysis findById(String id){
		return biddingStrategyAnalysisMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingStrategyAnalysis biddingStrategyAnalysis) {
		biddingStrategyAnalysisMapper.updateByPrimaryKey(biddingStrategyAnalysis);
	}

	public void delete(String id) {
		biddingStrategyAnalysisMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingStrategyAnalysis> list(BiddingStrategyAnalysis biddingStrategyAnalysis) {
		return biddingStrategyAnalysisMapper.select(biddingStrategyAnalysis);
	}

    public List<BiddingStrategyAnalysis> list(BiddingStrategyAnalysis biddingStrategyAnalysis, int currentPage, int pageSize) {
		biddingStrategyAnalysisMapper.selectCount(biddingStrategyAnalysis);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingStrategyAnalysisMapper.selectByRowBounds(biddingStrategyAnalysis, bounds);
    }

	public Integer selectCount(BiddingStrategyAnalysis biddingStrategyAnalysis){
		return biddingStrategyAnalysisMapper.selectCount(biddingStrategyAnalysis);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}