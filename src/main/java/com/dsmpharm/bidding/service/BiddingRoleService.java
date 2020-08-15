package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingRoleMapper;
import com.dsmpharm.bidding.pojo.BiddingRole;
import com.dsmpharm.bidding.utils.IdWorker;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
@Service
public class BiddingRoleService {

	@Resource
	private BiddingRoleMapper biddingRoleMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingRole biddingRole){
		biddingRole.setId(idWorker.nextId() + "");
		biddingRoleMapper.insert(biddingRole);
	}

	public Result selectAll() {
		List<BiddingRole> biddingRoles= null;
		try {
			biddingRoles = biddingRoleMapper.selectAllNoDel();
			if (biddingRoles!=null){
				return new Result<>(true, StatusCode.OK, "查询成功", biddingRoles);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<>(true, StatusCode.ERROR, "呀! 服务器开小差了~");
		}
		return new Result<>(true, StatusCode.ERROR, "查询失败");
	}

	public BiddingRole findById(String id){
		return biddingRoleMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingRole biddingRole) {
		biddingRoleMapper.updateByPrimaryKey(biddingRole);
	}

	public void delete(String id) {
		biddingRoleMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingRole> list(BiddingRole biddingRole) {
		return biddingRoleMapper.select(biddingRole);
	}

    public List<BiddingRole> list(BiddingRole biddingRole, int currentPage, int pageSize) {
		biddingRoleMapper.selectCount(biddingRole);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingRoleMapper.selectByRowBounds(biddingRole, bounds);
    }

	public Integer selectCount(BiddingRole biddingRole){
		return biddingRoleMapper.selectCount(biddingRole);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }
}