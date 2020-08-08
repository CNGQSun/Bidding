package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingProjectMapper;
import com.dsmpharm.bidding.pojo.BiddingProject;
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
public class BiddingProjectService {

	@Resource
	private BiddingProjectMapper biddingProjectMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingProject biddingProject){
		biddingProject.setId(idWorker.nextId() + "");
		biddingProjectMapper.insert(biddingProject);
	}

	public List<BiddingProject> selectAll() {
		return biddingProjectMapper.selectAll();
	}

	public BiddingProject findById(String id){
		return biddingProjectMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingProject biddingProject) {
		biddingProjectMapper.updateByPrimaryKey(biddingProject);
	}

	public void delete(String id) {
		biddingProjectMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingProject> list(BiddingProject biddingProject) {
		return biddingProjectMapper.select(biddingProject);
	}

    public List<BiddingProject> list(BiddingProject biddingProject, int currentPage, int pageSize) {
		biddingProjectMapper.selectCount(biddingProject);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingProjectMapper.selectByRowBounds(biddingProject, bounds);
    }

	public Integer selectCount(BiddingProject biddingProject){
		return biddingProjectMapper.selectCount(biddingProject);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}