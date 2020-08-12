package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.*;
import com.dsmpharm.bidding.pojo.AppFlowApply;
import com.dsmpharm.bidding.pojo.AppFlowApproval;
import com.dsmpharm.bidding.pojo.AppFlowLine;
import com.dsmpharm.bidding.utils.DateUtils;
import com.dsmpharm.bidding.utils.IdWorker;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/08/10
 */
@Service
public class AppFlowApprovalService {

	@Resource
	private AppFlowApprovalMapper appFlowApprovalMapper;
	@Resource
	private AppFlowApplyMapper appFlowApplyMapper;
	@Resource
	private BiddingProjectMapper biddingProjectMapper;
	//立项
	@Resource
	private BiddingProjectBulidMapper biddingProjectBulidMapper;
	//文件解读
	@Resource
	private BiddingDocInterpretationMapper biddingDocInterpretationMapper;
	//竞品收集
	@Resource
	private BiddingProductCollectionMapper biddingProductCollectionMapper;
	//策略分析
	@Resource
	private BiddingStrategyAnalysisMapper biddingStrategyAnalysisMapper;
	//信息填报
	@Resource
	private BiddingInfoFillingMapper biddingInfoFillingMapper;
	//官方公告
	@Resource
	private BiddingOfficialNoticeMapper biddingOfficialNoticeMapper;
	//项目总结
	@Resource
	private BiddingProjectSummaryMapper biddingProjectSummaryMapper;
	@Resource
	private AppFlowLineMapper appFlowLineMapper;
	@Resource
	private IdWorker idWorker;

	public void insert(AppFlowApproval appFlowApproval){
		appFlowApproval.setId(idWorker.nextId() + "");
		appFlowApprovalMapper.insert(appFlowApproval);
	}

	public List<AppFlowApproval> selectAll() {
		return appFlowApprovalMapper.selectAll();
	}

	public AppFlowApproval findById(String id){
		return appFlowApprovalMapper.selectByPrimaryKey(id);
	}

	public void updateById(AppFlowApproval appFlowApproval) {
		appFlowApprovalMapper.updateByPrimaryKey(appFlowApproval);
	}

	public void delete(String id) {
		appFlowApprovalMapper.deleteByPrimaryKey(id);
	}

	public List<AppFlowApproval> list(AppFlowApproval appFlowApproval) {
		return appFlowApprovalMapper.select(appFlowApproval);
	}

    public List<AppFlowApproval> list(AppFlowApproval appFlowApproval, int currentPage, int pageSize) {
		appFlowApprovalMapper.selectCount(appFlowApproval);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return appFlowApprovalMapper.selectByRowBounds(appFlowApproval, bounds);
    }

	public Integer selectCount(AppFlowApproval appFlowApproval){
		return appFlowApprovalMapper.selectCount(appFlowApproval);
	}

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }

	public Result approvalById(Map map,String userId) {
		String approveId = map.get("approveId").toString();
		String approveResult = map.get("approveResult").toString();
		AppFlowApproval appFlowApproval=new AppFlowApproval();
		appFlowApproval.setId(approveId);
		AppFlowApproval appFlowApproval1 = appFlowApprovalMapper.selectOne(appFlowApproval);
		if (appFlowApproval1==null){
			return new Result<>(false, StatusCode.ERROR, "该条记录不存在");
		}
		appFlowApproval.setApproveResult(approveResult);
		//添加审批时间
		appFlowApproval.setApproveDate(DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss"));
		int i = appFlowApprovalMapper.updateByPrimaryKeySelective(appFlowApproval);
		AppFlowApply appFlowApply=new AppFlowApply();
		appFlowApply.setId(appFlowApproval1.getApplyId());
		AppFlowApply appFlowApply1 = appFlowApplyMapper.selectOne(appFlowApply);
		if (appFlowApply1==null){
			return new Result<>(false, StatusCode.ERROR, "审批失败");
		}
		//申请被驳回
		if (approveResult.equals("2")){

			//这条申请的状态就是被驳回，审批不通过
			appFlowApply1.setStatus("1");
			//根据申请表里的项目id去项目表里找到该项目当前所处阶段，处于哪个阶段就去对应的表里将其状态改为审批未通过
			//this is code

			return new Result<>(true, StatusCode.OK, "审批成功");
		}
		//申请通过，则需要看看是否还有其他节点，有其他节点则继续走，没有其他节点则
		//如上，根据申请表里的项目id去项目表里找到该项目当前所处阶段，处于哪个阶段就去对应的表里将其状态改为审批通过
		//先将当前节点+1
		String prevNode=(Integer.valueOf(appFlowApply1.getCurrentNode())+1)+"";
		AppFlowLine appFlowLine=new AppFlowLine();
		appFlowLine.setFlowId(appFlowApply1.getFlowId());
		appFlowLine.setPrevNodeId(prevNode);
		AppFlowLine appFlowLine1 = appFlowLineMapper.selectOne(appFlowLine);
		//如果appFlowLine1查不到记录，证明该次审核流程已经走完
		if (appFlowLine1==null){
			//申请表里审批状态设置为通过
			appFlowApply1.setStatus("0");
		}
		//如果查的到记录，说明还有审批流程没有走完
		//将当前节点设置为下一个节点
		appFlowApply1.setCurrentNode(prevNode);
		return null;

	}
}