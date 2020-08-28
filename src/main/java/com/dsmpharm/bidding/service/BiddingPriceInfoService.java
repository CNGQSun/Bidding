package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.controller.BiddingProductController;
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
import java.io.File;
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

    public Result exportProject(Map map) {

        String projectId = map.get("projectId").toString();
        //BiddingProject biddingProject = biddingProjectMapper.selectByPrimaryKey(projectId);
        //BiddingProjectType biddingProjectType = biddingProjectTypeMapper.selectByPrimaryKey(biddingProject.getTypeId());
        //String proPhase=biddingProjectType.getProjectPhaseId();
        //if (biddingProject.getIsSkip().equals("0")){
        //    if (proPhase.contains("3")){
        //        proPhase = proPhase.replace(",3", "");
        //    }
        //}
        //
        ////多个sheet页同时导出
        //String DBType = "SQLServer2008";
        //String docPath1 = uploadBase+projectId+"/" ;
        //File docPath = new File(docPath1);
        //if (!docPath.exists() && !docPath.isDirectory()) {
        //    docPath.mkdirs();
        //}
        //String exportFilePath3 = uploadBase+projectId+"/text.xls";
        //
        //String[] split = proPhase.split(",");
        //String[] sheetNameArr = new String[split.length];
        //String[] tableTitleArr = new String[split.length];
        //String[] headTablesColumnsNameArr = new String[split.length];
        //String[] tablesColumnNameArr = new String[split.length];
        //
        //List<Object> list = new ArrayList<Object>();
        //for (int i = 0; i <split.length; i++) {
        //    String phaseId=split[i];
        //    if (phaseId.equals("1")){
        //        List<LinkedHashMap> mapBuildList = biddingProjectMapper.selectBuildList(projectId);
        //        sheetNameArr[i]="立项";
        //        tableTitleArr[i]="立项表";
        //        headTablesColumnsNameArr[i]="文件发布时间,项目类型,项目名称,项目来源,省份,城市,产品,正式稿文件,征求稿文件,文件解读时间,递交/填报资料时间,公示期,申诉期,公告期,项目标签,意见";
        //        tablesColumnNameArr[i]="doc_public_time,type_id,name,source,province_id,city_id,product_id,file_formal,file_ask,doc_inter_time,submit_time,public_time,appeal_time,notice_time,pro_label,suggestion";
        //        list.add(mapBuildList);
        //    }else if (phaseId.equals("2")){
        //        List<Map> mapDocInterpretationList = biddingProjectMapper.selectInterpretationList(projectId);
        //        sheetNameArr[i]="文件解读";
        //        tableTitleArr[i]="文件解读表";
        //        headTablesColumnsNameArr[i]="参加招标的品种,实施范围,不参加本次招标的原因,执行时间范围起始,执行时间范围终止,通用名,规格,剂型,质量层次,限价制定,限价的参考值,限价的说明,采购规则,报价,行业影响,公司自身产品影响,征求稿意见,征求稿意见文件,意见填写";
        //        tablesColumnNameArr[i]="type,range,no_reason,time_range_start,time_range_end,common_name,standards,dosage_form,quality_level,price_limit,price_limit_reference,price_limit_explain,file_buy_rules,file_quoted_price,industry_influence,self_influence,soliciting_opinions,file_soliciting_opinions,suggestion";
        //        list.add(mapDocInterpretationList);
        //    }else if (phaseId.equals("3")){
        //        List<Map> mapProCollectionList = biddingProjectMapper.selectProCollectionList(projectId);
        //        sheetNameArr[i]="竞品收集";
        //        tableTitleArr[i]="竞品收集表";
        //        headTablesColumnsNameArr[i]="文件或内容,意见填写";
        //        tablesColumnNameArr[i]="file_text,suggestion";
        //        list.add(mapProCollectionList);
        //    }else if (phaseId.equals("4")){
        //        List<Map> mapStrategyAnalysisList = biddingProjectMapper.selectStrategyAnalysisList(projectId);
        //        sheetNameArr[i]="策略分析";
        //        tableTitleArr[i]="策略分析表";
        //        headTablesColumnsNameArr[i]="产品信息,产品质量层次,产品分组,产品,其他产品限价,竞品信息,竞品质量层次,竞品分组,报价预估,其他报价预估,全国联动价格,意见填写";
        //        tablesColumnNameArr[i]="file_product_info,file_quality_level,file_product_gro,file_product,file_price_limit,file_compe_info,file_quality_levels,file_compe_gro,file_quotation_estimate,file_quotation_other,file_national_price,suggestion";
        //        list.add(mapStrategyAnalysisList);
        //    }else if (phaseId.equals("5")){
        //        List<Map> mapInfoFillingList = biddingProjectMapper.selectInfoFillingList(projectId);
        //        sheetNameArr[i]="信息填报";
        //        tableTitleArr[i]="信息填报表";
        //        headTablesColumnsNameArr[i]="线上填报,盖章申请,资料清单,OA文件名,特殊文件明细,递交时间记录,价格申请,价格申诉函,信息变更申诉函,其他文件,意见填写";
        //        tablesColumnNameArr[i]="file_online_filling,file_application_seal,file_info_list,file_name_oa,file_details_special,Submission_time,file_price_app,file_price_letter,file_info_change,file_other,suggestion";
        //        list.add(mapInfoFillingList);
        //    }else if (phaseId.equals("6")){
        //        List<Map> OfficialNoticeList = biddingProjectMapper.selectOfficialNoticeList(projectId);
        //        sheetNameArr[i]="官方公告";
        //        tableTitleArr[i]="官方公告表";
        //        headTablesColumnsNameArr[i]="公告文件,配送商要求,意见填写";
        //        tablesColumnNameArr[i]="file_announce,distributor_require,suggestion";
        //        list.add(OfficialNoticeList);
        //    }else if (phaseId.equals("7")){
        //        List<Map> ProjectSummaryList = biddingProjectMapper.selectProjectSummaryList(projectId);
        //        sheetNameArr[i]="项目总结";
        //        tableTitleArr[i]="项目总结表";
        //        headTablesColumnsNameArr[i]="产品ID,参与竞标产品状态,较上一轮降幅,竞品情况,备案,临床,失败原因,下一步工作,预估下一轮启动时间,启动时间,意见填写";
        //        tablesColumnNameArr[i]="product_id,status,last_round_decline,competitive_produc,is_record,is_clinical,failure_reasons,next_step,estimated_time,start_time,suggestion";
        //        list.add(ProjectSummaryList);
        //    }
        //}
        ////new POIExcelUtils(exportFilePath3,sheetNameArr,tablesColumnArr,list);
        //POIExcelUtils poiExcel = new POIExcelUtils(sheetNameArr,tableTitleArr,headTablesColumnsNameArr,list,tablesColumnNameArr);
        //poiExcel.excelDataExport(exportFilePath3);
        //return null;

        //多个sheet页同时导出
        String DBType = "SQLServer2008";
        List<Map> queryList = biddingSettingsExtraMapper.selectByProjectId(projectId);
        List<Map> queryList2 = biddingSettingsExtraMapper.selectByProjectId(projectId);
        String docPath1 = uploadBase+projectId+"/" ;
        File docPath = new File(docPath1);
        if (!docPath.exists() && !docPath.isDirectory()) {
            docPath.mkdirs();
        }
        String exportFilePath3 = uploadBase+projectId+"/text.xls";
        String[] sheetNameArr = {"用户信息","学生信息"};
        String[] tableTitleArr = {"用户信息表","学生信息表"};
        String[] headTablesColumnsNameArr = {"ID,名称,属性值","ID,名称,属性值"};
        String[] tablesColumnNameArr = {"id,name,value","id,name,value"};
        List<Object> list = new ArrayList<Object>();
        list.add(queryList);
        list.add(queryList2);
        //new POIExcelUtils(exportFilePath3,sheetNameArr,tablesColumnArr,list);
        POIExcelUtils poiExcel = new POIExcelUtils(sheetNameArr,tableTitleArr,headTablesColumnsNameArr,list,tablesColumnNameArr);
        poiExcel.excelDataExport(exportFilePath3);
        return null;
        //单个sheet页导出
	/*
	//sheet页的名字
	String sheetName = "用户列表";
	//表格标题名
	String tableTitle = "用户信息表";
	//表头列名数组
	String[] headTableColumnsNameArr = {"用户名","密码","年龄","性别","手机号","邮箱"};
	String sql = "select * from userinfo";
	String DBType = "SQLServer2008";
	List<UserInfo> queryList = dBHandleUtils.findAll(sql, DBType, UserInfo.class);
	String[] columnNameArr = {"userName","password","age","sex","tel","email"};
	POIExcelUtils poiExcel = new POIExcelUtils(sheetName,tableTitle,headTableColumnsNameArr,queryList,columnNameArr);
	//设置导出路径
	String exportFilePath = "C:\\Users\\Administrator\\Desktop\,est\\userinfo.xls";
	poiExcel.excelDataExport(exportFilePath);

	//sheet页的名字
	String sheetName2 = "学生列表";
	//表格标题名
	String tableTitle2 = "学生信息表";
	//表头列名数组
	String[] headTableColumnsNameArr2 = {"学号","姓名","年龄","性别"};
	String sql2 = "select * from studentinfo";
	List<StudentInfo> queryList2 = dBHandleUtils.findAll(sql2, DBType, StudentInfo.class);
	String[] columnNameArr2 = {"sid","studName","age","sex"};
	poiExcel = new POIExcelUtils(sheetName2,tableTitle2,headTableColumnsNameArr2,queryList2,columnNameArr2);
	//设置导出路径
	String exportFilePath2 = "C:\\Users\\Administrator\\Desktop\,est\\student.xls";
	poiExcel.excelDataExport(exportFilePath2);
	*/
    }
}