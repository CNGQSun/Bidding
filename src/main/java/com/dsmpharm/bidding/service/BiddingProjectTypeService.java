package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingProjectTypeMapper;
import com.dsmpharm.bidding.pojo.BiddingProjectType;
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
 * Created by Grant on 2020/08/03
 */
@Service
public class BiddingProjectTypeService {
	private static Logger log = LoggerFactory.getLogger(BiddingProjectTypeService.class);

	@Resource
	private BiddingProjectTypeMapper biddingProjectTypeMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingProjectType biddingProjectType){
		biddingProjectType.setId(idWorker.nextId() + "");
		biddingProjectTypeMapper.insert(biddingProjectType);
	}

	public List<BiddingProjectType> selectAll() {
		return biddingProjectTypeMapper.selectAll();
	}

	public BiddingProjectType findById(String id){
		return biddingProjectTypeMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingProjectType biddingProjectType) {
		biddingProjectTypeMapper.updateByPrimaryKey(biddingProjectType);
	}

	public void delete(String id) {
		biddingProjectTypeMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingProjectType> list(BiddingProjectType biddingProjectType) {
		return biddingProjectTypeMapper.select(biddingProjectType);
	}

    public List<BiddingProjectType> list(BiddingProjectType biddingProjectType, int currentPage, int pageSize) {
		biddingProjectTypeMapper.selectCount(biddingProjectType);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingProjectTypeMapper.selectByRowBounds(biddingProjectType, bounds);
    }

	public Integer selectCount(BiddingProjectType biddingProjectType){
		return biddingProjectTypeMapper.selectCount(biddingProjectType);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }

	/**
	 * 获取所有未删除的项目类型
	 * @return
	 */
	public Result selectNoDel() {
		try {
			List<BiddingProjectType> projectTypes=biddingProjectTypeMapper.selectNoDel();
			if (projectTypes!=null){
				return new Result<>(true, StatusCode.OK, "查询成功",projectTypes);
			}
		} catch (Exception e) {
			log.error(e.toString(),e);
			return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
		}
		return new Result<>(false, StatusCode.ERROR, "查询失败");
	}
}