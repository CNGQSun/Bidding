package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingUserFrameworkMapper;
import com.dsmpharm.bidding.mapper.BiddingUserMapper;
import com.dsmpharm.bidding.pojo.BiddingUserFramework;
import com.dsmpharm.bidding.utils.IdWorker;
import com.dsmpharm.bidding.utils.PageResult;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <br/>
 * Created by Grant on 2020/07/23
 */
@Service
public class BiddingUserFrameworkService {
    private static Logger log = LoggerFactory.getLogger(BiddingUserFrameworkService.class);

    @Resource
    private BiddingUserFrameworkMapper biddingUserFrameworkMapper;

    @Resource
    private BiddingUserMapper biddingUserMapper;

    @Resource
    private IdWorker idWorker;

    public void insert(BiddingUserFramework biddingUserFramework) {
        biddingUserFramework.setId(idWorker.nextId() + "");
        biddingUserFrameworkMapper.insert(biddingUserFramework);
    }

    /**
     * 查询全部未删除的用户架构关系
     * @param map
     * @return
     */
    public Result selectAll(Map map) {
        try {
            Integer currentPage = Integer.valueOf(map.get("currentPage").toString());
            Integer pageSize = Integer.valueOf(map.get("pageSize").toString());
            String userId = map.get("userId").toString();
            String parentId = map.get("parentId").toString();
            String graParentId = map.get("graParentId").toString();
            List<Map> frameworkList = biddingUserFrameworkMapper.selectAllNoDel(userId, parentId, graParentId);
            PageHelper.startPage(currentPage, pageSize);
            List<Map> frameworks = biddingUserFrameworkMapper.selectAllNoDel(userId, parentId, graParentId);
            PageInfo pageInfo = new PageInfo<>(frameworks);
            pageInfo.setTotal(frameworkList.size());
            PageResult pageResult = new PageResult(pageInfo.getTotal(), frameworks);
            log.info("用户架构列表获取成功！");
            return new Result(true, StatusCode.OK, "查询成功", pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }
    /**
     * 根据ID查询用户架构信息及用户关系
     *
     * @param id
     * @return
     */
    public Result findById(String id) {
        try {
            Map map = biddingUserFrameworkMapper.selectById(id);
            if (map!=null){
                return new Result<>(true, StatusCode.OK, "查询成功", map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
        return new Result<>(false, StatusCode.ERROR, "查询失败");
    }

    /**
     * 根据id修改架构关系
     *
     * @param map
     * @return
     */
    public Result updateById(Map map) {
        String id = map.get("id").toString();
        String userId = map.get("userId").toString();
        String parentId = map.get("parentId").toString();
        String graParentId = map.get("graParentId").toString();
        BiddingUserFramework biddingUserFramework=new BiddingUserFramework();
        int i = 0;
        try {
            biddingUserFramework.setId(id);
            biddingUserFramework.setUserId(userId);
            biddingUserFramework.setParentId(parentId);
            biddingUserFramework.setGraParentId(graParentId);
            biddingUserFramework.setStatus("0");
            BiddingUserFramework biddingUserFramework1 = new BiddingUserFramework();
            biddingUserFramework1.setStatus("0");
            biddingUserFramework1.setGraParentId(biddingUserFramework.getGraParentId());
            biddingUserFramework1.setParentId(biddingUserFramework.getParentId());
            biddingUserFramework1.setUserId(biddingUserFramework.getUserId());
            biddingUserFramework1.setDelflag("0");
            int i1 = biddingUserFrameworkMapper.selectCount(biddingUserFramework1);
            if (i1 > 0) {
                return new Result<>(false, StatusCode.ERROR, "已存在，请勿重复提交");
            }
            i = biddingUserFrameworkMapper.updateByPrimaryKeySelective(biddingUserFramework);
            if (i > 0) {
                return new Result<>(true, StatusCode.OK, "修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器异常");
        }
        return new Result<>(false, StatusCode.ERROR, "修改失败");
    }

    /**
     * 根据ID删除多个用户架构关系，将delflag置为1
     *
     * @param id
     * @return
     */
    public Result delete(String id) {
        BiddingUserFramework biddingUserFramework = new BiddingUserFramework();
        biddingUserFramework.setId(id);
        biddingUserFramework.setDelflag("1");
        try {
            int i = biddingUserFrameworkMapper.updateByPrimaryKeySelective(biddingUserFramework);
            if (i > 0) {
                return new Result<>(true, StatusCode.OK, "删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
        return new Result<>(false, StatusCode.ERROR, "删除失败");
    }

    public List<BiddingUserFramework> list(BiddingUserFramework biddingUserFramework) {
        return biddingUserFrameworkMapper.select(biddingUserFramework);
    }

    public List<BiddingUserFramework> list(BiddingUserFramework biddingUserFramework, int currentPage, int pageSize) {
        biddingUserFrameworkMapper.selectCount(biddingUserFramework);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingUserFrameworkMapper.selectByRowBounds(biddingUserFramework, bounds);
    }

    public Integer selectCount(BiddingUserFramework biddingUserFramework) {
        return biddingUserFrameworkMapper.selectCount(biddingUserFramework);
    }

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }

    /**
     * 提交用户架构关系
     * @param map
     * @return
     */
    public Result insertFrameSub(Map map) {
        String userId = map.get("userId").toString();
        String parentId = map.get("parentId").toString();
        String graParentId = map.get("graParentId").toString();
        BiddingUserFramework biddingUserFramework=new BiddingUserFramework();
        int i = 0;
        try {
            biddingUserFramework.setUserId(userId);
            biddingUserFramework.setParentId(parentId);
            biddingUserFramework.setGraParentId(graParentId);
            biddingUserFramework.setDelflag("0");
            biddingUserFramework.setStatus("0");
            int i1 = biddingUserFrameworkMapper.selectCount(biddingUserFramework);
            if (i1 > 0) {
                return new Result<>(false, StatusCode.ERROR, "已存在，请勿重复提交");
            }
            biddingUserFramework.setId(idWorker.nextId() + "");
            i = biddingUserFrameworkMapper.insert(biddingUserFramework);
            if (i > 0) {
                return new Result<>(true, StatusCode.OK, "提交成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器异常");
        }
        return new Result<>(false, StatusCode.ERROR, "提交失败");
    }

    /**
     * 保存用户架构关系
     * @param map
     * @return
     */
    public Result insertFramePre(Map map) {
        String userId = map.get("userId").toString();
        String parentId = map.get("parentId").toString();
        String graParentId = map.get("graParentId").toString();
        BiddingUserFramework biddingUserFramework=new BiddingUserFramework();
        int i = 0;
        try {
            biddingUserFramework.setUserId(userId);
            biddingUserFramework.setParentId(parentId);
            biddingUserFramework.setGraParentId(graParentId);
            biddingUserFramework.setDelflag("0");
            biddingUserFramework.setStatus("1");
            int i1 = biddingUserFrameworkMapper.selectCount(biddingUserFramework);
            if (i1 > 0) {
                return new Result<>(false, StatusCode.ERROR, "已存在，请勿重复填写");
            }
            biddingUserFramework.setId(idWorker.nextId() + "");
            i = biddingUserFrameworkMapper.insert(biddingUserFramework);
            if (i > 0) {
                return new Result<>(true, StatusCode.OK, "保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器异常");
        }
        return new Result<>(false, StatusCode.ERROR, "保存失败");
    }

    /**
     * 根据id删除多个用户架构关系，delflag置为1
     *
     * @param ids
     * @return
     */
    public Result deleteIds(String ids) {
        String[] split = ids.split(",");
        BiddingUserFramework biddingUserFramework = null;
        List<BiddingUserFramework> frameworks = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            biddingUserFramework = new BiddingUserFramework();
            biddingUserFramework.setId(split[i]);
            biddingUserFramework.setDelflag("1");
            frameworks.add(biddingUserFramework);
        }
        try {
            for (BiddingUserFramework framework : frameworks) {
                biddingUserFrameworkMapper.updateByPrimaryKeySelective(framework);
            }
            return new Result<>(true, StatusCode.OK, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }
}