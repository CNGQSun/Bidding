package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingUserMapper;
import com.dsmpharm.bidding.mapper.BiddingUserRoleMapper;
import com.dsmpharm.bidding.pojo.BiddingUser;
import com.dsmpharm.bidding.pojo.BiddingUserRole;
import com.dsmpharm.bidding.utils.IdWorker;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <br/>
 * Created by Grant on 2020/07/23
 */
@Service
public class BiddingUserRoleService {

    @Resource
    private BiddingUserRoleMapper biddingUserRoleMapper;

    @Resource
    private BiddingUserMapper biddingUserMapper;

    @Resource
    private IdWorker idWorker;

    public void insert(BiddingUserRole biddingUserRole) {
        biddingUserRole.setId(idWorker.nextId() + "");
        biddingUserRoleMapper.insert(biddingUserRole);
    }

    public List<BiddingUserRole> selectAll() {
        return biddingUserRoleMapper.selectAll();
    }

    public BiddingUserRole findById(String id) {
        return biddingUserRoleMapper.selectByPrimaryKey(id);
    }

    public void updateById(BiddingUserRole biddingUserRole) {
        biddingUserRoleMapper.updateByPrimaryKey(biddingUserRole);
    }

    public void delete(String id) {
        biddingUserRoleMapper.deleteByPrimaryKey(id);
    }

    public List<BiddingUserRole> list(BiddingUserRole biddingUserRole) {
        return biddingUserRoleMapper.select(biddingUserRole);
    }

    public List<BiddingUserRole> list(BiddingUserRole biddingUserRole, int currentPage, int pageSize) {
        biddingUserRoleMapper.selectCount(biddingUserRole);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingUserRoleMapper.selectByRowBounds(biddingUserRole, bounds);
    }

    public Integer selectCount(BiddingUserRole biddingUserRole) {
        return biddingUserRoleMapper.selectCount(biddingUserRole);
    }

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }


    /**
     * 根据角色获取用户信息
     *
     * @param role
     * @return
     */
    public Result findByRole(String role) {
        try {
            BiddingUserRole biddingUserRole = new BiddingUserRole();
            biddingUserRole.setRoleId(role);
            List<BiddingUserRole> biddingUserRoles = biddingUserRoleMapper.select(biddingUserRole);
            ArrayList<BiddingUser> userList = new ArrayList();
            for (BiddingUserRole userRole : biddingUserRoles) {
                BiddingUser biddingUser = new BiddingUser();
                biddingUser.setId(userRole.getUserId());
                biddingUser.setStatus("0");
                biddingUser.setDelflag("0");
                BiddingUser biddingUser1 = biddingUserMapper.selectOne(biddingUser);
                if (biddingUser1 != null) {
                    userList.add(biddingUser1);
                }
            }
            return new Result<>(true, StatusCode.OK, "获取成功", userList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "获取失败");
        }
    }
}