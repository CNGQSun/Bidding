package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingProjectTypeMapper;
import com.dsmpharm.bidding.pojo.BiddingProjectType;
import com.dsmpharm.bidding.utils.IdWorker;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/03
 */
@Service
public class BiddingProjectTypeService {

	@Resource
	private BiddingProjectTypeMapper biddingProjectTypeMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingProjectType biddingProjectType){
		biddingProjectType.setId(idWorker.nextId() + "");
		biddingProjectTypeMapper.insert(biddingProjectType);
	}

	public List<BiddingProjectType> selectAll() {
		return biddingProjectTypeMapper.selectAll();
	}

	public BiddingProjectType findById(String id){
		return biddingProjectTypeMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingProjectType biddingProjectType) {
		biddingProjectTypeMapper.updateByPrimaryKey(biddingProjectType);
	}

	public void delete(String id) {
		biddingProjectTypeMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingProjectType> list(BiddingProjectType biddingProjectType) {
		return biddingProjectTypeMapper.select(biddingProjectType);
	}

    public List<BiddingProjectType> list(BiddingProjectType biddingProjectType, int currentPage, int pageSize) {
		biddingProjectTypeMapper.selectCount(biddingProjectType);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingProjectTypeMapper.selectByRowBounds(biddingProjectType, bounds);
    }

	public Integer selectCount(BiddingProjectType biddingProjectType){
		return biddingProjectTypeMapper.selectCount(biddingProjectType);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}