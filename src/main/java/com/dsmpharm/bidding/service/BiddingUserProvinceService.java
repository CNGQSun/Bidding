package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingUserProvinceMapper;
import com.dsmpharm.bidding.pojo.BiddingUserProvince;
import com.dsmpharm.bidding.utils.IdWorker;
import com.dsmpharm.bidding.utils.PageResult;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <br/>
 * Created by Grant on 2020/07/27
 */
@Service
public class BiddingUserProvinceService {

    @Resource
    private BiddingUserProvinceMapper biddingUserProvinceMapper;

    @Resource
    private IdWorker idWorker;

    /**
     * 添加用户省份，点击提交
     * @param map
     * @return
     */
    public Result insertSub(Map map) {
        String userId = map.get("userId").toString();
        Integer proId = Integer.valueOf(map.get("proId").toString());
        BiddingUserProvince biddingUserProvince=new BiddingUserProvince();
        biddingUserProvince.setUserId(userId);
        biddingUserProvince.setProId(proId);
        try {
            biddingUserProvince.setDelflag("0");
            int i = biddingUserProvinceMapper.selectCount(biddingUserProvince);
            if (i > 0) {
                return new Result<>(false, StatusCode.ERROR, "该用户省份信息已存在");
            }
            biddingUserProvince.setId(idWorker.nextId() + "");
            biddingUserProvince.setStatus("0");
            int insert = biddingUserProvinceMapper.insert(biddingUserProvince);
            if (insert > 0) {
                return new Result<>(true, StatusCode.OK, "添加成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器异常");
        }
        return new Result<>(false, StatusCode.ERROR, "添加失败");
    }

    /**
     * 添加用户省份，点击保存
     *
     * @param map
     * @return
     */
    public Result insertPro(Map map) {
        String userId = map.get("userId").toString();
        Integer proId = Integer.valueOf(map.get("proId").toString());
        BiddingUserProvince biddingUserProvince=new BiddingUserProvince();
        biddingUserProvince.setUserId(userId);
        biddingUserProvince.setProId(proId);
        try {
            biddingUserProvince.setDelflag("0");
            int i = biddingUserProvinceMapper.selectCount(biddingUserProvince);
            if (i > 0) {
                return new Result<>(false, StatusCode.ERROR, "该用户省份信息已存在");
            }
            biddingUserProvince.setId(idWorker.nextId() + "");
            biddingUserProvince.setStatus("1");
            int insert = biddingUserProvinceMapper.insert(biddingUserProvince);
            if (insert > 0) {
                return new Result<>(true, StatusCode.OK, "添加成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器异常");
        }
        return new Result<>(false, StatusCode.ERROR, "添加失败");
    }


    public List<BiddingUserProvince> selectAll() {
        return biddingUserProvinceMapper.selectAll();
    }

	/**
	 * 根据id查询用户省份信息
	 * @param id
	 * @return
	 */
    public Result findById(String id) {
        try {
            Map map = biddingUserProvinceMapper.selectById(id);
            return new Result<>(true, StatusCode.OK, "查询成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
        }
    }

	/**
	 * 根据id修改用户省份信息
	 * @param map
	 * @return
	 */
	public Result updateById(Map map) {
        String id = map.get("id").toString();
        String userId = map.get("userId").toString();
        Integer proId = Integer.valueOf(map.get("proId").toString());
        BiddingUserProvince biddingUserProvince=new BiddingUserProvince();
        biddingUserProvince.setId(id);
        biddingUserProvince.setUserId(userId);
        biddingUserProvince.setProId(proId);
		try {
            BiddingUserProvince biddingUserProvince1 = biddingUserProvinceMapper.selectByPrimaryKey(biddingUserProvince.getId());
            if (biddingUserProvince1==null||(biddingUserProvince1.getDelflag()).equals("1")) {
                return new Result<>(false, StatusCode.ERROR, "该条记录不存在");
            }
		    biddingUserProvince.setStatus("0");
			int i = biddingUserProvinceMapper.updateByPrimaryKeySelective(biddingUserProvince);
			if (i>0){
				return new Result<>(true, StatusCode.OK, "修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<>(true, StatusCode.ERROR, "服务器异常");
		}
		return new Result<>(true, StatusCode.ERROR, "修改失败");
    }

    public void delete(String id) {
        biddingUserProvinceMapper.deleteByPrimaryKey(id);
    }

    public List<BiddingUserProvince> list(BiddingUserProvince biddingUserProvince) {
        return biddingUserProvinceMapper.select(biddingUserProvince);
    }

    /**
     * 分页、条件查询所有用户省份信息
     * @param map
     * @return
     */
    public Result list(Map map) {
        try {
            Integer currentPage = Integer.valueOf(map.get("currentPage").toString());
            Integer pageSize = Integer.valueOf(map.get("pageSize").toString());
            String name = map.get("name").toString();
            String proId = map.get("proId").toString();
            List<Map> list=biddingUserProvinceMapper.selectList(name,proId);
            PageHelper.startPage(currentPage, pageSize);
            List<Map> users=biddingUserProvinceMapper.selectList(name,proId);
            PageInfo pageInfo = new PageInfo<>(users);
            pageInfo.setTotal(list.size());
            PageResult pageResult = new PageResult(pageInfo.getTotal(), users);
            return new Result(true, StatusCode.OK, "查询成功",pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "服务器错误");
        }
    }

    public Integer selectCount(BiddingUserProvince biddingUserProvince) {
        return biddingUserProvinceMapper.selectCount(biddingUserProvince);
    }

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }

	/**
	 * 根据ID删除用户省份 delflag置为1
	 * @param id
	 * @return
	 */
	public Result deleteById(String id) {
		try {
			BiddingUserProvince biddingUserProvince = biddingUserProvinceMapper.selectByPrimaryKey(id);
			if (biddingUserProvince==null||(biddingUserProvince.getDelflag()).equals("1")) {
				return new Result<>(false, StatusCode.ERROR, "该条记录不存在");
			}
			biddingUserProvince.setDelflag("1");
			int i = biddingUserProvinceMapper.updateByPrimaryKeySelective(biddingUserProvince);
			if (i>0){
				return new Result<>(true, StatusCode.OK, "删除成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<>(false, StatusCode.ERROR, "服务器异常");
		}
		return new Result<>(false, StatusCode.ERROR, "删除失败");
	}

    /**
     * 根据Id删除多个用户省份信息
     * @param ids
     * @return
     */
    public Result deleteByIds(String ids) {
        try {
            String[] split = ids.split(",");
            for (int i = 0; i < split.length; i++) {
                String id=split[i];
                BiddingUserProvince biddingUserProvince = biddingUserProvinceMapper.selectByPrimaryKey(id);
                biddingUserProvince.setDelflag("1");
                biddingUserProvinceMapper.updateByPrimaryKeySelective(biddingUserProvince);
            }
            return new Result<>(true, StatusCode.OK, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "删除失败");
        }
    }
}