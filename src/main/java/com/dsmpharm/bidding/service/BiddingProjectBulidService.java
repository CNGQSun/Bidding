package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingProjectBulidMapper;
import com.dsmpharm.bidding.pojo.BiddingProjectBulid;
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
public class BiddingProjectBulidService {

	@Resource
	private BiddingProjectBulidMapper biddingProjectBulidMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingProjectBulid biddingProjectBulid){
		biddingProjectBulid.setId(idWorker.nextId() + "");
		biddingProjectBulidMapper.insert(biddingProjectBulid);
	}

	public List<BiddingProjectBulid> selectAll() {
		return biddingProjectBulidMapper.selectAll();
	}

	public BiddingProjectBulid findById(String id){
		return biddingProjectBulidMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingProjectBulid biddingProjectBulid) {
		biddingProjectBulidMapper.updateByPrimaryKey(biddingProjectBulid);
	}

	public void delete(String id) {
		biddingProjectBulidMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingProjectBulid> list(BiddingProjectBulid biddingProjectBulid) {
		return biddingProjectBulidMapper.select(biddingProjectBulid);
	}

    public List<BiddingProjectBulid> list(BiddingProjectBulid biddingProjectBulid, int currentPage, int pageSize) {
		biddingProjectBulidMapper.selectCount(biddingProjectBulid);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingProjectBulidMapper.selectByRowBounds(biddingProjectBulid, bounds);
    }

	public Integer selectCount(BiddingProjectBulid biddingProjectBulid){
		return biddingProjectBulidMapper.selectCount(biddingProjectBulid);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}