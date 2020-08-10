package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingContentSettingsMapper;
import com.dsmpharm.bidding.pojo.BiddingContentSettings;
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
 * Created by Grant on 2020/08/03
 */
@Service
public class BiddingContentSettingsService {

    @Resource
    private BiddingContentSettingsMapper biddingContentSettingsMapper;

    @Resource
    private IdWorker idWorker;

    public void insert(BiddingContentSettings biddingContentSettings) {
        biddingContentSettings.setId(idWorker.nextId() + "");
        biddingContentSettingsMapper.insert(biddingContentSettings);
    }

    public List<BiddingContentSettings> selectAll() {
        return biddingContentSettingsMapper.selectAll();
    }

    /**
     * 根据ID查询内容设置详细信息
     *
     * @param id
     * @return
     */
    public Result findById(String id) {
        try {
            Map map = biddingContentSettingsMapper.selectByIdNoDel(id);
            if (map != null) {
                return new Result<>(true, StatusCode.OK, "查询成功", map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
        }
        return new Result<>(false, StatusCode.ERROR, "查询失败");
    }

    /**
     * 根据ID修改内容设置详细信息
     *
     * @param map
     * @return
     */
    public Result updateById(Map map) {
        try {
            String id = map.get("id").toString();
            BiddingContentSettings biddingContentSettings1 = biddingContentSettingsMapper.selectByPrimaryKey(id);
            if (biddingContentSettings1.getDelflag().equals("1")) {
                return new Result<>(false, StatusCode.ERROR, "该条记录不存在");
            }
            String name = map.get("name").toString();
            String isNull = map.get("isNull").toString();
            String contentTypeId = map.get("contentTypeId").toString();
            BiddingContentSettings biddingContentSettings = new BiddingContentSettings();
            biddingContentSettings.setId(id);
            biddingContentSettings.setName(name);
            biddingContentSettings.setIsNull(isNull);
            biddingContentSettings.setContentTypeId(contentTypeId);
            biddingContentSettings.setProjectPhaseId(biddingContentSettings1.getProjectPhaseId());
            biddingContentSettings.setDelflag("0");
            int i1 = biddingContentSettingsMapper.selectCount(biddingContentSettings);
            if (i1 > 0) {
                return new Result<>(false, StatusCode.ERROR, "该条记录已存在");
            }
            int i = biddingContentSettingsMapper.updateByPrimaryKeySelective(biddingContentSettings);
            if (i > 0) {
                return new Result<>(true, StatusCode.OK, "修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
        }
        return new Result<>(false, StatusCode.ERROR, "修改失败");
    }

    public void delete(String id) {
        biddingContentSettingsMapper.deleteByPrimaryKey(id);
    }

    /**
     * 分页、条件查询所有内容设置
     * @param map
     * @return
     */
    public Result list(Map map) {
        try {
            String name = map.get("name").toString();
			String projectPhaseId = map.get("projectPhaseId").toString();
            Integer currentPage = Integer.valueOf(map.get("currentPage").toString());
            Integer pageSize = Integer.valueOf(map.get("pageSize").toString());
            List<Map> selectList = biddingContentSettingsMapper.selectList(name,projectPhaseId);
            PageHelper.startPage(currentPage, pageSize);
            List<Map> maps = biddingContentSettingsMapper.selectList(name,projectPhaseId);
            PageInfo pageInfo = new PageInfo<>(maps);
            pageInfo.setTotal(selectList.size());
            PageResult pageResult = new PageResult(pageInfo.getTotal(), maps);
            return new Result(true, StatusCode.OK, "查询成功", pageResult);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
        }
    }

    public List<BiddingContentSettings> list(BiddingContentSettings biddingContentSettings, int currentPage, int pageSize) {
        biddingContentSettingsMapper.selectCount(biddingContentSettings);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingContentSettingsMapper.selectByRowBounds(biddingContentSettings, bounds);
    }

    public Integer selectCount(BiddingContentSettings biddingContentSettings) {
        return biddingContentSettingsMapper.selectCount(biddingContentSettings);
    }

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }

    /**
     * 新增内容设置
     * @param map
     * @return
     */
    public Result insertSet(Map map) {
        try {
            String name = map.get("name").toString();
            String isNull = map.get("isNull").toString();
            String contentTypeId = map.get("contentTypeId").toString();
            String projectPhaseId = map.get("projectPhaseId").toString();
            BiddingContentSettings biddingContentSettings = new BiddingContentSettings();
            biddingContentSettings.setName(name);
            biddingContentSettings.setContentTypeId(contentTypeId);
            biddingContentSettings.setIsNull(isNull);
            biddingContentSettings.setProjectPhaseId(projectPhaseId);
            biddingContentSettings.setDelflag("0");
            int i = biddingContentSettingsMapper.selectCount(biddingContentSettings);
            if (i > 0) {
                return new Result<>(false, StatusCode.ERROR, "该内容已存在");
            }
            biddingContentSettings.setId(idWorker.nextId() + "");
            biddingContentSettings.setEnName(biddingContentSettings.getId());
            int insert = biddingContentSettingsMapper.insert(biddingContentSettings);
            if (insert > 0) {
                return new Result<>(true, StatusCode.OK, "添加成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
        }
        return new Result<>(false, StatusCode.ERROR, "添加失败");
    }

    /**
     * 根据ID删除内容设置信息
     * @param id
     * @return
     */
    public Result deleteById(String id) {
        try {

            BiddingContentSettings biddingContentSettings = biddingContentSettingsMapper.selectByPrimaryKey(id);
            if (biddingContentSettings==null||biddingContentSettings.getDelflag().equals("1")){
                return new Result<>(false, StatusCode.ERROR, "该条记录不存在");
            }
            biddingContentSettings.setDelflag("1");
            int i = biddingContentSettingsMapper.updateByPrimaryKeySelective(biddingContentSettings);
            if (i>0){
                return new Result<>(true, StatusCode.OK, "删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
        }
        return new Result<>(false, StatusCode.ERROR, "删除失败");
    }

    /**
     * 根据IDS批量删除内容设置信息
     * @param ids
     * @return
     */
    public Result deleteByIds(String ids) {
        try {
            String[] split = ids.split(",");
            for (int i = 0; i < split.length; i++) {
                String id=split[i];
                BiddingContentSettings biddingContentSettings = biddingContentSettingsMapper.selectByPrimaryKey(id);
                biddingContentSettings.setDelflag("1");
                int j = biddingContentSettingsMapper.updateByPrimaryKeySelective(biddingContentSettings);
            }
            return new Result<>(true, StatusCode.OK, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
        }
    }
}