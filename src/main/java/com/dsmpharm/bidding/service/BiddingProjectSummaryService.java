package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingProjectSummaryMapper;
import com.dsmpharm.bidding.pojo.BiddingProjectSummary;
import com.dsmpharm.bidding.utils.IdWorker;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/08
 */
@Service
public class BiddingProjectSummaryService {
	private static Logger log = LoggerFactory.getLogger(BiddingProjectSummaryService.class);

	@Resource
	private BiddingProjectSummaryMapper biddingProjectSummaryMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingProjectSummary biddingProjectSummary){
		biddingProjectSummary.setId(idWorker.nextId() + "");
		biddingProjectSummaryMapper.insert(biddingProjectSummary);
	}

	public List<BiddingProjectSummary> selectAll() {
		return biddingProjectSummaryMapper.selectAll();
	}

	public BiddingProjectSummary findById(String id){
		return biddingProjectSummaryMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingProjectSummary biddingProjectSummary) {
		biddingProjectSummaryMapper.updateByPrimaryKey(biddingProjectSummary);
	}

	public void delete(String id) {
		biddingProjectSummaryMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingProjectSummary> list(BiddingProjectSummary biddingProjectSummary) {
		return biddingProjectSummaryMapper.select(biddingProjectSummary);
	}

    public List<BiddingProjectSummary> list(BiddingProjectSummary biddingProjectSummary, int currentPage, int pageSize) {
		biddingProjectSummaryMapper.selectCount(biddingProjectSummary);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingProjectSummaryMapper.selectByRowBounds(biddingProjectSummary, bounds);
    }

	public Integer selectCount(BiddingProjectSummary biddingProjectSummary){
		return biddingProjectSummaryMapper.selectCount(biddingProjectSummary);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}