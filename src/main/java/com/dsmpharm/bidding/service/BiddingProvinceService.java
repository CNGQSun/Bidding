package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingProvinceMapper;
import com.dsmpharm.bidding.pojo.BiddingProvince;
import com.dsmpharm.bidding.utils.IdWorker;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/07/27
 */
@Service
public class BiddingProvinceService {
	private static Logger log = LoggerFactory.getLogger(BiddingProvinceService.class);

	@Resource
	private BiddingProvinceMapper biddingProvinceMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingProvince biddingProvince){
		biddingProvinceMapper.insert(biddingProvince);
	}

	public List<BiddingProvince> selectAll() {
		return biddingProvinceMapper.selectAll();
	}

	public BiddingProvince findById(Integer id){
		return biddingProvinceMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingProvince biddingProvince) {
		biddingProvinceMapper.updateByPrimaryKey(biddingProvince);
	}

	public void delete(Integer id) {
		biddingProvinceMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingProvince> list(BiddingProvince biddingProvince) {
		return biddingProvinceMapper.select(biddingProvince);
	}

    public List<BiddingProvince> list(BiddingProvince biddingProvince, int currentPage, int pageSize) {
		biddingProvinceMapper.selectCount(biddingProvince);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingProvinceMapper.selectByRowBounds(biddingProvince, bounds);
    }

	public Integer selectCount(BiddingProvince biddingProvince){
		return biddingProvinceMapper.selectCount(biddingProvince);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }

	/**
	 * 查询全部省份信息
	 * @return
	 */
	public Result selectAllPro() {
		try {
			List<BiddingProvince> biddingProvinces = biddingProvinceMapper.selectAllNoDel();
			return 	new Result<>(true, StatusCode.OK, "查询成功", biddingProvinces);
		} catch (Exception e) {
			log.error(e.toString(),e);
			return 	new Result<>(false, StatusCode.ERROR, "服务器异常");
		}
	}
}