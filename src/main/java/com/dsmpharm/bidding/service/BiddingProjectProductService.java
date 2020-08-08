package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingProjectProductMapper;
import com.dsmpharm.bidding.pojo.BiddingProjectProduct;
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
public class BiddingProjectProductService {

	@Resource
	private BiddingProjectProductMapper biddingProjectProductMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingProjectProduct biddingProjectProduct){
		biddingProjectProduct.setId(idWorker.nextId() + "");
		biddingProjectProductMapper.insert(biddingProjectProduct);
	}

	public List<BiddingProjectProduct> selectAll() {
		return biddingProjectProductMapper.selectAll();
	}

	public BiddingProjectProduct findById(String id){
		return biddingProjectProductMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingProjectProduct biddingProjectProduct) {
		biddingProjectProductMapper.updateByPrimaryKey(biddingProjectProduct);
	}

	public void delete(String id) {
		biddingProjectProductMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingProjectProduct> list(BiddingProjectProduct biddingProjectProduct) {
		return biddingProjectProductMapper.select(biddingProjectProduct);
	}

    public List<BiddingProjectProduct> list(BiddingProjectProduct biddingProjectProduct, int currentPage, int pageSize) {
		biddingProjectProductMapper.selectCount(biddingProjectProduct);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingProjectProductMapper.selectByRowBounds(biddingProjectProduct, bounds);
    }

	public Integer selectCount(BiddingProjectProduct biddingProjectProduct){
		return biddingProjectProductMapper.selectCount(biddingProjectProduct);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}