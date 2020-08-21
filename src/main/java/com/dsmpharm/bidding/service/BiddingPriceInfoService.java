package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.controller.BiddingProductController;
import com.dsmpharm.bidding.mapper.*;
import com.dsmpharm.bidding.pojo.BiddingProjectBulid;
import com.dsmpharm.bidding.pojo.BiddingProjectType;
import com.dsmpharm.bidding.utils.IdWorker;
import com.dsmpharm.bidding.utils.PageResult;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/08/21
 */
@Service
public class BiddingPriceInfoService {

	@Resource
	private BiddingPriceInfoMapper biddingPriceInfoMapper;
	@Value("${upload.build}")
	private String uploadBuild;//上传文件保存的本地目录，使用@Value获取全局配置文件中配置的属性值
	@Value("${upload.doc_terpretation}")
	private String uploadDocTerpretation;//上传文件保存的本地目录，使用@Value获取全局配置文件中配置的属性值
	@Value("${upload.product_collection}")
	private String uploadProCollection;//上传文件保存的本地目录，使用@Value获取全局配置文件中配置的属性值
	@Value("${upload.strategy_analysis}")
	private String uploadStrategyAnalysis;//上传文件保存的本地目录，使用@Value获取全局配置文件中配置的属性值
	@Value("${upload.info_filling}")
	private String uploadInfoFilling;//上传文件保存的本地目录，使用@Value获取全局配置文件中配置的属性值
	@Value("${upload.official_notice}")
	private String uploadOfficialNotice;//上传文件保存的本地目录，使用@Value获取全局配置文件中配置的属性值
	@Value("${upload.project_summary}")
	private String uploadProjectSummary;//上传文件保存的本地目录，使用@Value获取全局配置文件中配置的属性值


	private static Logger log = LoggerFactory.getLogger(BiddingProductController.class);
	@Resource
	private BiddingProjectMapper biddingProjectMapper;
	@Resource
	private BiddingProjectTypeMapper biddingProjectTypeMapper;
	@Resource
	private BiddingContentSettingsMapper biddingContentSettingsMapper;
	@Resource
	private BiddingContentBakMapper biddingContentBakMapper;
	@Resource
	private BiddingProjectDataMapper biddingProjectDataMapper;
	@Resource
	private BiddingUserRoleMapper biddingUserRoleMapper;
	@Resource
	private AppFlowNodeMapper appFlowNodeMapper;
	@Resource
	private AppFlowApplyMapper appFlowApplyMapper;
	@Resource
	private AppFlowApprovalMapper appFlowApprovalMapper;
	@Resource
	private BiddingSettingsExtraMapper biddingSettingsExtraMapper;
	@Resource
	private BiddingProjectBulidMapper biddingProjectBulidMapper;
	@Resource
	private BiddingProjectProductMapper biddingProjectProductMapper;
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
	private BiddingFileAddcontentMapper biddingFileAddcontentMapper;

	@Resource
	private BiddingProductMapper biddingProductMapper;

	@Resource
	private BiddingUserProvinceMapper biddingUserProvinceMapper;
	@Resource
	private IdWorker idWorker;

	public Result databaseList(Map map, String userId) {

		try {
			Integer currentPage = Integer.valueOf(map.get("currentPage").toString());
			Integer pageSize = Integer.valueOf(map.get("pageSize").toString());
			String proId = map.get("proId").toString();
			String cityId = map.get("cityId").toString();
			String name = map.get("name").toString();
			String proLabel = map.get("proLabel").toString();
			String proTypeId = map.get("typeId").toString();
			String startTime = map.get("startTime").toString();
			String endTime = map.get("endTime").toString();
			Integer start = Integer.valueOf(startTime.replace("/", ""));
			Integer end = Integer.valueOf(endTime.replace("/", ""));
			List<Map> mapList = biddingProjectMapper.selectByDatebase(proId, cityId,name,proLabel,proTypeId,start,end);
			PageHelper.startPage(currentPage, pageSize);
			List<Map> maps = biddingProjectMapper.selectByNoDel(name, proId, userId);
			for (Map map1 : maps) {
				String typeId = null;
				String projectId = null;
				String isSkip = null;
				if (map1.get("id") != null) {
					projectId = map1.get("id").toString();
				}
				if (map1.get("type_id") != null) {
					typeId = map1.get("type_id").toString();
				}
				if (map1.get("is_skip") != null) {
					isSkip = map1.get("is_skip").toString();
				}
				BiddingProjectType biddingProjectType = biddingProjectTypeMapper.selectByPrimaryKey(typeId);
				if (biddingProjectType != null) {
					String projectPhaseId = biddingProjectType.getProjectPhaseId();
					if (isSkip.equals("0")) {
						projectPhaseId = biddingProjectType.getProjectPhaseId().replace("3,", "");
					}
					map1.put("projectPhaseId", projectPhaseId);
				}
				BiddingProjectBulid biddingProjectBulid = null;
				if (map1.get("project_phase_now") != null) {
					if ((map1.get("project_phase_now").toString()).equals("1")) {
						biddingProjectBulid = biddingProjectMapper.selectGoStatus1(projectId);
					} else if ((map1.get("project_phase_now").toString()).equals("2")) {
						biddingProjectBulid = biddingProjectMapper.selectGoStatus2(projectId);
					} else if ((map1.get("project_phase_now").toString()).equals("3")) {
						biddingProjectBulid = biddingProjectMapper.selectGoStatus3(projectId);
					} else if ((map1.get("project_phase_now").toString()).equals("4")) {
						biddingProjectBulid = biddingProjectMapper.selectGoStatus4(projectId);
					} else if ((map1.get("project_phase_now").toString()).equals("5")) {
						biddingProjectBulid = biddingProjectMapper.selectGoStatus5(projectId);
					} else if ((map1.get("project_phase_now").toString()).equals("6")) {
						biddingProjectBulid = biddingProjectMapper.selectGoStatus6(projectId);
					} else if ((map1.get("project_phase_now").toString()).equals("7")) {
						biddingProjectBulid = biddingProjectMapper.selectGoStatus7(projectId);
					}
				}
				if (biddingProjectBulid != null) {
					map1.put("goStatus", biddingProjectBulid.getGoStatus());
				}
			}
			PageInfo pageInfo = new PageInfo<>(maps);
			pageInfo.setTotal(mapList.size());
			PageResult pageResult = new PageResult(pageInfo.getTotal(), maps);
			return new Result<>(true, StatusCode.OK, "查询成功", pageResult);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
		}
	}
}