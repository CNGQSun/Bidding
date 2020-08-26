package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingOfficialNoticeMapper;
import com.dsmpharm.bidding.pojo.BiddingOfficialNotice;
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
public class BiddingOfficialNoticeService {
	private static Logger log = LoggerFactory.getLogger(BiddingOfficialNoticeService.class);

	@Resource
	private BiddingOfficialNoticeMapper biddingOfficialNoticeMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingOfficialNotice biddingOfficialNotice){
		biddingOfficialNotice.setId(idWorker.nextId() + "");
		biddingOfficialNoticeMapper.insert(biddingOfficialNotice);
	}

	public List<BiddingOfficialNotice> selectAll() {
		return biddingOfficialNoticeMapper.selectAll();
	}

	public BiddingOfficialNotice findById(String id){
		return biddingOfficialNoticeMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingOfficialNotice biddingOfficialNotice) {
		biddingOfficialNoticeMapper.updateByPrimaryKey(biddingOfficialNotice);
	}

	public void delete(String id) {
		biddingOfficialNoticeMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingOfficialNotice> list(BiddingOfficialNotice biddingOfficialNotice) {
		return biddingOfficialNoticeMapper.select(biddingOfficialNotice);
	}

    public List<BiddingOfficialNotice> list(BiddingOfficialNotice biddingOfficialNotice, int currentPage, int pageSize) {
		biddingOfficialNoticeMapper.selectCount(biddingOfficialNotice);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingOfficialNoticeMapper.selectByRowBounds(biddingOfficialNotice, bounds);
    }

	public Integer selectCount(BiddingOfficialNotice biddingOfficialNotice){
		return biddingOfficialNoticeMapper.selectCount(biddingOfficialNotice);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}