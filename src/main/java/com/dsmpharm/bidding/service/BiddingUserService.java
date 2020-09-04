package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.entity.BiddingLoginUser;
import com.dsmpharm.bidding.mapper.BiddingRoleMapper;
import com.dsmpharm.bidding.mapper.BiddingUserFrameworkMapper;
import com.dsmpharm.bidding.mapper.BiddingUserMapper;
import com.dsmpharm.bidding.mapper.BiddingUserRoleMapper;
import com.dsmpharm.bidding.pojo.BiddingRole;
import com.dsmpharm.bidding.pojo.BiddingUser;
import com.dsmpharm.bidding.pojo.BiddingUserFramework;
import com.dsmpharm.bidding.pojo.BiddingUserRole;
import com.dsmpharm.bidding.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger log = LoggerFactory.getLogger(BiddingUserService.class);

    @Resource
    private BiddingUserMapper biddingUserMapper;

    @Resource
    private BiddingRoleMapper biddingRoleMapper;

    @Resource
    private BiddingUserRoleMapper biddingUserRoleMapper;

    @Resource
    private BiddingUserFrameworkMapper biddingUserFrameworkMapper;


    @Resource
    private IdWorker idWorker;
    @Resource
    private JwtUtil jwtUtil;

    public void insert(BiddingUser biddingUser) {
        biddingUser.setId(idWorker.nextId() + "");
        biddingUserMapper.insert(biddingUser);
    }

    /**
     * 查询所有未删除的用户
     *
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
            log.error(e.toString(), e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }

    /**
     * 根据ID查询用户详情
     *
     * @param id
     * @return
     */
    public Result findById(String id) {
        try {
            Map map = biddingUserMapper.selectById(id);
            if (map != null) {
                return new Result<>(true, StatusCode.OK, "查询成功", map);
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
        return new Result<>(false, StatusCode.ERROR, "查询失败");
    }

    /**
     * 根据id修改用户信息
     *
     * @param biddingUser
     * @param role
     * @return
     */
    public Result updateById(BiddingUser biddingUser, String role) {
        try {
            biddingUser.setStatus("0");
            int i = biddingUserMapper.updateByPrimaryKeySelective(biddingUser);
            BiddingUserRole biddingUserRole = new BiddingUserRole();
            biddingUserRole.setUserId(biddingUser.getId());
            biddingUserRole.setDelflag("0");
            BiddingUserRole biddingUserRole1 = biddingUserRoleMapper.selectOne(biddingUserRole);
            biddingUserRole1.setRoleId(role);
            int i1 = biddingUserRoleMapper.updateByPrimaryKeySelective(biddingUserRole1);
            if (i > 0 && i1 > 0) {
                return new Result<>(true, StatusCode.OK, "修改成功");
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
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

        biddingUser.setPassword(biddingUser.getPhoneNumber());
        //加盐,第一个参数是密码,第二个参数是所加盐(本项目用手机号作为盐)
        Md5Hash md5HashStudent = new Md5Hash(biddingUser.getPassword(), biddingUser.getPhoneNumber());
        //设置迭代次数
        md5HashStudent.setIterations(1);
        //将加密后的密码转化为string类型
        String password = md5HashStudent.toString();
        //设置加密后的password
        biddingUser.setPassword(password);
        biddingUser.setStatus("0");
        biddingUser.setDelflag("0");
        int insertUser = 0;
        int insertRole = 0;
        try {
            int i = biddingUserMapper.selectCount(biddingUser);
            if (i > 0) {
                return new Result<>(false, StatusCode.ERROR, "该用户已存在");
            }
            biddingUser.setDept("市场准入及商务部");
            insertUser = biddingUserMapper.insert(biddingUser);
            BiddingUserRole biddingUserRole = new BiddingUserRole();
            biddingUserRole.setId(idWorker.nextId() + "");
            biddingUserRole.setRoleId(role);
            biddingUserRole.setUserId(biddingUser.getId());
            biddingUserRole.setDelflag("0");
            insertRole = biddingUserRoleMapper.insert(biddingUserRole);
        } catch (Exception e) {
            log.error(e.toString(), e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
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
            log.error(e.toString(), e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
        if (insertRole > 0 && insertUser > 0) {
            return new Result<>(true, StatusCode.OK, "已保存");
        }
        return new Result<>(false, StatusCode.ERROR, "保存失败");
    }

    /**
     * 根据ID删除用户信息
     *
     * @param id
     * @return
     */
    public Result deleteById(String id) {
        try {
            BiddingUser biddingUser = new BiddingUser();
            biddingUser.setId(id);
            biddingUser.setDelflag("0");
            int i = biddingUserMapper.selectCount(biddingUser);
            if (i <= 0) {
                return new Result<>(false, StatusCode.ERROR, "该记录不存在");
            }
            biddingUser.setDelflag("1");
            int i1 = biddingUserMapper.updateByPrimaryKeySelective(biddingUser);
            if (i1 > 0) {
                return new Result<>(true, StatusCode.OK, "删除成功");
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
        return new Result<>(false, StatusCode.ERROR, "删除失败");
    }

    /**
     * 根据IDS批量删除用户信息
     *
     * @param ids
     * @return
     */
    public Result deleteByIdS(String ids) {
        String[] split = ids.split(",");
        try {
            for (int i = 0; i < split.length; i++) {
                String id = split[i];
                BiddingUser biddingUser = new BiddingUser();
                biddingUser.setId(id);
                biddingUser.setDelflag("0");
                int j = biddingUserMapper.selectCount(biddingUser);
                if (j <= 0) {
                    return new Result<>(false, StatusCode.ERROR, "有记录不存在");
                }
                biddingUser.setDelflag("1");
                biddingUserMapper.updateByPrimaryKeySelective(biddingUser);
            }
            return new Result<>(true, StatusCode.OK, "删除成功");

        } catch (Exception e) {
            log.error(e.toString(), e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }

    /**
     * 用户登录
     *
     * @param map
     * @return
     */
    public Result login(Map map) {
        try {
            String userId = map.get("userId").toString();
            String password = map.get("password").toString();
            BiddingUser biddingUser = biddingUserMapper.selectByPrimaryKey(userId);
            if (biddingUser == null || biddingUser.getDelflag().equals("1") || biddingUser.getStatus().equals("1")) {
                return new Result<>(false, StatusCode.ERROR, "账号不存在或未启用，请联系管理员");
            }
            //给用户输入的密码加密
            Md5Hash md5HashPassword = new Md5Hash(password, biddingUser.getPhoneNumber());
            //迭代一次
            md5HashPassword.setIterations(1);

            //开始比较用户输入的密码加密后是否与数据库里存储的加密密码一致
            if ((md5HashPassword.toString()).equals(biddingUser.getPassword())) {
                //密码正确
                BiddingUserRole biddingUserRole = biddingUserRoleMapper.selectByUserId(biddingUser.getId());
                BiddingRole biddingRole = biddingRoleMapper.selectByPrimaryKey(biddingUserRole.getRoleId());
                BiddingLoginUser biddingLoginUser = new BiddingLoginUser();
                biddingLoginUser.setId(biddingUser.getId());
                biddingLoginUser.setDelflag(biddingUser.getDelflag());
                biddingLoginUser.setDept(biddingUser.getDept());
                biddingLoginUser.setEmail(biddingUser.getEmail());
                biddingLoginUser.setName(biddingUser.getName());
                biddingLoginUser.setParentId(biddingUser.getParentId());
                biddingLoginUser.setPassword(biddingUser.getPassword());
                biddingLoginUser.setPhoneNumber(biddingUser.getPhoneNumber());
                biddingLoginUser.setStatus(biddingUser.getStatus());
                biddingLoginUser.setRoleId(biddingUserRole.getRoleId());
                biddingLoginUser.setRoleName(biddingRole.getName());


                if (biddingUserRole.getRoleId().equals("1")) {
                    List<BiddingUserFramework> biddingUserFrameworks = biddingUserFrameworkMapper.selectByGraParent(userId);
                    if (biddingUserFrameworks != null && biddingUserFrameworks.size() > 0) {
                        //大区经理不空岗，不可以创建项目
                        biddingLoginUser.setIsEmpty("1");
                    } else {
                        //大区经理空岗 可以创建项目
                        biddingLoginUser.setIsEmpty("0");
                    }
                }

                if (biddingUserRole.getRoleId().equals("2")) {
                    List<BiddingUserFramework> biddingUserFrameworks = biddingUserFrameworkMapper.selectByParent(userId);
                    if (biddingUserFrameworks != null && biddingUserFrameworks.size() > 0) {
                        //商务经理不空岗，不可以创建项目
                        biddingLoginUser.setIsEmpty("1");
                    } else {
                        //商务经理空岗 可以创建项目
                        biddingLoginUser.setIsEmpty("0");
                    }
                }
                // 登陆成功，返回令牌给用户
                String token = jwtUtil.createJWT(biddingUser.getId(), biddingUser.getPhoneNumber());
                biddingLoginUser.setToken(token);
                return new Result<>(true, StatusCode.OK, "登录成功", biddingLoginUser);
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
        return new Result<>(false, StatusCode.ERROR, "登录失败，请联系管理员");
    }

    public Result changePassword(Map map, String userId) {
        try {
            String oldPassword = map.get("oldPassword").toString();
            String newPassword = map.get("newPassword").toString();
            String conPassword = map.get("conPassword").toString();
            BiddingUser biddingUser = biddingUserMapper.selectByPrimaryKey(userId);
            if (biddingUser == null || biddingUser.getDelflag().equals("1") || biddingUser.getStatus().equals("1")) {
                return new Result<>(false, StatusCode.ERROR, "账号不存在或未启用，请联系管理员");
            }
            //给用户输入的密码加密
            Md5Hash md5HashPassword = new Md5Hash(oldPassword, biddingUser.getPhoneNumber());
            //迭代一次
            md5HashPassword.setIterations(1);
            //开始比较用户输入的密码加密后是否与数据库里存储的加密密码一致
            if ((md5HashPassword.toString()).equals(biddingUser.getPassword())) {
                if (!newPassword.equals(conPassword)) {
                    return new Result<>(false, StatusCode.ERROR, "两次密码输入不一致");
                }
                if (newPassword.equals(oldPassword)) {
                    return new Result<>(false, StatusCode.ERROR, "新密码与旧密码一致");
                }
                //给用户输入的密码加密
                Md5Hash md5HashNewPassword = new Md5Hash(newPassword, biddingUser.getPhoneNumber());
                biddingUser.setPassword(md5HashNewPassword.toString());
                int i = biddingUserMapper.updateByPrimaryKeySelective(biddingUser);
                if (i > 0) {
                    return new Result<>(true, StatusCode.OK, "密码修改成功");
                }
            }
        } catch (Exception e) {
            log.error(e.toString(),e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
        return new Result<>(false, StatusCode.ERROR, "密码修改失败");
    }

    public Result changeAllPassword(String userId) {
        try {
            BiddingUser biddingUser = biddingUserMapper.selectByPrimaryKey(userId);
            if (biddingUser==null ||biddingUser.getDelflag().equals("1")){
                return new Result<>(false, StatusCode.ERROR, "该用户不存在");
            }
            //加盐,第一个参数是密码,第二个参数是所加盐(本项目用手机号作为盐)
            Md5Hash md5HashStudent = new Md5Hash(biddingUser.getPhoneNumber(), biddingUser.getPhoneNumber());
            //设置迭代次数
            md5HashStudent.setIterations(1);
            //将加密后的密码转化为string类型
            String password = md5HashStudent.toString();
            //设置加密后的password
            biddingUser.setPassword(password);
            int i = biddingUserMapper.updateByPrimaryKeySelective(biddingUser);
            if (i>0){
                return new Result<>(true, StatusCode.OK, "密码重置成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
        return new Result<>(false, StatusCode.ERROR, "密码重置失败");
    }
}