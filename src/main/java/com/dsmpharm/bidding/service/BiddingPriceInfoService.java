package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.controller.BiddingProductController;
import com.dsmpharm.bidding.entity.*;
import com.dsmpharm.bidding.mapper.*;
import com.dsmpharm.bidding.pojo.*;
import com.dsmpharm.bidding.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <br/>
 * Created by Grant on 2020/08/21
 */
@Service
public class BiddingPriceInfoService {

    @Value("${upload.base}")
    private String uploadBase;

    @Resource
    private BiddingPriceInfoMapper biddingPriceInfoMapper;

    private static Logger log = LoggerFactory.getLogger(BiddingProductController.class);
    @Resource
    private BiddingProjectMapper biddingProjectMapper;
    @Resource
    private BiddingProjectTypeMapper biddingProjectTypeMapper;
    @Resource
    private BiddingProvinceMapper biddingProvinceMapper;
    @Resource
    private BiddingCityMapper biddingCityMapper;
    @Resource
    private BiddingProductMapper biddingProductMapper;
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
            log.error(e.toString(), e);
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
            log.error(e.toString(), e);
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
            List<Map> priceInfos = biddingPriceInfoMapper.selectByproductEn(productEnName);
            return new Result<>(true, StatusCode.OK, "查询成功", priceInfos);
        } catch (Exception e) {
            log.error(e.toString(), e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }

    /**
     * @param projectId
     * @param response
     * @return
     */
    public Result exportProject(String projectId, HttpServletResponse response) {
        BiddingProject biddingProject = biddingProjectMapper.selectByPrimaryKey(projectId);
        BiddingProjectType biddingProjectType = biddingProjectTypeMapper.selectByPrimaryKey(biddingProject.getTypeId());
        BiddingProjectBulid biddingProjectBulid = biddingProjectBulidMapper.selectByPrimaryKey(biddingProject.getProjectBulidId());
        String proPhase = biddingProjectType.getProjectPhaseId();
        if (biddingProject.getIsSkip().equals("0")) {
            if (proPhase.contains("3")) {
                proPhase = proPhase.replace(",3", "");
            }
        }
        //多个sheet页同时导出
        String DBType = "SQLServer2008";
        String docPath1 = uploadBase + projectId + "/";
        File docPath = new File(docPath1);
        if (!docPath.exists() && !docPath.isDirectory()) {
            docPath.mkdirs();
        }
        String exportFilePath3 = uploadBase + projectId + "/" + biddingProjectBulid.getName() + ".xls";
        String[] split = proPhase.split(",");
        String[] sheetNameArr = new String[split.length];
        String[] tableTitleArr = new String[split.length];
        String[] headTablesColumnsNameArr = new String[split.length];
        String[] tablesColumnNameArr = new String[split.length];
        List<Object> list = new ArrayList<Object>();
        List<String> list1 = null;
        for (int i = 0; i < split.length; i++) {
            String phaseId = split[i];
            if (phaseId.equals("1")) {
                List<BiddingProjectBulidDo> mapBuildList = biddingProjectMapper.selectBuildList(projectId);
                if (mapBuildList.get(0).getTypeId() != null && !mapBuildList.get(0).getTypeId().equals("")) {
                    BiddingProjectType biddingProjectType1 = biddingProjectTypeMapper.selectByPrimaryKey(mapBuildList.get(0).getTypeId());
                    mapBuildList.get(0).setTypeId(biddingProjectType1.getName());
                }
                if (mapBuildList.get(0).getProvinceId() != null && !mapBuildList.get(0).getProvinceId().equals("")) {
                    BiddingProvince biddingProvince = biddingProvinceMapper.selectByPrimaryKey(mapBuildList.get(0).getProvinceId());
                    mapBuildList.get(0).setProvinceId(biddingProvince.getProName());
                }
                if (mapBuildList.get(0).getCityId() != null && !mapBuildList.get(0).getCityId().equals("")) {
                    String citys = mapBuildList.get(0).getCityId();
                    String[] split1 = citys.split(",");
                    String cityNames = "";
                    for (int j = 0; j < split1.length; j++) {
                        BiddingCity biddingCity = biddingCityMapper.selectByPrimaryKey(split1[j]);
                        cityNames += biddingCity.getCityName() + ",";
                    }
                    cityNames = cityNames.substring(0, cityNames.length() - 1);
                    mapBuildList.get(0).setCityId(cityNames);
                }
                if (mapBuildList.get(0).getProductId() != null && !mapBuildList.get(0).getProductId().equals("")) {
                    String productIds = mapBuildList.get(0).getProductId();
                    String[] split1 = productIds.split(",");
                    String productNames = "";
                    for (int j = 0; j < split1.length; j++) {
                        BiddingProduct biddingProduct = biddingProductMapper.selectByPrimaryKey(split1[j]);
                        productNames += biddingProduct.getEnName() + ",";
                    }
                    productNames = productNames.substring(0, productNames.length() - 1);
                    mapBuildList.get(0).setProductId(productNames);
                }
                sheetNameArr[i] = "立项";
                tableTitleArr[i] = "立项表";
                headTablesColumnsNameArr[i] = "文件发布时间,项目类型,项目名称,项目来源,省份,城市,产品,正式稿文件,征求稿文件,文件解读时间,递交/填报资料时间,公示期,申诉期,公告期,项目标签,意见";
                tablesColumnNameArr[i] = "docPublicTime,typeId,name,source,provinceId,cityId,productId,fileFormal,fileAsk,docInterTime,submitTime,publicTime,appealTime,noticeTime,proLabel,suggestion";
                String destPath = biddingProject.getBasePath() + biddingProject.getId() + "/立项/";
                //获取额外设置的内容
                list1 = new ArrayList<>();
                List<Map> addContent = biddingProjectMapper.selectAddContent(projectId, biddingProject.getProjectBulidId());
                int index1 = 1;
                for (Map map1 : addContent) {

                    if (map1.get("typeId").toString().equals("4")) {
                        BiddingFileAddcontent contentExtraValue = biddingFileAddcontentMapper.selectByPrimaryKey(map1.get("contentExtraValue").toString());
                        list1.add(contentExtraValue.getFilePath());
                        File file = new File(contentExtraValue.getFilePath());
                        headTablesColumnsNameArr[i] += "," + map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i] += ",value" + index1;
                        if (index1 == 1) {
                            mapBuildList.get(0).setValue1(file.getName());
                        } else if (index1 == 2) {
                            mapBuildList.get(0).setValue2(file.getName());
                        } else if (index1 == 3) {
                            mapBuildList.get(0).setValue3(file.getName());
                        } else if (index1 == 4) {
                            mapBuildList.get(0).setValue4(file.getName());
                        } else if (index1 == 5) {
                            mapBuildList.get(0).setValue5(file.getName());
                        } else if (index1 == 6) {
                            mapBuildList.get(0).setValue6(file.getName());
                        } else if (index1 == 7) {
                            mapBuildList.get(0).setValue7(file.getName());
                        } else if (index1 == 8) {
                            mapBuildList.get(0).setValue8(file.getName());
                        } else if (index1 == 9) {
                            mapBuildList.get(0).setValue9(file.getName());
                        } else if (index1 == 10) {
                            mapBuildList.get(0).setValue10(file.getName());
                        }
                        index1++;
                    } else {
                        headTablesColumnsNameArr[i] += "," + map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i] += ",value" + index1;
                        if (index1 == 1) {
                            mapBuildList.get(0).setValue1(map1.get("contentExtraValue").toString());
                        } else if (index1 == 2) {
                            mapBuildList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        } else if (index1 == 3) {
                            mapBuildList.get(0).setValue3(map1.get("contentExtraValue").toString());
                        } else if (index1 == 4) {
                            mapBuildList.get(0).setValue4(map1.get("contentExtraValue").toString());
                        } else if (index1 == 5) {
                            mapBuildList.get(0).setValue5(map1.get("contentExtraValue").toString());
                        } else if (index1 == 6) {
                            mapBuildList.get(0).setValue6(map1.get("contentExtraValue").toString());
                        } else if (index1 == 7) {
                            mapBuildList.get(0).setValue7(map1.get("contentExtraValue").toString());
                        } else if (index1 == 8) {
                            mapBuildList.get(0).setValue8(map1.get("contentExtraValue").toString());
                        } else if (index1 == 9) {
                            mapBuildList.get(0).setValue9(map1.get("contentExtraValue").toString());
                        } else if (index1 == 10) {
                            mapBuildList.get(0).setValue10(map1.get("contentExtraValue").toString());
                        }
                        index1++;
                    }
                }
                if (list1 != null && list1.size() > 0) {
                    for (String srcPath : list1) {
                        File file = new File(srcPath);
                        try {
                            FileUtil.copyFile(srcPath, destPath + file.getName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //获取内容设置表新增内容
                List<Map> mapSetting = biddingProjectMapper.selectSetting(projectId, phaseId);
                int index11 = 11;
                for (Map map : mapSetting) {

                    headTablesColumnsNameArr[i] += "," + map.get("contentName").toString();
                    tablesColumnNameArr[i] += ",value" + index11;
                    if (index11 == 11) {
                        mapBuildList.get(0).setValue11(map.get("contentValue").toString());
                    } else if (index11 == 12) {
                        mapBuildList.get(0).setValue12(map.get("contentValue").toString());
                    } else if (index11 == 13) {
                        mapBuildList.get(0).setValue13(map.get("contentValue").toString());
                    } else if (index11 == 14) {
                        mapBuildList.get(0).setValue14(map.get("contentValue").toString());
                    } else if (index11 == 15) {
                        mapBuildList.get(0).setValue15(map.get("contentValue").toString());
                    } else if (index11 == 16) {
                        mapBuildList.get(0).setValue16(map.get("contentValue").toString());
                    } else if (index11 == 17) {
                        mapBuildList.get(0).setValue17(map.get("contentValue").toString());
                    } else if (index11 == 18) {
                        mapBuildList.get(0).setValue18(map.get("contentValue").toString());
                    } else if (index11 == 19) {
                        mapBuildList.get(0).setValue19(map.get("contentValue").toString());
                    } else if (index11 == 20) {
                        mapBuildList.get(0).setValue20(map.get("contentValue").toString());
                    }
                    index11++;
                }
                list.add(mapBuildList);

            } else if (phaseId.equals("2")) {
                List<BiddingDocInterpretationDo> mapDocInterpretationList = biddingProjectMapper.selectInterpretationList(projectId);
                sheetNameArr[i] = "文件解读";
                tableTitleArr[i] = "文件解读表";
                headTablesColumnsNameArr[i] = "参加招标的品种,实施范围,不参加本次招标的原因,执行时间范围起始,执行时间范围终止,通用名,规格,剂型,质量层次,限价制定,限价的参考值,限价的说明,采购规则类型,采购规则,报价,行业影响,公司自身产品影响,征求稿意见,征求稿意见文件,意见填写";
                tablesColumnNameArr[i] = "type,range,noReason,timeRangeStart,timeRangeEnd,commonName,standards,dosageForm,qualityLevel,priceLimit,priceLimitReference,priceLimitExplain,fileBuyRulestag,fileBuyRules,fileQuotedPrice,industryInfluence,selfInfluence,solicitingOpinions,fileSolicitingOpinions,suggestion";
                String destPath = biddingProject.getBasePath() + biddingProject.getId() + "/文件解读/";
                //获取额外设置的内容
                list1 = new ArrayList<>();
                List<Map> addContent = biddingProjectMapper.selectAddContent(projectId, biddingProject.getDocInterpretationId());
                int index1 = 1;
                for (Map map1 : addContent) {

                    if (map1.get("typeId").toString().equals("4")) {
                        BiddingFileAddcontent contentExtraValue = biddingFileAddcontentMapper.selectByPrimaryKey(map1.get("contentExtraValue").toString());
                        list1.add(contentExtraValue.getFilePath());
                        File file = new File(contentExtraValue.getFilePath());
                        headTablesColumnsNameArr[i] += "," + map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i] += ",value" + index1;
                        if (index1 == 1) {
                            mapDocInterpretationList.get(0).setValue1(file.getName());
                        } else if (index1 == 2) {
                            mapDocInterpretationList.get(0).setValue2(file.getName());
                        } else if (index1 == 3) {
                            mapDocInterpretationList.get(0).setValue3(file.getName());
                        } else if (index1 == 4) {
                            mapDocInterpretationList.get(0).setValue4(file.getName());
                        } else if (index1 == 5) {
                            mapDocInterpretationList.get(0).setValue5(file.getName());
                        } else if (index1 == 6) {
                            mapDocInterpretationList.get(0).setValue6(file.getName());
                        } else if (index1 == 7) {
                            mapDocInterpretationList.get(0).setValue7(file.getName());
                        } else if (index1 == 8) {
                            mapDocInterpretationList.get(0).setValue8(file.getName());
                        } else if (index1 == 9) {
                            mapDocInterpretationList.get(0).setValue9(file.getName());
                        } else if (index1 == 10) {
                            mapDocInterpretationList.get(0).setValue10(file.getName());
                        }
                        index1++;
                    } else {
                        headTablesColumnsNameArr[i] += "," + map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i] += ",value" + index1;
                        if (index1 == 1) {

                            mapDocInterpretationList.get(0).setValue1(map1.get("contentExtraValue").toString());
                        } else if (index1 == 2) {
                            mapDocInterpretationList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        } else if (index1 == 3) {
                            mapDocInterpretationList.get(0).setValue3(map1.get("contentExtraValue").toString());
                        } else if (index1 == 4) {
                            mapDocInterpretationList.get(0).setValue4(map1.get("contentExtraValue").toString());
                        } else if (index1 == 5) {
                            mapDocInterpretationList.get(0).setValue5(map1.get("contentExtraValue").toString());
                        } else if (index1 == 6) {
                            mapDocInterpretationList.get(0).setValue6(map1.get("contentExtraValue").toString());
                        } else if (index1 == 7) {
                            mapDocInterpretationList.get(0).setValue7(map1.get("contentExtraValue").toString());
                        } else if (index1 == 8) {
                            mapDocInterpretationList.get(0).setValue8(map1.get("contentExtraValue").toString());
                        } else if (index1 == 9) {
                            mapDocInterpretationList.get(0).setValue9(map1.get("contentExtraValue").toString());
                        } else if (index1 == 10) {
                            mapDocInterpretationList.get(0).setValue10(map1.get("contentExtraValue").toString());
                        }
                        index1++;
                    }
                }
                if (list1 != null && list1.size() > 0) {
                    for (String srcPath : list1) {
                        File file = new File(srcPath);
                        try {
                            FileUtil.copyFile(srcPath, destPath + file.getName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //获取内容设置表新增内容
                List<Map> mapSetting = biddingProjectMapper.selectSetting(projectId, phaseId);
                int index11 = 11;
                for (Map map : mapSetting) {
                    headTablesColumnsNameArr[i] += "," + map.get("contentName").toString();
                    tablesColumnNameArr[i] += ",value" + index11;
                    if (index11 == 11) {
                        mapDocInterpretationList.get(0).setValue11(map.get("contentValue").toString());
                    } else if (index11 == 12) {
                        mapDocInterpretationList.get(0).setValue12(map.get("contentValue").toString());
                    } else if (index11 == 13) {
                        mapDocInterpretationList.get(0).setValue13(map.get("contentValue").toString());
                    } else if (index11 == 14) {
                        mapDocInterpretationList.get(0).setValue14(map.get("contentValue").toString());
                    } else if (index11 == 15) {
                        mapDocInterpretationList.get(0).setValue15(map.get("contentValue").toString());
                    } else if (index11 == 16) {
                        mapDocInterpretationList.get(0).setValue16(map.get("contentValue").toString());
                    } else if (index11 == 17) {
                        mapDocInterpretationList.get(0).setValue17(map.get("contentValue").toString());
                    } else if (index11 == 18) {
                        mapDocInterpretationList.get(0).setValue18(map.get("contentValue").toString());
                    } else if (index11 == 19) {
                        mapDocInterpretationList.get(0).setValue19(map.get("contentValue").toString());
                    } else if (index11 == 20) {
                        mapDocInterpretationList.get(0).setValue20(map.get("contentValue").toString());
                    }
                    index11++;
                }
                list.add(mapDocInterpretationList);
            } else if (phaseId.equals("3")) {
                List<BiddingProductCollectionDo> mapProCollectionList = biddingProjectMapper.selectProCollectionList(projectId);
                sheetNameArr[i] = "竞品收集";
                tableTitleArr[i] = "竞品收集表";
                headTablesColumnsNameArr[i] = "文件或内容,意见填写";
                tablesColumnNameArr[i] = "fileText,suggestion";
                String destPath = biddingProject.getBasePath() + biddingProject.getId() + "/竞品收集/";
                //获取额外设置的内容
                list1 = new ArrayList<>();
                List<Map> addContent = biddingProjectMapper.selectAddContent(projectId, biddingProject.getProductCollectionId());
                int index1 = 1;
                for (Map map1 : addContent) {
                    if (map1.get("typeId").toString().equals("4")) {
                        BiddingFileAddcontent contentExtraValue = biddingFileAddcontentMapper.selectByPrimaryKey(map1.get("contentExtraValue").toString());
                        list1.add(contentExtraValue.getFilePath());
                        File file = new File(contentExtraValue.getFilePath());
                        headTablesColumnsNameArr[i] += "," + map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i] += ",value" + index1;
                        if (index1 == 1) {

                            mapProCollectionList.get(0).setValue1(file.getName());
                        } else if (index1 == 2) {
                            mapProCollectionList.get(0).setValue2(file.getName());
                        } else if (index1 == 3) {
                            mapProCollectionList.get(0).setValue3(file.getName());
                        } else if (index1 == 4) {
                            mapProCollectionList.get(0).setValue4(file.getName());
                        } else if (index1 == 5) {
                            mapProCollectionList.get(0).setValue5(file.getName());
                        } else if (index1 == 6) {
                            mapProCollectionList.get(0).setValue6(file.getName());
                        } else if (index1 == 7) {
                            mapProCollectionList.get(0).setValue7(file.getName());
                        } else if (index1 == 8) {
                            mapProCollectionList.get(0).setValue8(file.getName());
                        } else if (index1 == 9) {
                            mapProCollectionList.get(0).setValue9(file.getName());
                        } else if (index1 == 10) {
                            mapProCollectionList.get(0).setValue10(file.getName());
                        }
                        index1++;
                    } else {
                        headTablesColumnsNameArr[i] += "," + map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i] += ",value" + index1;
                        if (index1 == 1) {

                            mapProCollectionList.get(0).setValue1(map1.get("contentExtraValue").toString());
                        } else if (index1 == 2) {
                            mapProCollectionList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        } else if (index1 == 3) {
                            mapProCollectionList.get(0).setValue3(map1.get("contentExtraValue").toString());
                        } else if (index1 == 4) {
                            mapProCollectionList.get(0).setValue4(map1.get("contentExtraValue").toString());
                        } else if (index1 == 5) {
                            mapProCollectionList.get(0).setValue5(map1.get("contentExtraValue").toString());
                        } else if (index1 == 6) {
                            mapProCollectionList.get(0).setValue6(map1.get("contentExtraValue").toString());
                        } else if (index1 == 7) {
                            mapProCollectionList.get(0).setValue7(map1.get("contentExtraValue").toString());
                        } else if (index1 == 8) {
                            mapProCollectionList.get(0).setValue8(map1.get("contentExtraValue").toString());
                        } else if (index1 == 9) {
                            mapProCollectionList.get(0).setValue9(map1.get("contentExtraValue").toString());
                        } else if (index1 == 10) {
                            mapProCollectionList.get(0).setValue10(map1.get("contentExtraValue").toString());
                        }
                        index1++;
                    }
                }
                if (list1 != null && list1.size() > 0) {
                    for (String srcPath : list1) {
                        File file = new File(srcPath);
                        try {
                            FileUtil.copyFile(srcPath, destPath + file.getName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //获取内容设置表新增内容
                List<Map> mapSetting = biddingProjectMapper.selectSetting(projectId, phaseId);
                int index11 = 11;
                for (Map map : mapSetting) {
                    headTablesColumnsNameArr[i] += "," + map.get("contentName").toString();
                    tablesColumnNameArr[i] += ",value" + index11;
                    if (index11 == 11) {
                        mapProCollectionList.get(0).setValue11(map.get("contentValue").toString());
                    } else if (index11 == 12) {
                        mapProCollectionList.get(0).setValue12(map.get("contentValue").toString());
                    } else if (index11 == 13) {
                        mapProCollectionList.get(0).setValue13(map.get("contentValue").toString());
                    } else if (index11 == 14) {
                        mapProCollectionList.get(0).setValue14(map.get("contentValue").toString());
                    } else if (index11 == 15) {
                        mapProCollectionList.get(0).setValue15(map.get("contentValue").toString());
                    } else if (index11 == 16) {
                        mapProCollectionList.get(0).setValue16(map.get("contentValue").toString());
                    } else if (index11 == 17) {
                        mapProCollectionList.get(0).setValue17(map.get("contentValue").toString());
                    } else if (index11 == 18) {
                        mapProCollectionList.get(0).setValue18(map.get("contentValue").toString());
                    } else if (index11 == 19) {
                        mapProCollectionList.get(0).setValue19(map.get("contentValue").toString());
                    } else if (index11 == 20) {
                        mapProCollectionList.get(0).setValue20(map.get("contentValue").toString());
                    }
                    index11++;
                }
                list.add(mapProCollectionList);
            } else if (phaseId.equals("4")) {
                List<BiddingStrategyAnalysisDo> mapStrategyAnalysisList = biddingProjectMapper.selectStrategyAnalysisList(projectId);
                sheetNameArr[i] = "策略分析";
                tableTitleArr[i] = "策略分析表";
                headTablesColumnsNameArr[i] = "产品信息,产品质量层次,产品分组,产品,其他产品限价,竞品信息,竞品质量层次,竞品分组,报价预估,其他报价预估,全国联动价格,意见填写";
                tablesColumnNameArr[i] = "fileProductInfo,fileQualityLevel,fileProductGro,fileProduct,filePriceLimit,fileCompeInfo,fileQualityLevels,fileCompeGro,fileQuotationEstimate,fileQuotationOther,fileNationalPrice,suggestion";
                String destPath = biddingProject.getBasePath() + biddingProject.getId() + "/策略分析/";
                //获取额外设置的内容
                list1 = new ArrayList<>();
                List<Map> addContent = biddingProjectMapper.selectAddContent(projectId, biddingProject.getStrategyAnalysisId());
                int index1 = 1;
                for (Map map1 : addContent) {
                    if (map1.get("typeId").toString().equals("4")) {
                        BiddingFileAddcontent contentExtraValue = biddingFileAddcontentMapper.selectByPrimaryKey(map1.get("contentExtraValue").toString());
                        list1.add(contentExtraValue.getFilePath());
                        File file = new File(contentExtraValue.getFilePath());
                        headTablesColumnsNameArr[i] += "," + map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i] += ",value" + index1;
                        if (index1 == 1) {

                            mapStrategyAnalysisList.get(0).setValue1(file.getName());
                        } else if (index1 == 2) {
                            mapStrategyAnalysisList.get(0).setValue2(file.getName());
                        } else if (index1 == 3) {
                            mapStrategyAnalysisList.get(0).setValue3(file.getName());
                        } else if (index1 == 4) {
                            mapStrategyAnalysisList.get(0).setValue4(file.getName());
                        } else if (index1 == 5) {
                            mapStrategyAnalysisList.get(0).setValue5(file.getName());
                        } else if (index1 == 6) {
                            mapStrategyAnalysisList.get(0).setValue6(file.getName());
                        } else if (index1 == 7) {
                            mapStrategyAnalysisList.get(0).setValue7(file.getName());
                        } else if (index1 == 8) {
                            mapStrategyAnalysisList.get(0).setValue8(file.getName());
                        } else if (index1 == 9) {
                            mapStrategyAnalysisList.get(0).setValue9(file.getName());
                        } else if (index1 == 10) {
                            mapStrategyAnalysisList.get(0).setValue10(file.getName());
                        }
                        index1++;
                    } else {
                        headTablesColumnsNameArr[i] += "," + map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i] += ",value" + index1;
                        if (index1 == 1) {

                            mapStrategyAnalysisList.get(0).setValue1(map1.get("contentExtraValue").toString());
                        } else if (index1 == 2) {
                            mapStrategyAnalysisList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        } else if (index1 == 3) {
                            mapStrategyAnalysisList.get(0).setValue3(map1.get("contentExtraValue").toString());
                        } else if (index1 == 4) {
                            mapStrategyAnalysisList.get(0).setValue4(map1.get("contentExtraValue").toString());
                        } else if (index1 == 5) {
                            mapStrategyAnalysisList.get(0).setValue5(map1.get("contentExtraValue").toString());
                        } else if (index1 == 6) {
                            mapStrategyAnalysisList.get(0).setValue6(map1.get("contentExtraValue").toString());
                        } else if (index1 == 7) {
                            mapStrategyAnalysisList.get(0).setValue7(map1.get("contentExtraValue").toString());
                        } else if (index1 == 8) {
                            mapStrategyAnalysisList.get(0).setValue8(map1.get("contentExtraValue").toString());
                        } else if (index1 == 9) {
                            mapStrategyAnalysisList.get(0).setValue9(map1.get("contentExtraValue").toString());
                        } else if (index1 == 10) {
                            mapStrategyAnalysisList.get(0).setValue10(map1.get("contentExtraValue").toString());
                        }
                        index1++;
                    }
                }
                if (list1 != null && list1.size() > 0) {
                    for (String srcPath : list1) {
                        File file = new File(srcPath);
                        try {
                            FileUtil.copyFile(srcPath, destPath + file.getName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //获取内容设置表新增内容
                List<Map> mapSetting = biddingProjectMapper.selectSetting(projectId, phaseId);
                int index11 = 11;
                for (Map map : mapSetting) {
                    headTablesColumnsNameArr[i] += "," + map.get("contentName").toString();
                    tablesColumnNameArr[i] += ",value" + index11;
                    if (index11 == 11) {
                        mapStrategyAnalysisList.get(0).setValue11(map.get("contentValue").toString());
                    } else if (index11 == 12) {
                        mapStrategyAnalysisList.get(0).setValue12(map.get("contentValue").toString());
                    } else if (index11 == 13) {
                        mapStrategyAnalysisList.get(0).setValue13(map.get("contentValue").toString());
                    } else if (index11 == 14) {
                        mapStrategyAnalysisList.get(0).setValue14(map.get("contentValue").toString());
                    } else if (index11 == 15) {
                        mapStrategyAnalysisList.get(0).setValue15(map.get("contentValue").toString());
                    } else if (index11 == 16) {
                        mapStrategyAnalysisList.get(0).setValue16(map.get("contentValue").toString());
                    } else if (index11 == 17) {
                        mapStrategyAnalysisList.get(0).setValue17(map.get("contentValue").toString());
                    } else if (index11 == 18) {
                        mapStrategyAnalysisList.get(0).setValue18(map.get("contentValue").toString());
                    } else if (index11 == 19) {
                        mapStrategyAnalysisList.get(0).setValue19(map.get("contentValue").toString());
                    } else if (index11 == 20) {
                        mapStrategyAnalysisList.get(0).setValue20(map.get("contentValue").toString());
                    }
                    index11++;
                }
                list.add(mapStrategyAnalysisList);
            } else if (phaseId.equals("5")) {
                List<BiddingInfoFillingDo> mapInfoFillingList = biddingProjectMapper.selectInfoFillingList(projectId);
                sheetNameArr[i] = "信息填报";
                tableTitleArr[i] = "信息填报表";
                headTablesColumnsNameArr[i] = "线上填报,盖章申请,资料清单,OA文件名,特殊文件明细,递交时间记录,价格申请,价格申诉函,信息变更申诉函,其他文件,意见填写";
                tablesColumnNameArr[i] = "fileOnlineFilling,fileApplicationSeal,fileInfoList,fileNameOa,fileDetailsSpecial,submissionTime,filePriceApp,filePriceLetter,fileInfoChange,fileOther,suggestion";
                String destPath = biddingProject.getBasePath() + biddingProject.getId() + "/信息填报/";
                //获取额外设置的内容
                list1 = new ArrayList<>();
                List<Map> addContent = biddingProjectMapper.selectAddContent(projectId, biddingProject.getInfoFillingId());
                int index1 = 1;
                for (Map map1 : addContent) {

                    if (map1.get("typeId").toString().equals("4")) {
                        BiddingFileAddcontent contentExtraValue = biddingFileAddcontentMapper.selectByPrimaryKey(map1.get("contentExtraValue").toString());
                        list1.add(contentExtraValue.getFilePath());
                        File file = new File(contentExtraValue.getFilePath());
                        headTablesColumnsNameArr[i] += "," + map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i] += ",value" + index1;
                        if (index1 == 1) {

                            mapInfoFillingList.get(0).setValue1(file.getName());
                        } else if (index1 == 2) {
                            mapInfoFillingList.get(0).setValue2(file.getName());
                        } else if (index1 == 3) {
                            mapInfoFillingList.get(0).setValue3(file.getName());
                        } else if (index1 == 4) {
                            mapInfoFillingList.get(0).setValue4(file.getName());
                        } else if (index1 == 5) {
                            mapInfoFillingList.get(0).setValue5(file.getName());
                        } else if (index1 == 6) {
                            mapInfoFillingList.get(0).setValue6(file.getName());
                        } else if (index1 == 7) {
                            mapInfoFillingList.get(0).setValue7(file.getName());
                        } else if (index1 == 8) {
                            mapInfoFillingList.get(0).setValue8(file.getName());
                        } else if (index1 == 9) {
                            mapInfoFillingList.get(0).setValue9(file.getName());
                        } else if (index1 == 10) {
                            mapInfoFillingList.get(0).setValue10(file.getName());
                        }
                        index1++;
                    } else {
                        headTablesColumnsNameArr[i] += "," + map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i] += ",value" + index1;
                        if (index1 == 1) {

                            mapInfoFillingList.get(0).setValue1(map1.get("contentExtraValue").toString());
                        } else if (index1 == 2) {
                            mapInfoFillingList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        } else if (index1 == 3) {
                            mapInfoFillingList.get(0).setValue3(map1.get("contentExtraValue").toString());
                        } else if (index1 == 4) {
                            mapInfoFillingList.get(0).setValue4(map1.get("contentExtraValue").toString());
                        } else if (index1 == 5) {
                            mapInfoFillingList.get(0).setValue5(map1.get("contentExtraValue").toString());
                        } else if (index1 == 6) {
                            mapInfoFillingList.get(0).setValue6(map1.get("contentExtraValue").toString());
                        } else if (index1 == 7) {
                            mapInfoFillingList.get(0).setValue7(map1.get("contentExtraValue").toString());
                        } else if (index1 == 8) {
                            mapInfoFillingList.get(0).setValue8(map1.get("contentExtraValue").toString());
                        } else if (index1 == 9) {
                            mapInfoFillingList.get(0).setValue9(map1.get("contentExtraValue").toString());
                        } else if (index1 == 10) {
                            mapInfoFillingList.get(0).setValue10(map1.get("contentExtraValue").toString());
                        }
                        index1++;
                    }
                }
                if (list1 != null && list1.size() > 0) {
                    for (String srcPath : list1) {
                        File file = new File(srcPath);
                        try {
                            FileUtil.copyFile(srcPath, destPath + file.getName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //获取内容设置表新增内容
                List<Map> mapSetting = biddingProjectMapper.selectSetting(projectId, phaseId);
                int index11 = 11;
                for (Map map : mapSetting) {

                    headTablesColumnsNameArr[i] += "," + map.get("contentName").toString();
                    tablesColumnNameArr[i] += ",value" + index11;
                    if (index11 == 11) {
                        mapInfoFillingList.get(0).setValue11(map.get("contentValue").toString());
                    } else if (index11 == 12) {
                        mapInfoFillingList.get(0).setValue12(map.get("contentValue").toString());
                    } else if (index11 == 13) {
                        mapInfoFillingList.get(0).setValue13(map.get("contentValue").toString());
                    } else if (index11 == 14) {
                        mapInfoFillingList.get(0).setValue14(map.get("contentValue").toString());
                    } else if (index11 == 15) {
                        mapInfoFillingList.get(0).setValue15(map.get("contentValue").toString());
                    } else if (index11 == 16) {
                        mapInfoFillingList.get(0).setValue16(map.get("contentValue").toString());
                    } else if (index11 == 17) {
                        mapInfoFillingList.get(0).setValue17(map.get("contentValue").toString());
                    } else if (index11 == 18) {
                        mapInfoFillingList.get(0).setValue18(map.get("contentValue").toString());
                    } else if (index11 == 19) {
                        mapInfoFillingList.get(0).setValue19(map.get("contentValue").toString());
                    } else if (index11 == 20) {
                        mapInfoFillingList.get(0).setValue20(map.get("contentValue").toString());
                    }
                    index11++;
                }
                list.add(mapInfoFillingList);
            } else if (phaseId.equals("6")) {
                List<BiddingOfficialNoticeDo> OfficialNoticeList = biddingProjectMapper.selectOfficialNoticeList(projectId);
                sheetNameArr[i] = "官方公告";
                tableTitleArr[i] = "官方公告表";
                headTablesColumnsNameArr[i] = "公告文件,配送商要求,意见填写";
                tablesColumnNameArr[i] = "fileAnnounce,distributorRequire,suggestion";
                String destPath = biddingProject.getBasePath() + biddingProject.getId() + "/官方公告/";
                //获取额外设置的内容
                list1 = new ArrayList<>();
                List<Map> addContent = biddingProjectMapper.selectAddContent(projectId, biddingProject.getOfficialNoticeId());
                int index1 = 1;
                for (Map map1 : addContent) {

                    if (map1.get("typeId").toString().equals("4")) {
                        BiddingFileAddcontent contentExtraValue = biddingFileAddcontentMapper.selectByPrimaryKey(map1.get("contentExtraValue").toString());
                        list1.add(contentExtraValue.getFilePath());
                        File file = new File(contentExtraValue.getFilePath());
                        headTablesColumnsNameArr[i] += "," + map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i] += ",value" + index1;
                        if (index1 == 1) {

                            OfficialNoticeList.get(0).setValue1(file.getName());
                        } else if (index1 == 2) {
                            OfficialNoticeList.get(0).setValue2(file.getName());
                        } else if (index1 == 3) {
                            OfficialNoticeList.get(0).setValue3(file.getName());
                        } else if (index1 == 4) {
                            OfficialNoticeList.get(0).setValue4(file.getName());
                        } else if (index1 == 5) {
                            OfficialNoticeList.get(0).setValue5(file.getName());
                        } else if (index1 == 6) {
                            OfficialNoticeList.get(0).setValue6(file.getName());
                        } else if (index1 == 7) {
                            OfficialNoticeList.get(0).setValue7(file.getName());
                        } else if (index1 == 8) {
                            OfficialNoticeList.get(0).setValue8(file.getName());
                        } else if (index1 == 9) {
                            OfficialNoticeList.get(0).setValue9(file.getName());
                        } else if (index1 == 10) {
                            OfficialNoticeList.get(0).setValue10(file.getName());
                        }
                        index1++;
                    } else {
                        headTablesColumnsNameArr[i] += "," + map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i] += ",value" + index1;
                        if (index1 == 1) {

                            OfficialNoticeList.get(0).setValue1(map1.get("contentExtraValue").toString());
                        } else if (index1 == 2) {
                            OfficialNoticeList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        } else if (index1 == 3) {
                            OfficialNoticeList.get(0).setValue3(map1.get("contentExtraValue").toString());
                        } else if (index1 == 4) {
                            OfficialNoticeList.get(0).setValue4(map1.get("contentExtraValue").toString());
                        } else if (index1 == 5) {
                            OfficialNoticeList.get(0).setValue5(map1.get("contentExtraValue").toString());
                        } else if (index1 == 6) {
                            OfficialNoticeList.get(0).setValue6(map1.get("contentExtraValue").toString());
                        } else if (index1 == 7) {
                            OfficialNoticeList.get(0).setValue7(map1.get("contentExtraValue").toString());
                        } else if (index1 == 8) {
                            OfficialNoticeList.get(0).setValue8(map1.get("contentExtraValue").toString());
                        } else if (index1 == 9) {
                            OfficialNoticeList.get(0).setValue9(map1.get("contentExtraValue").toString());
                        } else if (index1 == 10) {
                            OfficialNoticeList.get(0).setValue10(map1.get("contentExtraValue").toString());
                        }
                        index1++;
                    }
                }
                if (list1 != null && list1.size() > 0) {
                    for (String srcPath : list1) {
                        File file = new File(srcPath);
                        try {
                            FileUtil.copyFile(srcPath, destPath + file.getName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //获取内容设置表新增内容
                List<Map> mapSetting = biddingProjectMapper.selectSetting(projectId, phaseId);
                int index11 = 11;
                for (Map map : mapSetting) {

                    headTablesColumnsNameArr[i] += "," + map.get("contentName").toString();
                    tablesColumnNameArr[i] += ",value" + index11;
                    if (index11 == 11) {
                        OfficialNoticeList.get(0).setValue11(map.get("contentValue").toString());
                    } else if (index11 == 12) {
                        OfficialNoticeList.get(0).setValue12(map.get("contentValue").toString());
                    } else if (index11 == 13) {
                        OfficialNoticeList.get(0).setValue13(map.get("contentValue").toString());
                    } else if (index11 == 14) {
                        OfficialNoticeList.get(0).setValue14(map.get("contentValue").toString());
                    } else if (index11 == 15) {
                        OfficialNoticeList.get(0).setValue15(map.get("contentValue").toString());
                    } else if (index11 == 16) {
                        OfficialNoticeList.get(0).setValue16(map.get("contentValue").toString());
                    } else if (index11 == 17) {
                        OfficialNoticeList.get(0).setValue17(map.get("contentValue").toString());
                    } else if (index11 == 18) {
                        OfficialNoticeList.get(0).setValue18(map.get("contentValue").toString());
                    } else if (index11 == 19) {
                        OfficialNoticeList.get(0).setValue19(map.get("contentValue").toString());
                    } else if (index11 == 20) {
                        OfficialNoticeList.get(0).setValue20(map.get("contentValue").toString());
                    }
                    index11++;
                }
                list.add(OfficialNoticeList);
            } else if (phaseId.equals("7")) {
                List<BiddingProjectSummaryDo> ProjectSummaryList = biddingProjectMapper.selectProjectSummaryList(projectId);
                if (ProjectSummaryList.get(0).getProductId() != null && !ProjectSummaryList.get(0).getProductId().equals("")) {
                    String productIds = ProjectSummaryList.get(0).getProductId();
                    String[] split1 = productIds.split(",");
                    String productNames = "";
                    for (int j = 0; j < split1.length; j++) {
                        BiddingProduct biddingProduct = biddingProductMapper.selectByPrimaryKey(split1[j]);
                        productNames += biddingProduct.getEnName() + ",";
                    }
                    productNames = productNames.substring(0, productNames.length() - 1);
                    ProjectSummaryList.get(0).setProductId(productNames);
                }
                if (ProjectSummaryList.get(0).getStatus() != null && !ProjectSummaryList.get(0).getStatus().equals("")) {
                    if (ProjectSummaryList.get(0).getStatus().equals("0")) {
                        ProjectSummaryList.get(0).setStatus("成功");
                    } else {
                        ProjectSummaryList.get(0).setStatus("失败");
                    }
                }
                if (ProjectSummaryList.get(0).getIsClinical() != null && !ProjectSummaryList.get(0).getIsClinical().equals("")) {
                    if (ProjectSummaryList.get(0).getIsClinical().equals("0")) {
                        ProjectSummaryList.get(0).setIsClinical("是");
                    } else {
                        ProjectSummaryList.get(0).setIsClinical("否");
                    }
                }
                if (ProjectSummaryList.get(0).getIsRecord() != null && !ProjectSummaryList.get(0).getIsRecord().equals("")) {
                    if (ProjectSummaryList.get(0).getIsRecord().equals("0")) {
                        ProjectSummaryList.get(0).setIsRecord("是");
                    } else {
                        ProjectSummaryList.get(0).setIsRecord("否");
                    }
                }
                sheetNameArr[i] = "项目总结";
                tableTitleArr[i] = "项目总结表";
                headTablesColumnsNameArr[i] = "产品ID,参与竞标产品状态,较上一轮降幅,竞品情况,备案,临床,失败原因,下一步工作,预估下一轮启动时间,启动时间,意见填写";
                tablesColumnNameArr[i] = "productId,status,lastRoundDecline,competitiveProduc,isRecord,isClinical,failureReasons,nextStep,estimatedTime,startTime,suggestion";
                String destPath = biddingProject.getBasePath() + biddingProject.getId() + "/项目总结/";
                //获取额外设置的内容
                list1 = new ArrayList<>();
                List<Map> addContent = biddingProjectMapper.selectAddContent(projectId, biddingProject.getProjectSummaryId());
                int index1 = 1;
                for (Map map1 : addContent) {

                    if (map1.get("typeId").toString().equals("4")) {
                        BiddingFileAddcontent contentExtraValue = biddingFileAddcontentMapper.selectByPrimaryKey(map1.get("contentExtraValue").toString());
                        list1.add(contentExtraValue.getFilePath());
                        File file = new File(contentExtraValue.getFilePath());
                        headTablesColumnsNameArr[i] += "," + map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i] += ",value" + index1;
                        if (index1 == 1) {
                            ProjectSummaryList.get(0).setValue1(file.getName());
                        } else if (index1 == 2) {
                            ProjectSummaryList.get(0).setValue2(file.getName());
                        } else if (index1 == 3) {
                            ProjectSummaryList.get(0).setValue3(file.getName());
                        } else if (index1 == 4) {
                            ProjectSummaryList.get(0).setValue4(file.getName());
                        } else if (index1 == 5) {
                            ProjectSummaryList.get(0).setValue5(file.getName());
                        } else if (index1 == 6) {
                            ProjectSummaryList.get(0).setValue6(file.getName());
                        } else if (index1 == 7) {
                            ProjectSummaryList.get(0).setValue7(file.getName());
                        } else if (index1 == 8) {
                            ProjectSummaryList.get(0).setValue8(file.getName());
                        } else if (index1 == 9) {
                            ProjectSummaryList.get(0).setValue9(file.getName());
                        } else if (index1 == 10) {
                            ProjectSummaryList.get(0).setValue10(file.getName());
                        }
                        index1++;
                    } else {
                        headTablesColumnsNameArr[i] += "," + map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i] += ",value" + index1;
                        if (index1 == 1) {

                            ProjectSummaryList.get(0).setValue1(map1.get("contentExtraValue").toString());
                        } else if (index1 == 2) {
                            ProjectSummaryList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        } else if (index1 == 3) {
                            ProjectSummaryList.get(0).setValue3(map1.get("contentExtraValue").toString());
                        } else if (index1 == 4) {
                            ProjectSummaryList.get(0).setValue4(map1.get("contentExtraValue").toString());
                        } else if (index1 == 5) {
                            ProjectSummaryList.get(0).setValue5(map1.get("contentExtraValue").toString());
                        } else if (index1 == 6) {
                            ProjectSummaryList.get(0).setValue6(map1.get("contentExtraValue").toString());
                        } else if (index1 == 7) {
                            ProjectSummaryList.get(0).setValue7(map1.get("contentExtraValue").toString());
                        } else if (index1 == 8) {
                            ProjectSummaryList.get(0).setValue8(map1.get("contentExtraValue").toString());
                        } else if (index1 == 9) {
                            ProjectSummaryList.get(0).setValue9(map1.get("contentExtraValue").toString());
                        } else if (index1 == 10) {
                            ProjectSummaryList.get(0).setValue10(map1.get("contentExtraValue").toString());
                        }
                        index1++;
                    }
                }
                if (list1 != null && list1.size() > 0) {
                    for (String srcPath : list1) {
                        File file = new File(srcPath);
                        try {
                            FileUtil.copyFile(srcPath, destPath + file.getName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //获取内容设置表新增内容
                List<Map> mapSetting = biddingProjectMapper.selectSetting(projectId, phaseId);
                int index11 = 11;
                for (Map map : mapSetting) {

                    headTablesColumnsNameArr[i] += "," + map.get("contentName").toString();
                    tablesColumnNameArr[i] += ",value" + index11;
                    if (index11 == 11) {
                        ProjectSummaryList.get(0).setValue11(map.get("contentValue").toString());
                    } else if (index11 == 12) {
                        ProjectSummaryList.get(0).setValue12(map.get("contentValue").toString());
                    } else if (index11 == 13) {
                        ProjectSummaryList.get(0).setValue13(map.get("contentValue").toString());
                    } else if (index11 == 14) {
                        ProjectSummaryList.get(0).setValue14(map.get("contentValue").toString());
                    } else if (index11 == 15) {
                        ProjectSummaryList.get(0).setValue15(map.get("contentValue").toString());
                    } else if (index11 == 16) {
                        ProjectSummaryList.get(0).setValue16(map.get("contentValue").toString());
                    } else if (index11 == 17) {
                        ProjectSummaryList.get(0).setValue17(map.get("contentValue").toString());
                    } else if (index11 == 18) {
                        ProjectSummaryList.get(0).setValue18(map.get("contentValue").toString());
                    } else if (index11 == 19) {
                        ProjectSummaryList.get(0).setValue19(map.get("contentValue").toString());
                    } else if (index11 == 20) {
                        ProjectSummaryList.get(0).setValue20(map.get("contentValue").toString());
                    }
                    index11++;
                }
                list.add(ProjectSummaryList);
            }
        }
        POIExcelUtils poiExcel = new POIExcelUtils(sheetNameArr, tableTitleArr, headTablesColumnsNameArr, list, tablesColumnNameArr);
        poiExcel.excelDataExport(exportFilePath3);

        String path = biddingProject.getBasePath() + "/" + biddingProject.getId();
        //压缩后的文件名字
        String zipName = biddingProjectBulid.getName();
        String format = "zip";
        String zipPath = "";
        try {
            zipPath = CompressUtil.generateFile(path, format, zipName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //开始下载zip文件
        File file = new File(zipPath);
        String fileName = file.getName();
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            try {
                response.addHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(fileName, "GB2312"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                log.error(e.toString(), e);
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                        return null;
                    } catch (IOException e) {
                        log.error(e.toString(), e);
                        return new Result<>(false, StatusCode.ERROR, "呀！服务器开小差了！");
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                        return null;
                    } catch (IOException e) {
                        log.error(e.toString(), e);
                        return new Result<>(false, StatusCode.ERROR, "呀！服务器开小差了！");
                    }
                }
            }
        }
        return null;
    }

    /**
     * 上传解析中标文件
     *
     * @param response
     * @param request
     * @param file
     * @return
     */
    public Result uploadFile(HttpServletResponse response, HttpServletRequest request, MultipartFile file) {
        //    获取文件的名称
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);//      获取文件的后缀名
        String pattern = fileName.substring(fileName.lastIndexOf(".") + 1);
        System.out.println(pattern);
        List<List<String>> listContent = new ArrayList<>();
        try {
            if (file != null) {
                //文件类型判断
                if (!ExcelUtil.isEXCEL(file)) {
                } else {
                    listContent = ExcelUtil.readExcelContents(file, pattern);
                    //文件内容判断
                    if (listContent.isEmpty()) {
                    } else {
                        //    循环遍历
                        for (int i = 0; i < listContent.size(); i++) {
                            BiddingPriceInfo biddingPriceInfo = new BiddingPriceInfo();
                            //      读取excel表格中的数据
                            String area = listContent.get(i).get(0);
                            String userName = listContent.get(i).get(1);
                            String userId = listContent.get(i).get(2);
                            String region = listContent.get(i).get(3);
                            BiddingProvince biddingProvince = biddingProvinceMapper.selectByProName(region);
                            String proId = biddingProvince.getId() + "";
                            String productEn = listContent.get(i).get(4);
                            String bidPrice = listContent.get(i).get(5);
                            if (bidPrice.equals("——")) {
                                bidPrice = null;
                            }
                            String isBid = listContent.get(i).get(6);
                            if (isBid.equals("√")) {
                                isBid = "0";
                            } else if (isBid.equals("——")) {
                                isBid = "1";
                            } else if (isBid.equals("*")) {
                                isBid = "2";
                            }
                            String bidTime = listContent.get(i).get(7);
                            if (bidTime.equals("——")) {
                                bidTime = null;
                            }
                            String remarks = listContent.get(i).get(8);
                            String strikePrice = listContent.get(i).get(9);
                            if (strikePrice.equals("——")) {
                                strikePrice = null;
                            }
                            //赋值
                            biddingPriceInfo.setArea(area);
                            biddingPriceInfo.setUserName(userName);
                            biddingPriceInfo.setUserId(userId);
                            biddingPriceInfo.setRegion(region);
                            biddingPriceInfo.setProId(proId);
                            biddingPriceInfo.setProductEn(productEn);
                            biddingPriceInfo.setBidPrice(bidPrice);
                            biddingPriceInfo.setIsBid(isBid);
                            biddingPriceInfo.setBidTime(bidTime);
                            biddingPriceInfo.setRemarks(remarks);
                            biddingPriceInfo.setStrikePrice(strikePrice);
                            biddingPriceInfo.setDelflag("0");
                            //      插入数据
                            biddingPriceInfoMapper.insertSelective(biddingPriceInfo);
                        }
                    }
                }
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导出中标文件
     *
     * @param response
     * @return
     */
    public Result downloadFile(HttpServletResponse response) {
        //单个sheet页导出

        //sheet页的名字
        String sheetName = "中标信息";
        //表格标题名
        String tableTitle = "中标信息列表";
        //表头列名数组
        String[] headTableColumnsNameArr = {"区域", "负责人", "员工号", "地区", "产品", "中标/挂网价格", "中标/挂网情况", "中标/挂网价执行时间", "备注", "执行价"};
        List<BiddingPriceInfo> queryList = biddingPriceInfoMapper.selectAll();
        List<BiddingPriceInfo> biddingPriceInfos =new ArrayList<>();
        for (BiddingPriceInfo biddingPriceInfo : queryList) {
            if (biddingPriceInfo.getBidPrice()==null||biddingPriceInfo.getBidPrice().equals("")){
                biddingPriceInfo.setBidPrice("——");
            }
            if (biddingPriceInfo.getIsBid()!=null&&!biddingPriceInfo.getIsBid().equals("")){
                if (biddingPriceInfo.getIsBid().equals("1")){
                    biddingPriceInfo.setIsBid("——");
                }
                if (biddingPriceInfo.getIsBid().equals("2")){
                    biddingPriceInfo.setIsBid("*");
                }
            }
            if (biddingPriceInfo.getBidTime()==null||biddingPriceInfo.getBidTime().equals("")){
                biddingPriceInfo.setBidTime("——");
            }
            System.out.println(biddingPriceInfo.getStrikePrice());
            if (biddingPriceInfo.getStrikePrice()==null||biddingPriceInfo.getStrikePrice().equals("")){
                biddingPriceInfo.setStrikePrice("——");
            }
            biddingPriceInfos.add(biddingPriceInfo);
        }
        String[] columnNameArr = {"area", "userName", "userId", "region", "productEn", "bidPrice", "isBid", "bidTime", "remarks", "strikePrice"};
        POIExcelUtils poiExcel = new POIExcelUtils(sheetName, tableTitle, headTableColumnsNameArr, queryList, columnNameArr);
        //设置导出路径
        String exportFilePath = "E:\\project\\bidfiles.xls";
        poiExcel.excelDataExport(exportFilePath);
        return null;
    }
}