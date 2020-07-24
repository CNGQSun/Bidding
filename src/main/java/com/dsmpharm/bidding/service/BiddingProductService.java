package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingProductMapper;
import com.dsmpharm.bidding.pojo.BiddingProduct;
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
public class BiddingProductService {

	@Resource
	private BiddingProductMapper biddingProductMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingProduct biddingProduct){
		biddingProduct.setId(idWorker.nextId() + "");
		biddingProductMapper.insert(biddingProduct);
	}

	public List<BiddingProduct> selectAll() {
		return biddingProductMapper.selectAll();
	}

	public BiddingProduct findById(String id){
		return biddingProductMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingProduct biddingProduct) {
		biddingProductMapper.updateByPrimaryKey(biddingProduct);
	}

	public void delete(String id) {
		biddingProductMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingProduct> list(BiddingProduct biddingProduct) {
		return biddingProductMapper.select(biddingProduct);
	}

    public List<BiddingProduct> list(BiddingProduct biddingProduct, int currentPage, int pageSize) {
		biddingProductMapper.selectCount(biddingProduct);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingProductMapper.selectByRowBounds(biddingProduct, bounds);
    }

	public Integer selectCount(BiddingProduct biddingProduct){
		return biddingProductMapper.selectCount(biddingProduct);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}