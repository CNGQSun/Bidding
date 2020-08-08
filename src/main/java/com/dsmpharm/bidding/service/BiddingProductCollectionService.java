package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingProductCollectionMapper;
import com.dsmpharm.bidding.pojo.BiddingProductCollection;
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
public class BiddingProductCollectionService {

	@Resource
	private BiddingProductCollectionMapper biddingProductCollectionMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingProductCollection biddingProductCollection){
		biddingProductCollection.setId(idWorker.nextId() + "");
		biddingProductCollectionMapper.insert(biddingProductCollection);
	}

	public List<BiddingProductCollection> selectAll() {
		return biddingProductCollectionMapper.selectAll();
	}

	public BiddingProductCollection findById(String id){
		return biddingProductCollectionMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingProductCollection biddingProductCollection) {
		biddingProductCollectionMapper.updateByPrimaryKey(biddingProductCollection);
	}

	public void delete(String id) {
		biddingProductCollectionMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingProductCollection> list(BiddingProductCollection biddingProductCollection) {
		return biddingProductCollectionMapper.select(biddingProductCollection);
	}

    public List<BiddingProductCollection> list(BiddingProductCollection biddingProductCollection, int currentPage, int pageSize) {
		biddingProductCollectionMapper.selectCount(biddingProductCollection);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingProductCollectionMapper.selectByRowBounds(biddingProductCollection, bounds);
    }

	public Integer selectCount(BiddingProductCollection biddingProductCollection){
		return biddingProductCollectionMapper.selectCount(biddingProductCollection);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}