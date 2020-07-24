package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingCityMapper;
import com.dsmpharm.bidding.pojo.BiddingCity;
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
public class BiddingCityService {

	@Resource
	private BiddingCityMapper biddingCityMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingCity biddingCity){
		biddingCity.setId(idWorker.nextId() + "");
		biddingCityMapper.insert(biddingCity);
	}

	public List<BiddingCity> selectAll() {
		return biddingCityMapper.selectAll();
	}

	public BiddingCity findById(String id){
		return biddingCityMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingCity biddingCity) {
		biddingCityMapper.updateByPrimaryKey(biddingCity);
	}

	public void delete(String id) {
		biddingCityMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingCity> list(BiddingCity biddingCity) {
		return biddingCityMapper.select(biddingCity);
	}

    public List<BiddingCity> list(BiddingCity biddingCity, int currentPage, int pageSize) {
		biddingCityMapper.selectCount(biddingCity);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingCityMapper.selectByRowBounds(biddingCity, bounds);
    }

	public Integer selectCount(BiddingCity biddingCity){
		return biddingCityMapper.selectCount(biddingCity);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}