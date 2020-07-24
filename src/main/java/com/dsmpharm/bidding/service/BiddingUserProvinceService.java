package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingUserProvinceMapper;
import com.dsmpharm.bidding.pojo.BiddingUserProvince;
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
public class BiddingUserProvinceService {

	@Resource
	private BiddingUserProvinceMapper biddingUserProvinceMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingUserProvince biddingUserProvince){
		biddingUserProvince.setId(idWorker.nextId() + "");
		biddingUserProvinceMapper.insert(biddingUserProvince);
	}

	public List<BiddingUserProvince> selectAll() {
		return biddingUserProvinceMapper.selectAll();
	}

	public BiddingUserProvince findById(String id){
		return biddingUserProvinceMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingUserProvince biddingUserProvince) {
		biddingUserProvinceMapper.updateByPrimaryKey(biddingUserProvince);
	}

	public void delete(String id) {
		biddingUserProvinceMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingUserProvince> list(BiddingUserProvince biddingUserProvince) {
		return biddingUserProvinceMapper.select(biddingUserProvince);
	}

    public List<BiddingUserProvince> list(BiddingUserProvince biddingUserProvince, int currentPage, int pageSize) {
		biddingUserProvinceMapper.selectCount(biddingUserProvince);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingUserProvinceMapper.selectByRowBounds(biddingUserProvince, bounds);
    }

	public Integer selectCount(BiddingUserProvince biddingUserProvince){
		return biddingUserProvinceMapper.selectCount(biddingUserProvince);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}