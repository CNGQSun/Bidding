package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingDocInterpretationMapper;
import com.dsmpharm.bidding.pojo.BiddingDocInterpretation;
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
public class BiddingDocInterpretationService {

	@Resource
	private BiddingDocInterpretationMapper biddingDocInterpretationMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingDocInterpretation biddingDocInterpretation){
		biddingDocInterpretation.setId(idWorker.nextId() + "");
		biddingDocInterpretationMapper.insert(biddingDocInterpretation);
	}

	public List<BiddingDocInterpretation> selectAll() {
		return biddingDocInterpretationMapper.selectAll();
	}

	public BiddingDocInterpretation findById(String id){
		return biddingDocInterpretationMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingDocInterpretation biddingDocInterpretation) {
		biddingDocInterpretationMapper.updateByPrimaryKey(biddingDocInterpretation);
	}

	public void delete(String id) {
		biddingDocInterpretationMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingDocInterpretation> list(BiddingDocInterpretation biddingDocInterpretation) {
		return biddingDocInterpretationMapper.select(biddingDocInterpretation);
	}

    public List<BiddingDocInterpretation> list(BiddingDocInterpretation biddingDocInterpretation, int currentPage, int pageSize) {
		biddingDocInterpretationMapper.selectCount(biddingDocInterpretation);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingDocInterpretationMapper.selectByRowBounds(biddingDocInterpretation, bounds);
    }

	public Integer selectCount(BiddingDocInterpretation biddingDocInterpretation){
		return biddingDocInterpretationMapper.selectCount(biddingDocInterpretation);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}