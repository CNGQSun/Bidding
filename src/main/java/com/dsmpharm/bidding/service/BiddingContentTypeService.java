package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingContentTypeMapper;
import com.dsmpharm.bidding.pojo.BiddingContentType;
import com.dsmpharm.bidding.utils.IdWorker;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/03
 */
@Service
public class BiddingContentTypeService {

	@Resource
	private BiddingContentTypeMapper biddingContentTypeMapper;

	@Resource
	private IdWorker idWorker;

	public void insert(BiddingContentType biddingContentType){
		biddingContentType.setId(idWorker.nextId() + "");
		biddingContentTypeMapper.insert(biddingContentType);
	}

	public List<BiddingContentType> selectAll() {
		return biddingContentTypeMapper.selectAll();
	}

	public BiddingContentType findById(String id){
		return biddingContentTypeMapper.selectByPrimaryKey(id);
	}

	public void updateById(BiddingContentType biddingContentType) {
		biddingContentTypeMapper.updateByPrimaryKey(biddingContentType);
	}

	public void delete(String id) {
		biddingContentTypeMapper.deleteByPrimaryKey(id);
	}

	public List<BiddingContentType> list(BiddingContentType biddingContentType) {
		return biddingContentTypeMapper.select(biddingContentType);
	}

    public List<BiddingContentType> list(BiddingContentType biddingContentType, int currentPage, int pageSize) {
		biddingContentTypeMapper.selectCount(biddingContentType);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingContentTypeMapper.selectByRowBounds(biddingContentType, bounds);
    }

	public Integer selectCount(BiddingContentType biddingContentType){
		return biddingContentTypeMapper.selectCount(biddingContentType);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }

	/**
	 * 查询全部内容类型
	 * @return
	 */
	public Result selectAllNoDel() {
		try {
			List<BiddingContentType> list=biddingContentTypeMapper.selectAllNoDel();
			if (list!=null){
				return new Result<>(true, StatusCode.OK, "查询成功", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
		}
		return new Result<>(false, StatusCode.ERROR, "查询失败");
	}
}