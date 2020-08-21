package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.*;
import com.dsmpharm.bidding.pojo.*;
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
	private AppFlowNodeMapper appFlowNodeMapper;
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

	public Result approvalById(Map map) {
		try {
			String approveId = map.get("approveId").toString();
			String approveResult = map.get("approveResult").toString();
			AppFlowApproval appFlowApproval=new AppFlowApproval();
			appFlowApproval.setId(approveId);
			AppFlowApproval appFlowApproval1 = appFlowApprovalMapper.selectOne(appFlowApproval);
			if (appFlowApproval1==null){
				return new Result<>(false, StatusCode.ERROR, "该条记录不存在");
			}
			//设置审核结果
			appFlowApproval1.setApproveResult(approveResult);
			//设置审批时间
			appFlowApproval1.setApproveDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			//更新审批记录
			appFlowApprovalMapper.updateByPrimaryKeySelective(appFlowApproval1);
			AppFlowApply appFlowApply=new AppFlowApply();
			appFlowApply.setId(appFlowApproval1.getApplyId());
			AppFlowApply appFlowApply1 = appFlowApplyMapper.selectOne(appFlowApply);
			if (appFlowApply1==null){
				return new Result<>(false, StatusCode.ERROR, "审批失败");
			}
			BiddingProject biddingProject = biddingProjectMapper.selectByPrimaryKey(appFlowApply1.getProjectId());
			//申请被驳回
			if (approveResult.equals("2")){

				//这条申请的状态就是被驳回，审批不通过
				appFlowApply1.setStatus("2");
				//更新申请表
				appFlowApplyMapper.updateByPrimaryKeySelective(appFlowApply1);
				//根据申请表里的项目id去项目表里找到该项目当前所处阶段，处于哪个阶段就去对应的表里将其状态改为审批未通过
				//this is code
				if (biddingProject==null){
					return new Result<>(false, StatusCode.ERROR, "没有找到该项目记录");
				}
				if (biddingProject.getProjectPhaseNow().equals("1")){
					BiddingProjectBulid biddingProjectBulid = biddingProjectBulidMapper.selectByPrimaryKey(biddingProject.getProjectBulidId());
					if (biddingProjectBulid==null){
						return new Result<>(false, StatusCode.ERROR, "没有找到该阶段记录");
					}
					biddingProjectBulid.setGoStatus("5");
					biddingProjectBulidMapper.updateByPrimaryKeySelective(biddingProjectBulid);
				}else if (biddingProject.getProjectPhaseNow().equals("2")){
					BiddingDocInterpretation biddingDocInterpretation = biddingDocInterpretationMapper.selectByPrimaryKey(biddingProject.getDocInterpretationId());
					if (biddingDocInterpretation==null){
						return new Result<>(false, StatusCode.ERROR, "没有找到该阶段记录");
					}
					biddingDocInterpretation.setGoStatus("5");
					biddingDocInterpretationMapper.updateByPrimaryKeySelective(biddingDocInterpretation);
				}else if (biddingProject.getProjectPhaseNow().equals("3")){
					BiddingProductCollection biddingProductCollection = biddingProductCollectionMapper.selectByPrimaryKey(biddingProject.getProductCollectionId());
					if (biddingProductCollection==null){
						return new Result<>(false, StatusCode.ERROR, "没有找到该阶段记录");
					}
					biddingProductCollection.setGoStatus("5");
					biddingProductCollectionMapper.updateByPrimaryKeySelective(biddingProductCollection);
				}else if (biddingProject.getProjectPhaseNow().equals("4")){
					BiddingStrategyAnalysis biddingStrategyAnalysis = biddingStrategyAnalysisMapper.selectByPrimaryKey(biddingProject.getStrategyAnalysisId());
					if (biddingStrategyAnalysis==null){
						return new Result<>(false, StatusCode.ERROR, "没有找到该阶段记录");
					}
					biddingStrategyAnalysis.setGoStatus("5");
					biddingStrategyAnalysisMapper.updateByPrimaryKeySelective(biddingStrategyAnalysis);
				}else if (biddingProject.getProjectPhaseNow().equals("5")){
					BiddingInfoFilling biddingInfoFilling = biddingInfoFillingMapper.selectByPrimaryKey(biddingProject.getInfoFillingId());
					if (biddingInfoFilling==null){
						return new Result<>(false, StatusCode.ERROR, "没有找到该阶段记录");
					}
					biddingInfoFilling.setGoStatus("5");
					biddingInfoFillingMapper.updateByPrimaryKeySelective(biddingInfoFilling);
				}else if (biddingProject.getProjectPhaseNow().equals("6")){
					BiddingOfficialNotice biddingOfficialNotice = biddingOfficialNoticeMapper.selectByPrimaryKey(biddingProject.getOfficialNoticeId());
					if (biddingOfficialNotice==null){
						return new Result<>(false, StatusCode.ERROR, "没有找到该阶段记录");
					}
					biddingOfficialNotice.setGoStatus("5");
					biddingOfficialNoticeMapper.updateByPrimaryKeySelective(biddingOfficialNotice);
				}else if (biddingProject.getProjectPhaseNow().equals("7")){
					BiddingProjectSummary biddingProjectSummary = biddingProjectSummaryMapper.selectByPrimaryKey(biddingProject.getProjectSummaryId());
					if (biddingProjectSummary==null){
						return new Result<>(false, StatusCode.ERROR, "没有找到该阶段记录");
					}
					biddingProjectSummary.setGoStatus("5");
					biddingProjectSummaryMapper.updateByPrimaryKeySelective(biddingProjectSummary);
				}
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
				appFlowApply1.setStatus("1");
				//更新审批表
				appFlowApplyMapper.updateByPrimaryKeySelective(appFlowApply1);
				Integer newNow=0;
				if (biddingProject.getProjectPhaseNow().equals("1")){
					BiddingProjectBulid biddingProjectBulid = biddingProjectBulidMapper.selectByPrimaryKey(biddingProject.getProjectBulidId());
					if (biddingProjectBulid==null){
						return new Result<>(false, StatusCode.ERROR, "没有找到该阶段记录");
					}
					newNow = (Integer.valueOf(biddingProject.getProjectPhaseNow()))+1;
					biddingProjectBulid.setGoStatus("4");
					biddingProjectBulidMapper.updateByPrimaryKeySelective(biddingProjectBulid);
				}else if (biddingProject.getProjectPhaseNow().equals("2")){
					BiddingDocInterpretation biddingDocInterpretation = biddingDocInterpretationMapper.selectByPrimaryKey(biddingProject.getDocInterpretationId());
					if (biddingDocInterpretation==null){
						return new Result<>(false, StatusCode.ERROR, "没有找到该阶段记录");
					}
					if (biddingProject.getIsSkip().equals("0")){
						newNow= (Integer.valueOf(biddingProject.getProjectPhaseNow()))+2;
					}else {
						newNow = (Integer.valueOf(biddingProject.getProjectPhaseNow()))+1;
					}
					biddingDocInterpretation.setGoStatus("4");
					biddingDocInterpretationMapper.updateByPrimaryKeySelective(biddingDocInterpretation);
				}else if (biddingProject.getProjectPhaseNow().equals("3")){
					BiddingProductCollection biddingProductCollection = biddingProductCollectionMapper.selectByPrimaryKey(biddingProject.getProductCollectionId());
					if (biddingProductCollection==null){
						return new Result<>(false, StatusCode.ERROR, "没有找到该阶段记录");
					}
					newNow = (Integer.valueOf(biddingProject.getProjectPhaseNow()))+1;
					biddingProductCollection.setGoStatus("4");
					biddingProductCollectionMapper.updateByPrimaryKeySelective(biddingProductCollection);
				}else if (biddingProject.getProjectPhaseNow().equals("4")){
					BiddingStrategyAnalysis biddingStrategyAnalysis = biddingStrategyAnalysisMapper.selectByPrimaryKey(biddingProject.getStrategyAnalysisId());
					if (biddingStrategyAnalysis==null){
						return new Result<>(false, StatusCode.ERROR, "没有找到该阶段记录");
					}
					newNow = (Integer.valueOf(biddingProject.getProjectPhaseNow()))+1;
					biddingStrategyAnalysis.setGoStatus("4");
					biddingStrategyAnalysisMapper.updateByPrimaryKeySelective(biddingStrategyAnalysis);
				}else if (biddingProject.getProjectPhaseNow().equals("5")){
					BiddingInfoFilling biddingInfoFilling = biddingInfoFillingMapper.selectByPrimaryKey(biddingProject.getInfoFillingId());
					if (biddingInfoFilling==null){
						return new Result<>(false, StatusCode.ERROR, "没有找到该阶段记录");
					}
					newNow = (Integer.valueOf(biddingProject.getProjectPhaseNow()))+1;
					biddingInfoFilling.setGoStatus("4");
					biddingInfoFillingMapper.updateByPrimaryKeySelective(biddingInfoFilling);
				}else if (biddingProject.getProjectPhaseNow().equals("6")){
					BiddingOfficialNotice biddingOfficialNotice = biddingOfficialNoticeMapper.selectByPrimaryKey(biddingProject.getOfficialNoticeId());
					if (biddingOfficialNotice==null){
						return new Result<>(false, StatusCode.ERROR, "没有找到该阶段记录");
					}
					newNow = (Integer.valueOf(biddingProject.getProjectPhaseNow()))+1;
					biddingOfficialNotice.setGoStatus("4");
					biddingOfficialNoticeMapper.updateByPrimaryKeySelective(biddingOfficialNotice);
				}else if (biddingProject.getProjectPhaseNow().equals("7")){
					BiddingProjectSummary biddingProjectSummary = biddingProjectSummaryMapper.selectByPrimaryKey(biddingProject.getProjectSummaryId());
					if (biddingProjectSummary==null){
						return new Result<>(false, StatusCode.ERROR, "没有找到该阶段记录");
					}
					newNow = (Integer.valueOf(biddingProject.getProjectPhaseNow()))+1;
					biddingProjectSummary.setGoStatus("4");
					biddingProjectSummaryMapper.updateByPrimaryKeySelective(biddingProjectSummary);
				}
				biddingProject.setProjectPhaseNow(newNow+"");
				biddingProjectMapper.updateByPrimaryKeySelective(biddingProject);
				return new Result<>(true, StatusCode.OK, "审批成功");
			}
			//如果查的到记录，说明还有审批流程没有走完
			//将当前节点设置为下一个节点
			appFlowApply1.setCurrentNode(prevNode);
			//更新审批表里的数据
			appFlowApplyMapper.updateByPrimaryKeySelective(appFlowApply1);
			//查到下一个审批用户
			BiddingUser biddingUser = appFlowNodeMapper.selectAppUser(appFlowApply1.getFlowId(), appFlowApply1.getCurrentNode());
			AppFlowApproval appFlowApproval2=new AppFlowApproval();
			//查找下一个审批用户
			appFlowApproval2 = new AppFlowApproval();
			appFlowApproval2.setId(idWorker.nextId() + "");
			appFlowApproval2.setProjectPhaseId(appFlowApproval1.getProjectPhaseId());
			appFlowApproval2.setDelflag("0");
			String nextNode = (Integer.valueOf(appFlowApply1.getCurrentNode()) + 1) + "";
			appFlowApproval2.setFlowNodeId(nextNode);
			appFlowApproval2.setApplyId(appFlowApply1.getId());
			appFlowApproval2.setApproveResult("0");
			appFlowApproval2.setUserId(biddingUser.getId());
			//往审批表里插数据，为了后续查看是否有待审批的记录
			appFlowApprovalMapper.insert(appFlowApproval2);
			return new Result<>(true, StatusCode.OK, "审批成功");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
		}
	}
}