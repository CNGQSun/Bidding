package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingPhaseFileMapper;
import com.dsmpharm.bidding.pojo.BiddingPhaseFile;
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
public class BiddingPhaseFileService {
	private static Logger log = LoggerFactory.getLogger(BiddingPhaseFileService.class);

	@Resource
	private BiddingPhaseFileMapper biddingPhaseFileMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingPhaseFile biddingPhaseFile){
		biddingPhaseFile.setId(idWorker.nextId() + "");
		biddingPhaseFileMapper.insert(biddingPhaseFile);
	}

	public List<BiddingPhaseFile> selectAll() {
		return biddingPhaseFileMapper.selectAll();
	}

	public BiddingPhaseFile findById(String id){
		return biddingPhaseFileMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingPhaseFile biddingPhaseFile) {
		biddingPhaseFileMapper.updateByPrimaryKey(biddingPhaseFile);
	}

	public void delete(String id) {
		biddingPhaseFileMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingPhaseFile> list(BiddingPhaseFile biddingPhaseFile) {
		return biddingPhaseFileMapper.select(biddingPhaseFile);
	}

    public List<BiddingPhaseFile> list(BiddingPhaseFile biddingPhaseFile, int currentPage, int pageSize) {
		biddingPhaseFileMapper.selectCount(biddingPhaseFile);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingPhaseFileMapper.selectByRowBounds(biddingPhaseFile, bounds);
    }

	public Integer selectCount(BiddingPhaseFile biddingPhaseFile){
		return biddingPhaseFileMapper.selectCount(biddingPhaseFile);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}