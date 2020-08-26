package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.controller.BiddingProductController;
import com.dsmpharm.bidding.mapper.*;
import com.dsmpharm.bidding.pojo.*;
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

    /**
     * 资料库中项目展示
     *
     * @param map
     * @param userId
     * @return
     */
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
            Integer end = null;
            Integer start = null;
            if (startTime != null && !startTime.equals("")) {
                start = Integer.valueOf(startTime.replace("/", ""));
            }
            if (endTime != null && !endTime.equals("")) {
                end = Integer.valueOf(endTime.replace("/", ""));
            }
            List<Map> mapList = biddingProjectMapper.selectByDatebase(proId, cityId, name, proLabel, proTypeId, start, end);
            PageHelper.startPage(currentPage, pageSize);
            List<Map> maps = biddingProjectMapper.selectByDatebase(proId, cityId, name, proLabel, proTypeId, start, end);
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
                BiddingDocInterpretation biddingDocInterpretation = null;
                BiddingProductCollection biddingProductCollection = null;
                BiddingStrategyAnalysis biddingStrategyAnalysis = null;
                BiddingInfoFilling biddingInfoFilling = null;
                BiddingOfficialNotice biddingOfficialNotice = null;
                BiddingProjectSummary biddingProjectSummary = null;
                if (map1.get("project_phase_now") != null) {
                    if ((map1.get("project_phase_now").toString()).equals("1")) {
                        biddingProjectBulid = biddingProjectMapper.selectGoStatus1(projectId);
                    } else if ((map1.get("project_phase_now").toString()).equals("2")) {
                        biddingDocInterpretation = biddingProjectMapper.selectGoStatus2(projectId);
                    } else if ((map1.get("project_phase_now").toString()).equals("3")) {
                        biddingProductCollection = biddingProjectMapper.selectGoStatus3(projectId);
                    } else if ((map1.get("project_phase_now").toString()).equals("4")) {
                        biddingStrategyAnalysis = biddingProjectMapper.selectGoStatus4(projectId);
                    } else if ((map1.get("project_phase_now").toString()).equals("5")) {
                        biddingInfoFilling = biddingProjectMapper.selectGoStatus5(projectId);
                    } else if ((map1.get("project_phase_now").toString()).equals("6")) {
                        biddingOfficialNotice = biddingProjectMapper.selectGoStatus6(projectId);
                    } else if ((map1.get("project_phase_now").toString()).equals("7")) {
                        biddingProjectSummary = biddingProjectMapper.selectGoStatus7(projectId);
                    }
                }
                if (biddingProjectBulid != null) {
                    map1.put("goStatus", biddingProjectBulid.getGoStatus());
                } else if (biddingDocInterpretation != null) {
                    map1.put("goStatus", biddingDocInterpretation.getGoStatus());
                } else if (biddingProductCollection != null) {
                    map1.put("goStatus", biddingProductCollection.getGoStatus());
                } else if (biddingStrategyAnalysis != null) {
                    map1.put("goStatus", biddingStrategyAnalysis.getGoStatus());
                } else if (biddingInfoFilling != null) {
                    map1.put("goStatus", biddingInfoFilling.getGoStatus());
                } else if (biddingOfficialNotice != null) {
                    map1.put("goStatus", biddingOfficialNotice.getGoStatus());
                } else if (biddingProjectSummary != null) {
                    map1.put("goStatus", biddingProjectSummary.getGoStatus());
                }
            }
            PageInfo pageInfo = new PageInfo<>(maps);
            pageInfo.setTotal(mapList.size());
            PageResult pageResult = new PageResult(pageInfo.getTotal(), maps);
            return new Result<>(true, StatusCode.OK, "查询成功", pageResult);
        } catch (NumberFormatException e) {
            log.error(e.toString(),e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }

    public Result databaseAppealList(Map map, String userId) {
        try {
            Integer currentPage = Integer.valueOf(map.get("currentPage").toString());
            Integer pageSize = Integer.valueOf(map.get("pageSize").toString());
            String qualityLevel = map.get("qualityLevel").toString();
            String proId = map.get("proId").toString();
            String productId = map.get("productId").toString();
            List<Map> mapList = biddingProjectMapper.selectByAppeal(qualityLevel, proId, productId);
            PageHelper.startPage(currentPage, pageSize);
            List<Map> maps = biddingProjectMapper.selectByAppeal(qualityLevel, proId, productId);
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
                BiddingDocInterpretation biddingDocInterpretation = null;
                BiddingProductCollection biddingProductCollection = null;
                BiddingStrategyAnalysis biddingStrategyAnalysis = null;
                BiddingInfoFilling biddingInfoFilling = null;
                BiddingOfficialNotice biddingOfficialNotice = null;
                BiddingProjectSummary biddingProjectSummary = null;
                if (map1.get("project_phase_now") != null) {
                    if ((map1.get("project_phase_now").toString()).equals("1")) {
                        biddingProjectBulid = biddingProjectMapper.selectGoStatus1(projectId);
                    } else if ((map1.get("project_phase_now").toString()).equals("2")) {
                        biddingDocInterpretation = biddingProjectMapper.selectGoStatus2(projectId);
                    } else if ((map1.get("project_phase_now").toString()).equals("3")) {
                        biddingProductCollection = biddingProjectMapper.selectGoStatus3(projectId);
                    } else if ((map1.get("project_phase_now").toString()).equals("4")) {
                        biddingStrategyAnalysis = biddingProjectMapper.selectGoStatus4(projectId);
                    } else if ((map1.get("project_phase_now").toString()).equals("5")) {
                        biddingInfoFilling = biddingProjectMapper.selectGoStatus5(projectId);
                    } else if ((map1.get("project_phase_now").toString()).equals("6")) {
                        biddingOfficialNotice = biddingProjectMapper.selectGoStatus6(projectId);
                    } else if ((map1.get("project_phase_now").toString()).equals("7")) {
                        biddingProjectSummary = biddingProjectMapper.selectGoStatus7(projectId);
                    }
                }
                if (biddingProjectBulid != null) {
                    map1.put("goStatus", biddingProjectBulid.getGoStatus());
                } else if (biddingDocInterpretation != null) {
                    map1.put("goStatus", biddingDocInterpretation.getGoStatus());
                } else if (biddingProductCollection != null) {
                    map1.put("goStatus", biddingProductCollection.getGoStatus());
                } else if (biddingStrategyAnalysis != null) {
                    map1.put("goStatus", biddingStrategyAnalysis.getGoStatus());
                } else if (biddingInfoFilling != null) {
                    map1.put("goStatus", biddingInfoFilling.getGoStatus());
                } else if (biddingOfficialNotice != null) {
                    map1.put("goStatus", biddingOfficialNotice.getGoStatus());
                } else if (biddingProjectSummary != null) {
                    map1.put("goStatus", biddingProjectSummary.getGoStatus());
                }
            }
            PageInfo pageInfo = new PageInfo<>(maps);
            pageInfo.setTotal(mapList.size());
            PageResult pageResult = new PageResult(pageInfo.getTotal(), maps);
            return new Result<>(true, StatusCode.OK, "查询成功", pageResult);
        } catch (NumberFormatException e) {
            log.error(e.toString(),e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }

    /**
     * 首页地图展示
     *
     * @param map
     * @return
     */
    public Result findIndexInfo(Map map) {
        try {
            String productEnName = map.get("productEnName").toString();
            List<BiddingPriceInfo> priceInfos = biddingPriceInfoMapper.selectByproductEn(productEnName);
            return new Result<>(true, StatusCode.OK, "查询成功",priceInfos);
        } catch (Exception e) {
            log.error(e.toString(),e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }
}