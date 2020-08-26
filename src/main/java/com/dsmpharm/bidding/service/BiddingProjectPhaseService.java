package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingProjectPhaseMapper;
import com.dsmpharm.bidding.pojo.BiddingProjectPhase;
import com.dsmpharm.bidding.utils.IdWorker;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/03
 */
@Service
public class BiddingProjectPhaseService {
	private static Logger log = LoggerFactory.getLogger(BiddingProjectPhaseService.class);

	@Resource
	private BiddingProjectPhaseMapper biddingProjectPhaseMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingProjectPhase biddingProjectPhase){
		biddingProjectPhase.setId(idWorker.nextId() + "");
		biddingProjectPhaseMapper.insert(biddingProjectPhase);
	}

	public List<BiddingProjectPhase> selectAll() {
		return biddingProjectPhaseMapper.selectAll();
	}

	public BiddingProjectPhase findById(String id){
		return biddingProjectPhaseMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingProjectPhase biddingProjectPhase) {
		biddingProjectPhaseMapper.updateByPrimaryKey(biddingProjectPhase);
	}

	public void delete(String id) {
		biddingProjectPhaseMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingProjectPhase> list(BiddingProjectPhase biddingProjectPhase) {
		return biddingProjectPhaseMapper.select(biddingProjectPhase);
	}

    public List<BiddingProjectPhase> list(BiddingProjectPhase biddingProjectPhase, int currentPage, int pageSize) {
		biddingProjectPhaseMapper.selectCount(biddingProjectPhase);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingProjectPhaseMapper.selectByRowBounds(biddingProjectPhase, bounds);
    }

	public Integer selectCount(BiddingProjectPhase biddingProjectPhase){
		return biddingProjectPhaseMapper.selectCount(biddingProjectPhase);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}