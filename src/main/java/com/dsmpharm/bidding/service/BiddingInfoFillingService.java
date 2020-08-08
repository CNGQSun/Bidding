package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingInfoFillingMapper;
import com.dsmpharm.bidding.pojo.BiddingInfoFilling;
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
public class BiddingInfoFillingService {

	@Resource
	private BiddingInfoFillingMapper biddingInfoFillingMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingInfoFilling biddingInfoFilling){
		biddingInfoFilling.setId(idWorker.nextId() + "");
		biddingInfoFillingMapper.insert(biddingInfoFilling);
	}

	public List<BiddingInfoFilling> selectAll() {
		return biddingInfoFillingMapper.selectAll();
	}

	public BiddingInfoFilling findById(String id){
		return biddingInfoFillingMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingInfoFilling biddingInfoFilling) {
		biddingInfoFillingMapper.updateByPrimaryKey(biddingInfoFilling);
	}

	public void delete(String id) {
		biddingInfoFillingMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingInfoFilling> list(BiddingInfoFilling biddingInfoFilling) {
		return biddingInfoFillingMapper.select(biddingInfoFilling);
	}

    public List<BiddingInfoFilling> list(BiddingInfoFilling biddingInfoFilling, int currentPage, int pageSize) {
		biddingInfoFillingMapper.selectCount(biddingInfoFilling);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingInfoFillingMapper.selectByRowBounds(biddingInfoFilling, bounds);
    }

	public Integer selectCount(BiddingInfoFilling biddingInfoFilling){
		return biddingInfoFillingMapper.selectCount(biddingInfoFilling);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}