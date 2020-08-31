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

import javax.annotation.Resource;
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
            List<BiddingPriceInfo> priceInfos = biddingPriceInfoMapper.selectByproductEn(productEnName);
            return new Result<>(true, StatusCode.OK, "查询成功", priceInfos);
        } catch (Exception e) {
            log.error(e.toString(), e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }

    public Result exportProject(String projectId,HttpServletResponse response) {
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
        List<String> list1=new ArrayList<>();
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
                //获取额外设置的内容
                List<Map> addContent = biddingProjectMapper.selectAddContent(projectId, biddingProject.getProjectBulidId());
                for (Map map1 : addContent) {
                    int index=1;
                    if (map1.get("typeId").toString().equals("4")) {
                        BiddingFileAddcontent contentExtraValue = biddingFileAddcontentMapper.selectByPrimaryKey(map1.get("contentExtraValue").toString());
                        list1.add(contentExtraValue.getFilePath());
                    }else {
                        headTablesColumnsNameArr[i]+=","+map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i]+=",value"+index;
                        if (index==1){

                            mapBuildList.get(0).setValue1(map1.get("contentExtraValue").toString());
                        }else if(index==2){
                            mapBuildList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        }
                        else if(index==3){
                            mapBuildList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        }
                        index++;
                    }
                }
                //获取内容设置表新增内容
                List<Map> mapSetting = biddingProjectMapper.selectSetting(projectId, phaseId);
                for (Map map : mapSetting) {
                    int index=11;
                    if (map.get("contentTypeId").toString().equals("4")) {
                        list1.add(map.get("contentValue").toString());
                    }else {
                        headTablesColumnsNameArr[i]+=","+map.get("contentName").toString();
                        tablesColumnNameArr[i]+=",value"+index;
                        if (index==11){
                            mapBuildList.get(0).setValue11(map.get("contentValue").toString());
                        }else if(index==12){
                            mapBuildList.get(0).setValue12(map.get("contentValue").toString());
                        }else if(index==3){
                            mapBuildList.get(0).setValue13(map.get("contentValue").toString());
                        }
                        index++;
                    }
                }
                list.add(mapBuildList);

            } else if (phaseId.equals("2")) {
                List<BiddingDocInterpretationDo> mapDocInterpretationList = biddingProjectMapper.selectInterpretationList(projectId);
                sheetNameArr[i] = "文件解读";
                tableTitleArr[i] = "文件解读表";
                headTablesColumnsNameArr[i] = "参加招标的品种,实施范围,不参加本次招标的原因,执行时间范围起始,执行时间范围终止,通用名,规格,剂型,质量层次,限价制定,限价的参考值,限价的说明,采购规则类型,采购规则,报价,行业影响,公司自身产品影响,征求稿意见,征求稿意见文件,意见填写";
                tablesColumnNameArr[i] = "type,range,noReason,timeRangeStart,timeRangeEnd,commonName,standards,dosageForm,qualityLevel,priceLimit,priceLimitReference,priceLimitExplain,fileBuyRulestag,fileBuyRules,fileQuotedPrice,industryInfluence,selfInfluence,solicitingOpinions,fileSolicitingOpinions,suggestion";
                //获取额外设置的内容
                List<Map> addContent = biddingProjectMapper.selectAddContent(projectId, biddingProject.getDocInterpretationId());
                for (Map map1 : addContent) {
                    int index=1;
                    if (map1.get("typeId").toString().equals("4")) {
                        BiddingFileAddcontent contentExtraValue = biddingFileAddcontentMapper.selectByPrimaryKey(map1.get("contentExtraValue").toString());
                        list1.add(contentExtraValue.getFilePath());
                    }else {
                        headTablesColumnsNameArr[i]+=","+map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i]+=",value"+index;
                        if (index==1){

                            mapDocInterpretationList.get(0).setValue1(map1.get("contentExtraValue").toString());
                        }else if(index==2){
                            mapDocInterpretationList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        }
                        else if(index==3){
                            mapDocInterpretationList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        }
                        index++;
                    }
                }
                //获取内容设置表新增内容
                List<Map> mapSetting = biddingProjectMapper.selectSetting(projectId, phaseId);
                for (Map map : mapSetting) {
                    int index=11;
                    if (map.get("contentTypeId").toString().equals("4")) {
                        list1.add(map.get("contentValue").toString());
                    }else {
                        headTablesColumnsNameArr[i]+=","+map.get("contentName").toString();
                        tablesColumnNameArr[i]+=",value"+index;
                        if (index==11){
                            mapDocInterpretationList.get(0).setValue11(map.get("contentValue").toString());
                        }else if(index==12){
                            mapDocInterpretationList.get(0).setValue12(map.get("contentValue").toString());
                        }else if(index==3){
                            mapDocInterpretationList.get(0).setValue13(map.get("contentValue").toString());
                        }
                        index++;
                    }
                }
                list.add(mapDocInterpretationList);
            } else if (phaseId.equals("3")) {
                List<BiddingProductCollectionDo> mapProCollectionList = biddingProjectMapper.selectProCollectionList(projectId);
                sheetNameArr[i] = "竞品收集";
                tableTitleArr[i] = "竞品收集表";
                headTablesColumnsNameArr[i] = "文件或内容,意见填写";
                tablesColumnNameArr[i] = "fileText,suggestion";
                //获取额外设置的内容
                List<Map> addContent = biddingProjectMapper.selectAddContent(projectId, biddingProject.getProductCollectionId());
                for (Map map1 : addContent) {
                    int index=1;
                    if (map1.get("typeId").toString().equals("4")) {
                        BiddingFileAddcontent contentExtraValue = biddingFileAddcontentMapper.selectByPrimaryKey(map1.get("contentExtraValue").toString());
                        list1.add(contentExtraValue.getFilePath());
                    }else {
                        headTablesColumnsNameArr[i]+=","+map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i]+=",value"+index;
                        if (index==1){

                            mapProCollectionList.get(0).setValue1(map1.get("contentExtraValue").toString());
                        }else if(index==2){
                            mapProCollectionList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        }
                        else if(index==3){
                            mapProCollectionList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        }
                        index++;
                    }
                }
                //获取内容设置表新增内容
                List<Map> mapSetting = biddingProjectMapper.selectSetting(projectId, phaseId);
                for (Map map : mapSetting) {
                    int index=11;
                    if (map.get("contentTypeId").toString().equals("4")) {
                        list1.add(map.get("contentValue").toString());
                    }else {
                        headTablesColumnsNameArr[i]+=","+map.get("contentName").toString();
                        tablesColumnNameArr[i]+=",value"+index;
                        if (index==11){
                            mapProCollectionList.get(0).setValue11(map.get("contentValue").toString());
                        }else if(index==12){
                            mapProCollectionList.get(0).setValue12(map.get("contentValue").toString());
                        }else if(index==3){
                            mapProCollectionList.get(0).setValue13(map.get("contentValue").toString());
                        }
                        index++;
                    }
                }
                list.add(mapProCollectionList);
            } else if (phaseId.equals("4")) {
                List<BiddingStrategyAnalysisDo> mapStrategyAnalysisList = biddingProjectMapper.selectStrategyAnalysisList(projectId);
                sheetNameArr[i] = "策略分析";
                tableTitleArr[i] = "策略分析表";
                headTablesColumnsNameArr[i] = "产品信息,产品质量层次,产品分组,产品,其他产品限价,竞品信息,竞品质量层次,竞品分组,报价预估,其他报价预估,全国联动价格,意见填写";
                tablesColumnNameArr[i] = "fileProductInfo,fileQualityLevel,fileProductGro,fileProduct,filePriceLimit,fileCompeInfo,fileQualityLevels,fileCompeGro,fileQuotationEstimate,fileQuotationOther,fileNationalPrice,suggestion";
                //获取额外设置的内容
                List<Map> addContent = biddingProjectMapper.selectAddContent(projectId, biddingProject.getStrategyAnalysisId());
                for (Map map1 : addContent) {
                    int index=1;
                    if (map1.get("typeId").toString().equals("4")) {
                        BiddingFileAddcontent contentExtraValue = biddingFileAddcontentMapper.selectByPrimaryKey(map1.get("contentExtraValue").toString());
                        list1.add(contentExtraValue.getFilePath());
                    }else {
                        headTablesColumnsNameArr[i]+=","+map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i]+=",value"+index;
                        if (index==1){

                            mapStrategyAnalysisList.get(0).setValue1(map1.get("contentExtraValue").toString());
                        }else if(index==2){
                            mapStrategyAnalysisList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        }
                        else if(index==3){
                            mapStrategyAnalysisList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        }
                        index++;
                    }
                }
                //获取内容设置表新增内容
                List<Map> mapSetting = biddingProjectMapper.selectSetting(projectId, phaseId);
                for (Map map : mapSetting) {
                    int index=11;
                    if (map.get("contentTypeId").toString().equals("4")) {
                        list1.add(map.get("contentValue").toString());
                    }else {
                        headTablesColumnsNameArr[i]+=","+map.get("contentName").toString();
                        tablesColumnNameArr[i]+=",value"+index;
                        if (index==11){
                            mapStrategyAnalysisList.get(0).setValue11(map.get("contentValue").toString());
                        }else if(index==12){
                            mapStrategyAnalysisList.get(0).setValue12(map.get("contentValue").toString());
                        }else if(index==3){
                            mapStrategyAnalysisList.get(0).setValue13(map.get("contentValue").toString());
                        }
                        index++;
                    }
                }
                list.add(mapStrategyAnalysisList);
            } else if (phaseId.equals("5")) {
                List<BiddingInfoFillingDo> mapInfoFillingList = biddingProjectMapper.selectInfoFillingList(projectId);
                sheetNameArr[i] = "信息填报";
                tableTitleArr[i] = "信息填报表";
                headTablesColumnsNameArr[i] = "线上填报,盖章申请,资料清单,OA文件名,特殊文件明细,递交时间记录,价格申请,价格申诉函,信息变更申诉函,其他文件,意见填写";
                tablesColumnNameArr[i] = "fileOnlineFilling,fileApplicationSeal,fileInfoList,fileNameOa,fileDetailsSpecial,submissionTime,filePriceApp,filePriceLetter,fileInfoChange,fileOther,suggestion";
                //获取额外设置的内容
                List<Map> addContent = biddingProjectMapper.selectAddContent(projectId, biddingProject.getInfoFillingId());
                for (Map map1 : addContent) {
                    int index=1;
                    if (map1.get("typeId").toString().equals("4")) {
                        BiddingFileAddcontent contentExtraValue = biddingFileAddcontentMapper.selectByPrimaryKey(map1.get("contentExtraValue").toString());
                        list1.add(contentExtraValue.getFilePath());
                    }else {
                        headTablesColumnsNameArr[i]+=","+map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i]+=",value"+index;
                        if (index==1){

                            mapInfoFillingList.get(0).setValue1(map1.get("contentExtraValue").toString());
                        }else if(index==2){
                            mapInfoFillingList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        }
                        else if(index==3){
                            mapInfoFillingList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        }
                        index++;
                    }
                }
                //获取内容设置表新增内容
                List<Map> mapSetting = biddingProjectMapper.selectSetting(projectId, phaseId);
                for (Map map : mapSetting) {
                    int index=11;
                    if (map.get("contentTypeId").toString().equals("4")) {
                        list1.add(map.get("contentValue").toString());
                    }else {
                        headTablesColumnsNameArr[i]+=","+map.get("contentName").toString();
                        tablesColumnNameArr[i]+=",value"+index;
                        if (index==11){
                            mapInfoFillingList.get(0).setValue11(map.get("contentValue").toString());
                        }else if(index==12){
                            mapInfoFillingList.get(0).setValue12(map.get("contentValue").toString());
                        }else if(index==3){
                            mapInfoFillingList.get(0).setValue13(map.get("contentValue").toString());
                        }
                        index++;
                    }
                }
                list.add(mapInfoFillingList);
            } else if (phaseId.equals("6")) {
                List<BiddingOfficialNoticeDo> OfficialNoticeList = biddingProjectMapper.selectOfficialNoticeList(projectId);
                sheetNameArr[i] = "官方公告";
                tableTitleArr[i] = "官方公告表";
                headTablesColumnsNameArr[i] = "公告文件,配送商要求,意见填写";
                tablesColumnNameArr[i] = "fileAnnounce,distributorRequire,suggestion";
                //获取额外设置的内容
                List<Map> addContent = biddingProjectMapper.selectAddContent(projectId, biddingProject.getOfficialNoticeId());
                for (Map map1 : addContent) {
                    int index=1;
                    if (map1.get("typeId").toString().equals("4")) {
                        BiddingFileAddcontent contentExtraValue = biddingFileAddcontentMapper.selectByPrimaryKey(map1.get("contentExtraValue").toString());
                        list1.add(contentExtraValue.getFilePath());
                    }else {
                        headTablesColumnsNameArr[i]+=","+map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i]+=",value"+index;
                        if (index==1){

                            OfficialNoticeList.get(0).setValue1(map1.get("contentExtraValue").toString());
                        }else if(index==2){
                            OfficialNoticeList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        }
                        else if(index==3){
                            OfficialNoticeList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        }
                        index++;
                    }
                }
                //获取内容设置表新增内容
                List<Map> mapSetting = biddingProjectMapper.selectSetting(projectId, phaseId);
                for (Map map : mapSetting) {
                    int index=11;
                    if (map.get("contentTypeId").toString().equals("4")) {
                        list1.add(map.get("contentValue").toString());
                    }else {
                        headTablesColumnsNameArr[i]+=","+map.get("contentName").toString();
                        tablesColumnNameArr[i]+=",value"+index;
                        if (index==11){
                            OfficialNoticeList.get(0).setValue11(map.get("contentValue").toString());
                        }else if(index==12){
                            OfficialNoticeList.get(0).setValue12(map.get("contentValue").toString());
                        }else if(index==3){
                            OfficialNoticeList.get(0).setValue13(map.get("contentValue").toString());
                        }
                        index++;
                    }
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
                //获取额外设置的内容
                List<Map> addContent = biddingProjectMapper.selectAddContent(projectId, biddingProject.getProjectSummaryId());
                for (Map map1 : addContent) {
                    int index=1;
                    if (map1.get("typeId").toString().equals("4")) {
                        BiddingFileAddcontent contentExtraValue = biddingFileAddcontentMapper.selectByPrimaryKey(map1.get("contentExtraValue").toString());
                        list1.add(contentExtraValue.getFilePath());
                    }else {
                        headTablesColumnsNameArr[i]+=","+map1.get("contentExtraName").toString();
                        tablesColumnNameArr[i]+=",value"+index;
                        if (index==1){

                            ProjectSummaryList.get(0).setValue1(map1.get("contentExtraValue").toString());
                        }else if(index==2){
                            ProjectSummaryList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        }
                        else if(index==3){
                            ProjectSummaryList.get(0).setValue2(map1.get("contentExtraValue").toString());
                        }
                        index++;
                    }
                }
                //获取内容设置表新增内容
                List<Map> mapSetting = biddingProjectMapper.selectSetting(projectId, phaseId);
                for (Map map : mapSetting) {
                    int index=11;
                    if (map.get("contentTypeId").toString().equals("4")) {
                        list1.add(map.get("contentValue").toString());
                    }else {
                        headTablesColumnsNameArr[i]+=","+map.get("contentName").toString();
                        tablesColumnNameArr[i]+=",value"+index;
                        if (index==11){
                            ProjectSummaryList.get(0).setValue11(map.get("contentValue").toString());
                        }else if(index==12){
                            ProjectSummaryList.get(0).setValue12(map.get("contentValue").toString());
                        }else if(index==3){
                            ProjectSummaryList.get(0).setValue13(map.get("contentValue").toString());
                        }
                        index++;
                    }
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
        //File file = new File(zipPath);
        //String fileName = file.getName();
        //if (file.exists()) {
        //    response.setContentType("application/force-download");// 设置强制下载不打开
        //    try {
        //        response.addHeader("Content-Disposition", "attachment;fileName=" +  java.net.URLEncoder.encode(fileName, "GB2312"));
        //    } catch (UnsupportedEncodingException e) {
        //        e.printStackTrace();
        //    }
        //    byte[] buffer = new byte[1024];
        //    FileInputStream fis = null;
        //    BufferedInputStream bis = null;
        //    try {
        //        fis = new FileInputStream(file);
        //        bis = new BufferedInputStream(fis);
        //        OutputStream outputStream = response.getOutputStream();
        //        int i = bis.read(buffer);
        //        while (i != -1) {
        //            outputStream.write(buffer, 0, i);
        //            i = bis.read(buffer);
        //        }
        //    } catch (Exception e) {
        //        log.error(e.toString(), e);
        //    } finally {
        //        if (bis != null) {
        //            try {
        //                bis.close();
        //                return null;
        //            } catch (IOException e) {
        //                log.error(e.toString(), e);
        //                return new Result<>(false, StatusCode.ERROR, "呀！服务器开小差了！");
        //            }
        //        }
        //        if (fis != null) {
        //            try {
        //                fis.close();
        //                return null;
        //            } catch (IOException e) {
        //                log.error(e.toString(), e);
        //                return new Result<>(false, StatusCode.ERROR, "呀！服务器开小差了！");
        //            }
        //        }
        //    }
        //}
        return null;
    }

}