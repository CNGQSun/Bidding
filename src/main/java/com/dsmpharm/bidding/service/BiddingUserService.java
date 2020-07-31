package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingRoleMapper;
import com.dsmpharm.bidding.mapper.BiddingUserMapper;
import com.dsmpharm.bidding.mapper.BiddingUserRoleMapper;
import com.dsmpharm.bidding.pojo.BiddingUser;
import com.dsmpharm.bidding.pojo.BiddingUserRole;
import com.dsmpharm.bidding.utils.IdWorker;
import com.dsmpharm.bidding.utils.PageResult;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <br/>
 * Created by Grant on 2020/07/23
 */
@Service
public class BiddingUserService {

    @Resource
    private BiddingUserMapper biddingUserMapper;

    @Resource
    private BiddingRoleMapper biddingRoleMapper;

    @Resource
    private BiddingUserRoleMapper biddingUserRoleMapper;

    @Resource
    private IdWorker idWorker;

    public void insert(BiddingUser biddingUser) {
        biddingUser.setId(idWorker.nextId() + "");
        biddingUserMapper.insert(biddingUser);
    }

    /**
     * 查询所有未删除的用户
     * @param map
     * @return
     */
    public Result selectAll(Map map) {
        try {
            Integer currentPage = Integer.valueOf(map.get("currentPage").toString());
            Integer pageSize = Integer.valueOf(map.get("pageSize").toString());
            String name = map.get("name").toString();
            String roleId = map.get("roleId").toString();
            List<Map> userList = biddingUserMapper.selectAllNoDel(name, roleId);
            PageHelper.startPage(currentPage, pageSize);
            List<Map> users = biddingUserMapper.selectAllNoDel(name, roleId);
            PageInfo pageInfo = new PageInfo<>(users);
            pageInfo.setTotal(userList.size());
            PageResult pageResult = new PageResult(pageInfo.getTotal(), users);
            return new Result<>(true, StatusCode.OK, "查询成功", pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
        }
    }

    /**
     * 根据ID查询用户详情
     * @param id
     * @return
     */
    public Result findById(String id) {
        try {
            Map map = biddingUserMapper.selectById(id);
            if (map != null) {
                return new Result<>(true, StatusCode.OK, "查询成功",map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
        }
        return new Result<>(false, StatusCode.ERROR, "查询失败");
    }

    /**
     * 根据id修改用户信息
     * @param biddingUser
     * @param role
     * @return
     */
    public Result updateById(BiddingUser biddingUser, String role) {
        try {
            biddingUser.setStatus("0");
            int i = biddingUserMapper.updateByPrimaryKeySelective(biddingUser);
            BiddingUserRole biddingUserRole=new BiddingUserRole();
            biddingUserRole.setUserId(biddingUser.getId());
            biddingUserRole.setDelflag("0");
            BiddingUserRole biddingUserRole1 = biddingUserRoleMapper.selectOne(biddingUserRole);
            biddingUserRole1.setRoleId(role);
            int i1 = biddingUserRoleMapper.updateByPrimaryKeySelective(biddingUserRole1);
            if (i>0&&i1>0){
                return new Result<>(true, StatusCode.OK, "修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
        }
        return new Result<>(false, StatusCode.ERROR, "修改失败");
    }

    public void delete(String id) {
        biddingUserMapper.deleteByPrimaryKey(id);
    }

    public List<BiddingUser> list(BiddingUser biddingUser) {
        return biddingUserMapper.select(biddingUser);
    }

    public List<BiddingUser> list(BiddingUser biddingUser, int currentPage, int pageSize) {
        biddingUserMapper.selectCount(biddingUser);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingUserMapper.selectByRowBounds(biddingUser, bounds);
    }

    public Integer selectCount(BiddingUser biddingUser) {
        return biddingUserMapper.selectCount(biddingUser);
    }

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }

    /**
     * 添加新用户（包括角色），点击提交
     *
     * @param biddingUser
     * @param role
     * @return
     */
    public Result insertUserSub(BiddingUser biddingUser, String role) {

        biddingUser.setStatus("0");
        biddingUser.setDelflag("0");
        int insertUser = 0;
        int insertRole = 0;
        try {
            int i = biddingUserMapper.selectCount(biddingUser);
            if (i>0){
                return new Result<>(false, StatusCode.ERROR, "该用户已存在");
            }
            biddingUser.setId(idWorker.nextId() + "");
            biddingUser.setDept("市场准入及商务部");
            insertUser = biddingUserMapper.insert(biddingUser);
            BiddingUserRole biddingUserRole = new BiddingUserRole();
            biddingUserRole.setId(idWorker.nextId() + "");
            biddingUserRole.setRoleId(role);
            biddingUserRole.setUserId(biddingUser.getId());
            biddingUserRole.setDelflag("0");
            insertRole = biddingUserRoleMapper.insert(biddingUserRole);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
        }
        if (insertRole > 0 && insertUser > 0) {
            return new Result<>(true, StatusCode.OK, "添加用户成功");
        }
        return new Result<>(false, StatusCode.ERROR, "添加用户失败");
    }

    /**
     * 添加新用户（包括角色），点击保存
     *
     * @param biddingUser
     * @param role
     * @return
     */
    public Result insertUserPre(BiddingUser biddingUser, String role) {
        biddingUser.setId(idWorker.nextId() + "");
        biddingUser.setStatus("1");
        biddingUser.setDelflag("0");
        biddingUser.setDept("市场准入及商务部");
        int insertUser = 0;
        int insertRole = 0;
        try {
            insertUser = biddingUserMapper.insert(biddingUser);
            BiddingUserRole biddingUserRole = new BiddingUserRole();
            biddingUserRole.setId(idWorker.nextId() + "");
            biddingUserRole.setRoleId(role);
            biddingUserRole.setUserId(biddingUser.getId());
            biddingUserRole.setDelflag("0");
            insertRole = biddingUserRoleMapper.insert(biddingUserRole);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
        }
        if (insertRole > 0 && insertUser > 0) {
            return new Result<>(true, StatusCode.OK, "已保存");
        }
        return new Result<>(false, StatusCode.ERROR, "保存失败");
    }

    public Result deleteById(String id) {
        try {
            BiddingUser biddingUser=new BiddingUser();
            biddingUser.setId(id);
            biddingUser.setDelflag("0");
            int i = biddingUserMapper.selectCount(biddingUser);
            if (i<=0){
                return new Result<>(false, StatusCode.ERROR, "该记录不存在");
            }
            biddingUser.setDelflag("1");
            int i1 = biddingUserMapper.updateByPrimaryKeySelective(biddingUser);
            if (i1>0){
                return new Result<>(true, StatusCode.OK, "删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
        }
        return new Result<>(false, StatusCode.ERROR, "删除失败");
    }
}