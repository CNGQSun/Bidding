package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingContentBakMapper;
import com.dsmpharm.bidding.pojo.BiddingContentBak;
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
public class BiddingContentBakService {
	private static Logger log = LoggerFactory.getLogger(BiddingContentBakService.class);

	@Resource
	private BiddingContentBakMapper biddingContentBakMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingContentBak biddingContentBak){
		biddingContentBak.setId(idWorker.nextId() + "");
		biddingContentBakMapper.insert(biddingContentBak);
	}

	public List<BiddingContentBak> selectAll() {
		return biddingContentBakMapper.selectAll();
	}

	public BiddingContentBak findById(String id){
		return biddingContentBakMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingContentBak biddingContentBak) {
		biddingContentBakMapper.updateByPrimaryKey(biddingContentBak);
	}

	public void delete(String id) {
		biddingContentBakMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingContentBak> list(BiddingContentBak biddingContentBak) {
		return biddingContentBakMapper.select(biddingContentBak);
	}

    public List<BiddingContentBak> list(BiddingContentBak biddingContentBak, int currentPage, int pageSize) {
		biddingContentBakMapper.selectCount(biddingContentBak);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingContentBakMapper.selectByRowBounds(biddingContentBak, bounds);
    }

	public Integer selectCount(BiddingContentBak biddingContentBak){
		return biddingContentBakMapper.selectCount(biddingContentBak);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}