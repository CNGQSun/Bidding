package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingUserFrameworkMapper;
import com.dsmpharm.bidding.pojo.BiddingUserFramework;
import com.dsmpharm.bidding.utils.IdWorker;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
@Service
public class BiddingUserFrameworkService {

	@Resource
	private BiddingUserFrameworkMapper biddingUserFrameworkMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingUserFramework biddingUserFramework){
		biddingUserFramework.setId(idWorker.nextId() + "");
		biddingUserFrameworkMapper.insert(biddingUserFramework);
	}

	public List<BiddingUserFramework> selectAll() {
		return biddingUserFrameworkMapper.selectAll();
	}

	public BiddingUserFramework findById(String id){
		return biddingUserFrameworkMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingUserFramework biddingUserFramework) {
		biddingUserFrameworkMapper.updateByPrimaryKey(biddingUserFramework);
	}

	public void delete(String id) {
		biddingUserFrameworkMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingUserFramework> list(BiddingUserFramework biddingUserFramework) {
		return biddingUserFrameworkMapper.select(biddingUserFramework);
	}

    public List<BiddingUserFramework> list(BiddingUserFramework biddingUserFramework, int currentPage, int pageSize) {
		biddingUserFrameworkMapper.selectCount(biddingUserFramework);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingUserFrameworkMapper.selectByRowBounds(biddingUserFramework, bounds);
    }

	public Integer selectCount(BiddingUserFramework biddingUserFramework){
		return biddingUserFrameworkMapper.selectCount(biddingUserFramework);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}