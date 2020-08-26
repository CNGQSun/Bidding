package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingSettingsExtraMapper;
import com.dsmpharm.bidding.pojo.BiddingSettingsExtra;
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
public class BiddingSettingsExtraService {
	private static Logger log = LoggerFactory.getLogger(BiddingSettingsExtraService.class);

	@Resource
	private BiddingSettingsExtraMapper biddingSettingsExtraMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingSettingsExtra biddingSettingsExtra){
		biddingSettingsExtra.setId(idWorker.nextId() + "");
		biddingSettingsExtraMapper.insert(biddingSettingsExtra);
	}

	public List<BiddingSettingsExtra> selectAll() {
		return biddingSettingsExtraMapper.selectAll();
	}

	public BiddingSettingsExtra findById(String id){
		return biddingSettingsExtraMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingSettingsExtra biddingSettingsExtra) {
		biddingSettingsExtraMapper.updateByPrimaryKey(biddingSettingsExtra);
	}

	public void delete(String id) {
		biddingSettingsExtraMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingSettingsExtra> list(BiddingSettingsExtra biddingSettingsExtra) {
		return biddingSettingsExtraMapper.select(biddingSettingsExtra);
	}

    public List<BiddingSettingsExtra> list(BiddingSettingsExtra biddingSettingsExtra, int currentPage, int pageSize) {
		biddingSettingsExtraMapper.selectCount(biddingSettingsExtra);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingSettingsExtraMapper.selectByRowBounds(biddingSettingsExtra, bounds);
    }

	public Integer selectCount(BiddingSettingsExtra biddingSettingsExtra){
		return biddingSettingsExtraMapper.selectCount(biddingSettingsExtra);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}