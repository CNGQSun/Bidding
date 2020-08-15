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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * <br/>
 * Created by Grant on 2020/08/08
 */
@Service
public class BiddingProjectService {

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
    private IdWorker idWorker;

    /**
     * 立项
     *
     * @param map
     * @param userId
     * @param fileMap
     * @param addContent1
     * @return
     */
    public Result insert(Map<String, String> map, String userId, Map<String, MultipartFile> fileMap, List<String> addContent1) {
        try {
            String isSubmt = map.get("isSubmit").toString();
            List<List<String>> addContent = new ArrayList<>();
            ArrayList arrayList = new ArrayList();
            if (addContent1 != null && addContent1.size() > 0) {
                int icount = 0;
                for (String s : addContent1) {
                    arrayList.add(s);
                    icount++;
                    if (icount % 4 == 0) {
                        addContent.add(arrayList);
                        arrayList = new ArrayList();
                    }
                }
            }
            //根据userId查询用户角色
            BiddingUserRole biddingUserRole = biddingUserRoleMapper.selectByUserId(userId);
            BiddingProject biddingProject = new BiddingProject();
            BiddingProjectBulid biddingProjectBulid = new BiddingProjectBulid();
            biddingProject.setId(idWorker.nextId() + "");
            biddingProject.setStatus("0");
            biddingProject.setUserId(userId);
            biddingProject.setDelflag("0");
            String projectPhaseId = map.get("projectPhaseId").toString();
            String versionNum = idWorker.nextId() + "";
            biddingProject.setVersionNum(versionNum);
            //设置所有内容设置版本号
            int i = biddingContentSettingsMapper.updateAllNum(versionNum, projectPhaseId);
            //将内容设置数据备份到内容设置备份表
            biddingContentBakMapper.copySetting(projectPhaseId, versionNum);
            //获取内容设置表中的字段信息
            List<BiddingContentBak> settingsList = biddingContentBakMapper.selectByPhaseId(projectPhaseId, versionNum);
            for (BiddingContentBak biddingContentBak : settingsList) {
                if ((map.get(biddingContentBak.getEnName())) != null) {
                    String value = map.get(biddingContentBak.getEnName());
                    BiddingProjectData biddingProjectData = new BiddingProjectData();
                    biddingProjectData.setId(idWorker.nextId() + "");
                    biddingProjectData.setContentSettingsId(biddingContentBak.getId());
                    biddingProjectData.setProjectId(biddingProject.getId());
                    biddingProjectData.setValue(value);
                    //获取内容设置表里的属性值，并插入project_data
                    biddingProjectDataMapper.insert(biddingProjectData);
                }
            }
            biddingProjectBulid.setId(idWorker.nextId() + "");
            biddingProject.setProjectBulidId(biddingProjectBulid.getId());
            biddingProject.setProjectPhaseNow("1");
            //判断文件夹是否存在，不存在则新建
            String docPath1 = uploadBuild + "/" + biddingProject.getId();
            File docPath = new File(docPath1);
            if (!docPath.exists() && !docPath.isDirectory()) {
                docPath.mkdirs();
            }
            String docPublicTime = map.get("docPublicTime").toString();
            biddingProjectBulid.setDocPublicTime(docPublicTime);
            String typeId = map.get("typeId").toString();
            biddingProject.setTypeId(typeId);
            String name = map.get("name").toString();
            biddingProjectBulid.setName(name);
            String source = map.get("source").toString();
            biddingProjectBulid.setSource(source);
            String provinceId = map.get("provinceId").toString();
            biddingProjectBulid.setProvinceId(provinceId);
            String cityId = map.get("cityId").toString();
            biddingProjectBulid.setCityId(cityId);
            //产品ID有多个，需重点关注
            String productId = map.get("productId").toString();
            String[] split = productId.split(",");
            for (int j = 0; j < split.length; j++) {
                String pproductId = split[j];
                BiddingProjectProduct biddingProjectProduct = new BiddingProjectProduct();
                biddingProjectProduct.setId(idWorker.nextId() + "");
                biddingProjectProduct.setProjectId(biddingProject.getId());
                biddingProjectProduct.setProductId(pproductId);
                biddingProjectProduct.setDelflag("0");
                biddingProjectProductMapper.insert(biddingProjectProduct);
            }
            if (map.get("fileFormal") != null) {
                String fileFormal = map.get("fileFormal").toString();
                biddingProjectBulid.setFileFormal(fileFormal);
            } else {
                MultipartFile fileFormal = fileMap.get("fileFormal");
                String fileName1 = fileFormal.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileFormal.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingProjectBulid.setFileFormal(filePath.getPath());
            }
            if (map.get("fileAsk") != null) {
                String fileAsk = map.get("fileAsk").toString();
                biddingProjectBulid.setFileAsk(fileAsk);
            } else {
                MultipartFile fileAsk = fileMap.get("fileAsk");
                String fileName1 = fileAsk.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileAsk.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingProjectBulid.setFileAsk(filePath.getPath());
            }
            String docInterTime = map.get("docInterTime").toString();
            biddingProjectBulid.setDocInterTime(docInterTime);
            String submitTime = map.get("submitTime").toString();
            biddingProjectBulid.setSubmitTime(submitTime);
            String publicTime = map.get("publicTime").toString();
            biddingProjectBulid.setPublicTime(publicTime);
            String appealTime = map.get("appealTime").toString();
            biddingProjectBulid.setAppealTime(appealTime);
            String noticeTime = map.get("noticeTime").toString();
            biddingProjectBulid.setNoticeTime(noticeTime);
            String suggestion = map.get("suggestion").toString();
            biddingProjectBulid.setSuggestion(suggestion);
            //如果是提交，那么就要走审批流程
            if (isSubmt.equals("0")) {
                //开始走审批流程
                //如果角色是招标专员
                biddingProjectBulid.setGoStatus("3");//0 进行中 1 未进行 2 草稿 3 待审核 4 审核通过 5 审核驳回 6跳过此阶段
                AppFlowApply appFlowApply = null;
                AppFlowApproval appFlowApproval = null;
                if (biddingUserRole.getRoleId().equals("6")) {
                    biddingProjectBulid.setProLabel("国标");
                    appFlowApply = new AppFlowApply();
                    appFlowApply.setId(idWorker.nextId() + "");
                    appFlowApply.setAddDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    appFlowApply.setDelflag("0");
                    appFlowApply.setPhaseId(biddingProject.getProjectBulidId());
                    appFlowApply.setCurrentNode("0");
                    appFlowApply.setFlowId("0");
                    appFlowApply.setStatus("0");
                    appFlowApply.setProjectId(biddingProject.getId());
                    appFlowApply.setUserId(userId);
                    //往申请表里插数据
                    appFlowApplyMapper.insert(appFlowApply);
                    //查找下一个审批用户
                    BiddingUser biddingUser = appFlowNodeMapper.selectAppUser(appFlowApply.getFlowId(), appFlowApply.getCurrentNode());
                    appFlowApproval = new AppFlowApproval();
                    appFlowApproval.setId(idWorker.nextId() + "");
                    appFlowApproval.setProjectPhaseId(biddingProject.getProjectBulidId());
                    appFlowApproval.setDelflag("0");
                    String nextNode = (Integer.valueOf(appFlowApply.getCurrentNode()) + 1) + "";
                    appFlowApproval.setFlowNodeId(nextNode);
                    appFlowApproval.setApplyId(appFlowApply.getId());
                    appFlowApproval.setApproveResult("0");
                    appFlowApproval.setUserId(biddingUser.getId());
                    //往审批表里插数据，为了后续查看是否有待审批的记录
                    appFlowApprovalMapper.insert(appFlowApproval);
                    //如果角色是商务经理
                } else if (biddingUserRole.getRoleId().equals("3")) {
                    //商务经理城市为空 则为省标，城市不为空则为地市标
                    if (biddingProjectBulid.getCityId() == null) {
                        biddingProjectBulid.setProLabel("省标");
                    } else {
                        biddingProjectBulid.setProLabel("地市标");
                    }
                    appFlowApply = new AppFlowApply();
                    appFlowApply.setId(idWorker.nextId() + "");
                    appFlowApply.setAddDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    appFlowApply.setDelflag("0");
                    appFlowApply.setPhaseId(biddingProject.getProjectBulidId());
                    appFlowApply.setCurrentNode("3");
                    appFlowApply.setFlowId("1");
                    appFlowApply.setStatus("0");
                    appFlowApply.setProjectId(biddingProject.getId());
                    appFlowApply.setUserId(userId);
                    //往申请表里插数据
                    appFlowApplyMapper.insert(appFlowApply);
                    //查找下一个审批用户
                    BiddingUser biddingUser = appFlowNodeMapper.selectAppUserSw(appFlowApply.getFlowId(), appFlowApply.getCurrentNode(), userId);
                    appFlowApproval = new AppFlowApproval();
                    appFlowApproval.setId(idWorker.nextId() + "");
                    appFlowApproval.setProjectPhaseId(biddingProject.getProjectBulidId());
                    appFlowApproval.setDelflag("0");
                    String nextNode = (Integer.valueOf(appFlowApply.getCurrentNode()) + 1) + "";
                    appFlowApproval.setFlowNodeId(nextNode);
                    appFlowApproval.setUserId(biddingUser.getId());
                    appFlowApproval.setApproveResult("0");
                    appFlowApproval.setApplyId(appFlowApply.getId());
                    //往审批表里插数据，为了后续查看是否有待审批的记录
                    appFlowApprovalMapper.insert(appFlowApproval);
                }
                //如果是保存
            } else if (isSubmt.equals("1")) {
                biddingProjectBulid.setGoStatus("2");//0 进行中 1 未进行 2 草稿 3 待审核 4 审核通过 5 审核驳回 6跳过此阶段
                //如果角色是招标专员
                if (biddingUserRole.getRoleId().equals("6")) {
                    biddingProjectBulid.setProLabel("国标");
                    //如果角色是商务经理
                } else if (biddingUserRole.getRoleId().equals("3")) {
                    //商务经理城市为空 则为省标，城市不为空则为地市标
                    if (biddingProjectBulid.getCityId() == null) {
                        biddingProjectBulid.setProLabel("省标");
                    } else {
                        biddingProjectBulid.setProLabel("地市标");
                    }
                }
            }
            //将项目数据插入
            biddingProjectMapper.insert(biddingProject);
            if (addContent != null && addContent.size() > 0) {
                for (List<String> list : addContent) {
                    BiddingSettingsExtra biddingSettingsExtra = new BiddingSettingsExtra();
                    String name1 = list.get(0);
                    String contentTypeId = list.get(1);
                    String isNull = list.get(2);
                    String value = list.get(3);
                    biddingSettingsExtra.setId(idWorker.nextId() + "");
                    biddingSettingsExtra.setDelflag("0");
                    biddingSettingsExtra.setProjectId(biddingProject.getId());
                    biddingSettingsExtra.setContentTypeId(contentTypeId);
                    biddingSettingsExtra.setIsNull(isNull);
                    biddingSettingsExtra.setName(name1);
                    biddingSettingsExtra.setValue(value);
                    biddingSettingsExtra.setProjectPhaseId(biddingProject.getProjectBulidId());
                    //往额外设置表里插数据
                    biddingSettingsExtraMapper.insert(biddingSettingsExtra);
                }
            }
            //将立项数据插入
            biddingProjectBulidMapper.insert(biddingProjectBulid);
            BiddingProjectType biddingProjectType = biddingProjectTypeMapper.selectByPrimaryKey(typeId);
            Map resultMap = new HashMap();
            resultMap.put("projectPhase", biddingProjectType.getProjectPhaseId());
            return new Result<>(true, StatusCode.OK, "保存成功", resultMap);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }

    /**
     * 查询所有登录用户创建的项目
     *
     * @param map
     * @param userId
     * @return
     */
    public Result list(Map map, String userId) {
        try {
            Integer currentPage = Integer.valueOf(map.get("currentPage").toString());
            Integer pageSize = Integer.valueOf(map.get("pageSize").toString());
            String name = map.get("name").toString();
            String status = map.get("status").toString();
            List<Map> mapList = biddingProjectMapper.selectByNoDel(name, status, userId);
            PageHelper.startPage(currentPage, pageSize);
            List<Map> maps = biddingProjectMapper.selectByNoDel(name, status, userId);
            PageInfo pageInfo = new PageInfo<>(maps);
            pageInfo.setTotal(mapList.size());
            PageResult pageResult = new PageResult(pageInfo.getTotal(), maps);
            return new Result<>(true, StatusCode.OK, "查询成功", pageResult);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }

    /**
     * 点击新增加载内容设置                                                                                                                                                                                                                                                                                                                                                                                                         o
     *
     * @param projectPhaseId
     * @return
     */
    public Result findContent(String projectPhaseId) {
        try {
            List<BiddingContentSettings> settingsList = biddingContentSettingsMapper.selectByPhaseId(projectPhaseId);
            return new Result<>(true, StatusCode.OK, "查询成功", settingsList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }

    /**
     * 查询所有登录用户创建的项目
     *
     * @param map
     * @param userId
     * @return
     */
    public Result listDeal(Map map, String userId) {
        try {
            Integer currentPage = Integer.valueOf(map.get("currentPage").toString());
            Integer pageSize = Integer.valueOf(map.get("pageSize").toString());
            String name = map.get("name").toString();
            String status = map.get("status").toString();
            List<Map> mapList = biddingProjectMapper.selectDealByNoDel(name, status, userId);
            PageHelper.startPage(currentPage, pageSize);
            List<Map> maps = biddingProjectMapper.selectDealByNoDel(name, status, userId);
            PageInfo pageInfo = new PageInfo<>(maps);
            pageInfo.setTotal(mapList.size());
            PageResult pageResult = new PageResult(pageInfo.getTotal(), maps);
            return new Result<>(true, StatusCode.OK, "查询成功", pageResult);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }

    /**
     * 文件解读
     *
     * @param map
     * @param userId
     * @param fileMap
     * @param addContent1
     * @return
     */
    public Result insertDocInter(Map<String, String> map, String userId, Map<String, MultipartFile> fileMap, List<String> addContent1) {
        try {
            //第一块，直接引用开始
            String isSubmit = map.get("isSubmit").toString();
            List<List<String>> addContent = new ArrayList<>();
            ArrayList arrayList = new ArrayList();
            if (addContent1 != null && addContent1.size() > 0) {
                int icount = 0;
                for (String s : addContent1) {
                    arrayList.add(s);
                    icount++;
                    if (icount % 4 == 0) {
                        addContent.add(arrayList);
                        arrayList = new ArrayList();
                    }
                }
            }
            //根据userId查询用户角色
            BiddingUserRole biddingUserRole = biddingUserRoleMapper.selectByUserId(userId);
            String projectId = map.get("projectId").toString();
            String projectPhaseId = map.get("projectPhaseId").toString();
            BiddingProject biddingProject = new BiddingProject();
            biddingProject.setId(projectId);
            BiddingProject biddingProject1 = biddingProjectMapper.selectOne(biddingProject);
            //查不到记录则退出
            if (biddingProject1 == null) {
                return new Result<>(false, StatusCode.ERROR, "该条记录不存在");
            }
            //修改对应阶段的内容设置的版本号
            biddingContentSettingsMapper.updateAllNum(biddingProject1.getVersionNum(), projectPhaseId);
            //将内容设置数据备份到内容设置备份表
            biddingContentBakMapper.copySetting(projectPhaseId, biddingProject1.getVersionNum());
            //获取内容设置表中的字段信息
            List<BiddingContentBak> settingsList = biddingContentBakMapper.selectByPhaseId(projectPhaseId, biddingProject1.getVersionNum());
            if (settingsList != null) {
                for (BiddingContentBak biddingContentBak : settingsList) {
                    if ((map.get(biddingContentBak.getEnName())) != null) {
                        String value = (map.get(biddingContentBak.getEnName())).toString();
                        BiddingProjectData biddingProjectData = new BiddingProjectData();
                        biddingProjectData.setId(idWorker.nextId() + "");
                        biddingProjectData.setContentSettingsId(biddingContentBak.getId());
                        biddingProjectData.setProjectId(biddingProject1.getId());
                        biddingProjectData.setValue(value);
                        //获取内容设置表里的属性值，并插入project_data
                        biddingProjectDataMapper.insert(biddingProjectData);
                    }
                }
            }
            //第一块，直接引用结束

            biddingProject1.setDocInterpretationId(idWorker.nextId() + "");
            biddingProject1.setProjectPhaseNow("2");
            //更新project表
            biddingProjectMapper.updateByPrimaryKeySelective(biddingProject1);
            //判断文件夹是否存在，不存在则新建
            String docPath1 = uploadDocTerpretation + "/" + biddingProject1.getId();
            File docPath = new File(docPath1);
            if (!docPath.exists() && !docPath.isDirectory()) {
                docPath.mkdirs();
            }
            BiddingDocInterpretation biddingDocInterpretation = new BiddingDocInterpretation();
            biddingDocInterpretation.setId(biddingProject1.getDocInterpretationId());
            //开始拿参数
            String type = map.get("type").toString();
            biddingDocInterpretation.setType(type);
            String range = map.get("range").toString();
            biddingDocInterpretation.setRange(range);
            String noReason = map.get("noReason").toString();
            biddingDocInterpretation.setNoReason(noReason);
            String timeRangeStart = map.get("timeRangeStart").toString();
            biddingDocInterpretation.setTimeRangeStart(timeRangeStart);
            String timeRangeEnd = map.get("timeRangeEnd").toString();
            biddingDocInterpretation.setTimeRangeEnd(timeRangeEnd);
            String commonName = map.get("commonName").toString();
            biddingDocInterpretation.setCommonName(commonName);
            String standards = map.get("standards").toString();
            biddingDocInterpretation.setStandards(standards);
            String qualityLevel = map.get("qualityLevel").toString();
            biddingDocInterpretation.setQualityLevel(qualityLevel);
            String priceLimit = map.get("priceLimit").toString();
            biddingDocInterpretation.setPriceLimit(priceLimit);
            String priceLimitReference = map.get("priceLimitReference").toString();
            biddingDocInterpretation.setPriceLimitReference(priceLimitReference);
            String priceLimitExplain = map.get("priceLimitExplain").toString();
            biddingDocInterpretation.setPriceLimitExplain(priceLimitExplain);
            String solicitingOpinions = map.get("solicitingOpinions").toString();
            biddingDocInterpretation.setSolicitingOpinions(solicitingOpinions);


            if (map.get("fileBuyRules") != null) {
                String fileBuyRules = map.get("fileBuyRules").toString();
                biddingDocInterpretation.setFileBuyRules(fileBuyRules);
            } else {
                MultipartFile fileBuyRules = fileMap.get("fileBuyRules");
                String fileName1 = fileBuyRules.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileBuyRules.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingDocInterpretation.setFileBuyRules(filePath.getPath());
            }

            if (map.get("fileQuotedPrice") != null) {
                String fileQuotedPrice = map.get("fileQuotedPrice").toString();
                biddingDocInterpretation.setFileQuotedPrice(fileQuotedPrice);
            } else {
                MultipartFile fileQuotedPrice = fileMap.get("fileQuotedPrice");
                String fileName1 = fileQuotedPrice.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileQuotedPrice.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingDocInterpretation.setFileQuotedPrice(filePath.getPath());
            }

            if (map.get("fileSolicitingOpinions") != null) {
                String fileSolicitingOpinions = map.get("fileSolicitingOpinions").toString();
                biddingDocInterpretation.setFileSolicitingOpinions(fileSolicitingOpinions);
            } else {
                MultipartFile fileSolicitingOpinions = fileMap.get("fileSolicitingOpinions");
                String fileName1 = fileSolicitingOpinions.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileSolicitingOpinions.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingDocInterpretation.setFileSolicitingOpinions(filePath.getPath());
            }
            String industryInfluence = map.get("industryInfluence").toString();
            biddingDocInterpretation.setIndustryInfluence(industryInfluence);
            String suggestion = map.get("suggestion").toString();
            biddingDocInterpretation.setSuggestion(suggestion);

            //第二块，可以直接引用开始（里面有4处需要更改）
            //提交
            if (isSubmit.equals("0")) {
                //0 进行中 1 未进行 2 草稿 3 待审核 4 审核通过 5 审核驳回 6跳过此阶段
                biddingDocInterpretation.setGoStatus("3");
                //开始走审批流程
                //如果角色是招标专员
                AppFlowApply appFlowApply = null;
                AppFlowApproval appFlowApproval = null;
                if (biddingUserRole.getRoleId().equals("6")) {
                    appFlowApply = new AppFlowApply();
                    appFlowApply.setId(idWorker.nextId() + "");
                    appFlowApply.setAddDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    appFlowApply.setDelflag("0");
                    //********3,每个阶段都需要更改开始**********
                    appFlowApply.setPhaseId(biddingProject1.getDocInterpretationId());
                    //*********3,每个阶段都需要更改结束*********
                    appFlowApply.setProjectId(biddingProject1.getId());
                    appFlowApply.setCurrentNode("0");
                    appFlowApply.setFlowId("0");
                    appFlowApply.setStatus("0");
                    appFlowApply.setUserId(userId);
                    //往申请表里插数据
                    appFlowApplyMapper.insert(appFlowApply);
                    //查找下一个审批用户
                    BiddingUser biddingUser = appFlowNodeMapper.selectAppUser(appFlowApply.getFlowId(), appFlowApply.getCurrentNode());
                    appFlowApproval = new AppFlowApproval();
                    appFlowApproval.setId(idWorker.nextId() + "");
                    //********3,每个阶段都需要更改开始**********
                    appFlowApproval.setProjectPhaseId(biddingProject1.getDocInterpretationId());
                    //*********3,每个阶段都需要更改结束**********

                    appFlowApproval.setDelflag("0");
                    String nextNode = (Integer.valueOf(appFlowApply.getCurrentNode()) + 1) + "";
                    appFlowApproval.setFlowNodeId(nextNode);
                    appFlowApproval.setApplyId(appFlowApply.getId());
                    appFlowApproval.setApproveResult("0");
                    appFlowApproval.setUserId(biddingUser.getId());
                    //往审批表里插数据，为了后续查看是否有待审批的记录
                    appFlowApprovalMapper.insert(appFlowApproval);
                    //如果角色是商务经理
                } else if (biddingUserRole.getRoleId().equals("3")) {
                    appFlowApply = new AppFlowApply();
                    appFlowApply.setId(idWorker.nextId() + "");
                    appFlowApply.setAddDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    appFlowApply.setDelflag("0");

                    //**************,3，每个阶段都需要改开始****************
                    appFlowApply.setPhaseId(biddingProject1.getDocInterpretationId());
                    appFlowApply.setFlowId("2");
                    appFlowApply.setCurrentNode("6");
                    //**************,3，每个阶段都需要改结束*****************
                    appFlowApply.setStatus("0");
                    appFlowApply.setProjectId(biddingProject1.getId());
                    appFlowApply.setUserId(userId);
                    //往申请表里插数据
                    appFlowApplyMapper.insert(appFlowApply);
                    //查找下一个审批用户
                    BiddingUser biddingUser = appFlowNodeMapper.selectAppUserSw(appFlowApply.getFlowId(), appFlowApply.getCurrentNode(), userId);
                    appFlowApproval = new AppFlowApproval();
                    appFlowApproval.setId(idWorker.nextId() + "");

                    //************4，每个阶段都需要改开始************
                    appFlowApproval.setProjectPhaseId(biddingProject1.getDocInterpretationId());
                    //***********,4，每个阶段都需要改结束*************
                    appFlowApproval.setDelflag("0");
                    String nextNode = (Integer.valueOf(appFlowApply.getCurrentNode()) + 1) + "";
                    appFlowApproval.setFlowNodeId(nextNode);
                    appFlowApproval.setUserId(biddingUser.getId());
                    appFlowApproval.setApproveResult("0");
                    appFlowApproval.setApplyId(appFlowApply.getId());
                    //往审批表里插数据，为了后续查看是否有待审批的记录
                    appFlowApprovalMapper.insert(appFlowApproval);
                }
            } else if (isSubmit.equals("1")) {
                //0 进行中 1 未进行 2 草稿 3 待审核 4 审核通过 5 审核驳回 6跳过此阶段
                biddingDocInterpretation.setGoStatus("2");
            }

            //第二块，可以直接引用结束
            //第三块可以直接引用开始(有一处需要更改)
            if (addContent != null && addContent.size() > 0) {
                for (List<String> list : addContent) {
                    BiddingSettingsExtra biddingSettingsExtra = new BiddingSettingsExtra();
                    String name1 = list.get(0);
                    String contentTypeId = list.get(1);
                    String isNull = list.get(2);
                    String value = list.get(3);
                    biddingSettingsExtra.setId(idWorker.nextId() + "");
                    biddingSettingsExtra.setDelflag("0");
                    biddingSettingsExtra.setProjectId(biddingProject1.getId());
                    biddingSettingsExtra.setContentTypeId(contentTypeId);
                    biddingSettingsExtra.setIsNull(isNull);
                    biddingSettingsExtra.setName(name1);
                    biddingSettingsExtra.setValue(value);
                    //************1，每个阶段都需要改开始************
                    biddingSettingsExtra.setProjectPhaseId(biddingProject1.getDocInterpretationId());
                    //************1，每个阶段都需要改开始************
                    //往额外设置表里插数据
                    biddingSettingsExtraMapper.insert(biddingSettingsExtra);
                }
            }
            //第三块可以直接引用结束

            //将文件解读的数据插入
            biddingDocInterpretationMapper.insert(biddingDocInterpretation);
            return new Result<>(true, StatusCode.OK, "保存成功");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new Result<>(true, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }

    /**
     * 竞品收集
     *
     * @param map
     * @param userId
     * @param fileText
     * @param addContent1
     * @return
     */
    public Result insertProCollection(Map<String, String> map, String userId, List<MultipartFile> fileText, List<String> addContent1) {
        try {
            //第一块，直接引用开始
            String isSubmit = map.get("isSubmit").toString();
            List<List<String>> addContent = new ArrayList<>();
            ArrayList arrayList = new ArrayList();
            if (addContent1 != null && addContent1.size() > 0) {
                int icount = 0;
                for (String s : addContent1) {
                    arrayList.add(s);
                    icount++;
                    if (icount % 4 == 0) {
                        addContent.add(arrayList);
                        arrayList = new ArrayList();
                    }
                }
            }
            //根据userId查询用户角色
            BiddingUserRole biddingUserRole = biddingUserRoleMapper.selectByUserId(userId);
            String projectId = map.get("projectId").toString();
            String projectPhaseId = map.get("projectPhaseId").toString();
            BiddingProject biddingProject = new BiddingProject();
            biddingProject.setId(projectId);
            BiddingProject biddingProject1 = biddingProjectMapper.selectOne(biddingProject);
            //查不到记录则退出
            if (biddingProject1 == null) {
                return new Result<>(false, StatusCode.ERROR, "该条记录不存在");
            }
            //修改对应阶段的内容设置的版本号
            biddingContentSettingsMapper.updateAllNum(biddingProject1.getVersionNum(), projectPhaseId);
            //将内容设置数据备份到内容设置备份表
            biddingContentBakMapper.copySetting(projectPhaseId, biddingProject1.getVersionNum());
            //获取内容设置表中的字段信息
            List<BiddingContentBak> settingsList = biddingContentBakMapper.selectByPhaseId(projectPhaseId, biddingProject1.getVersionNum());
            if (settingsList != null) {
                for (BiddingContentBak biddingContentBak : settingsList) {
                    if ((map.get(biddingContentBak.getEnName())) != null) {
                        String value = (map.get(biddingContentBak.getEnName())).toString();
                        BiddingProjectData biddingProjectData = new BiddingProjectData();
                        biddingProjectData.setId(idWorker.nextId() + "");
                        biddingProjectData.setContentSettingsId(biddingContentBak.getId());
                        biddingProjectData.setProjectId(biddingProject1.getId());
                        biddingProjectData.setValue(value);
                        //获取内容设置表里的属性值，并插入project_data
                        biddingProjectDataMapper.insert(biddingProjectData);
                    }
                }
            }
            //第一块，直接引用结束

            biddingProject1.setProductCollectionId(idWorker.nextId() + "");
            biddingProject1.setProjectPhaseNow("3");
            //更新project表
            biddingProjectMapper.updateByPrimaryKeySelective(biddingProject1);
            //判断文件夹是否存在，不存在则新建
            String docPath1 = uploadProCollection + "/" + biddingProject1.getId();
            File docPath = new File(docPath1);
            if (!docPath.exists() && !docPath.isDirectory()) {
                docPath.mkdirs();
            }
            BiddingProductCollection biddingProductCollection = new BiddingProductCollection();
            biddingProductCollection.setId(biddingProject1.getProductCollectionId());
            //******开始拿参数******
            if (map.get("fileText") != null) {
                String fileText1 = map.get("fileText").toString();
                biddingProductCollection.setFileText(fileText1);
            } else {
                for (MultipartFile multipartFile : fileText) {
                    String fileName1 = multipartFile.getOriginalFilename();
                    String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                    File filePath = new File(docPath, fileName);
                    try {
                        multipartFile.transferTo(filePath);
                    } catch (IOException e) {
                        log.error(e.toString(), e);
                        return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                    }
                    biddingProductCollection.setFileText(filePath.getPath() + ",");
                }
            }
            String suggestion = map.get("suggestion").toString();
            biddingProductCollection.setSuggestion(suggestion);
            biddingProductCollection.setGoStatus("4");
            //******拿参数结束******

            //第三块可以直接引用开始(有一处需要更改)
            if (addContent != null && addContent.size() > 0) {
                for (List<String> list : addContent) {
                    BiddingSettingsExtra biddingSettingsExtra = new BiddingSettingsExtra();
                    String name1 = list.get(0);
                    String contentTypeId = list.get(1);
                    String isNull = list.get(2);
                    String value = list.get(3);
                    biddingSettingsExtra.setId(idWorker.nextId() + "");
                    biddingSettingsExtra.setDelflag("0");
                    biddingSettingsExtra.setProjectId(biddingProject1.getId());
                    biddingSettingsExtra.setContentTypeId(contentTypeId);
                    biddingSettingsExtra.setIsNull(isNull);
                    biddingSettingsExtra.setName(name1);
                    biddingSettingsExtra.setValue(value);
                    //************1，每个阶段都需要改开始************
                    biddingSettingsExtra.setProjectPhaseId(biddingProject1.getProductCollectionId());
                    //************1，每个阶段都需要改开始************
                    //往额外设置表里插数据
                    biddingSettingsExtraMapper.insert(biddingSettingsExtra);
                }
            }
            if (isSubmit.equals("0")) {
                //0 进行中 1 未进行 2 草稿 3 待审核 4 审核通过 5 审核驳回 6跳过此阶段
                biddingProductCollection.setGoStatus("3");
            } else if (isSubmit.equals("1")) {
                //0 进行中 1 未进行 2 草稿 3 待审核 4 审核通过 5 审核驳回 6跳过此阶段
                biddingProductCollection.setGoStatus("2");
            }
            //第三块可以直接引用结束

            //将文件解读的数据插入
            biddingProductCollectionMapper.insert(biddingProductCollection);
            return new Result<>(true, StatusCode.OK, "保存成功");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new Result<>(true, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }

    /**
     * 策略分析
     *
     * @param map
     * @param userId
     * @param fileMap
     * @param addContent1
     * @return
     */
    public Result insertStrategyAnalysis(Map<String, String> map, String userId, Map<String, MultipartFile> fileMap, List<String> addContent1) {

        try {
            //第一块，直接引用开始
            String isSubmit = map.get("isSubmit").toString();
            List<List<String>> addContent = new ArrayList<>();
            ArrayList arrayList = new ArrayList();
            if (addContent1 != null && addContent1.size() > 0) {
                int icount = 0;
                for (String s : addContent1) {
                    arrayList.add(s);
                    icount++;
                    if (icount % 4 == 0) {
                        addContent.add(arrayList);
                        arrayList = new ArrayList();
                    }
                }
            }
            //根据userId查询用户角色
            BiddingUserRole biddingUserRole = biddingUserRoleMapper.selectByUserId(userId);
            String projectId = map.get("projectId").toString();
            String projectPhaseId = map.get("projectPhaseId").toString();
            BiddingProject biddingProject = new BiddingProject();
            biddingProject.setId(projectId);
            BiddingProject biddingProject1 = biddingProjectMapper.selectOne(biddingProject);
            //查不到记录则退出
            if (biddingProject1 == null) {
                return new Result<>(false, StatusCode.ERROR, "该条记录不存在");
            }
            //修改对应阶段的内容设置的版本号
            biddingContentSettingsMapper.updateAllNum(biddingProject1.getVersionNum(), projectPhaseId);
            //将内容设置数据备份到内容设置备份表
            biddingContentBakMapper.copySetting(projectPhaseId, biddingProject1.getVersionNum());
            //获取内容设置表中的字段信息
            List<BiddingContentBak> settingsList = biddingContentBakMapper.selectByPhaseId(projectPhaseId, biddingProject1.getVersionNum());
            if (settingsList != null) {
                for (BiddingContentBak biddingContentBak : settingsList) {
                    if ((map.get(biddingContentBak.getEnName())) != null) {
                        String value = (map.get(biddingContentBak.getEnName())).toString();
                        BiddingProjectData biddingProjectData = new BiddingProjectData();
                        biddingProjectData.setId(idWorker.nextId() + "");
                        biddingProjectData.setContentSettingsId(biddingContentBak.getId());
                        biddingProjectData.setProjectId(biddingProject1.getId());
                        biddingProjectData.setValue(value);
                        //获取内容设置表里的属性值，并插入project_data
                        biddingProjectDataMapper.insert(biddingProjectData);
                    }
                }
            }
            //第一块，直接引用结束

            biddingProject1.setStrategyAnalysisId(idWorker.nextId() + "");
            biddingProject1.setProjectPhaseNow("4");
            //更新project表
            biddingProjectMapper.updateByPrimaryKeySelective(biddingProject1);
            //判断文件夹是否存在，不存在则新建
            String docPath1 = uploadStrategyAnalysis + "/" + biddingProject1.getId();
            File docPath = new File(docPath1);
            if (!docPath.exists() && !docPath.isDirectory()) {
                docPath.mkdirs();
            }
            BiddingStrategyAnalysis biddingStrategyAnalysis = new BiddingStrategyAnalysis();
            biddingStrategyAnalysis.setId(biddingProject1.getStrategyAnalysisId());
            //开始拿参数
            if (map.get("fileProductInfo") != null) {
                String fileProductInfo = map.get("fileProductInfo").toString();
                biddingStrategyAnalysis.setFileProductInfo(fileProductInfo);
            } else {
                MultipartFile fileProductInfo = fileMap.get("fileProductInfo");
                String fileName1 = fileProductInfo.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileProductInfo.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingStrategyAnalysis.setFileProductInfo(filePath.getPath());
            }

            if (map.get("fileQualityLevel") != null) {
                String fileQualityLevel = map.get("fileQualityLevel").toString();
                biddingStrategyAnalysis.setFileQualityLevel(fileQualityLevel);
            } else {
                MultipartFile fileQualityLevel = fileMap.get("fileQualityLevel");
                String fileName1 = fileQualityLevel.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileQualityLevel.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingStrategyAnalysis.setFileQualityLevel(filePath.getPath());
            }

            if (map.get("fileProductGro") != null) {
                String fileProductGro = map.get("fileProductGro").toString();
                biddingStrategyAnalysis.setFileProductGro(fileProductGro);
            } else {
                MultipartFile fileProductGro = fileMap.get("fileProductGro");
                String fileName1 = fileProductGro.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileProductGro.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingStrategyAnalysis.setFileProductGro(filePath.getPath());
            }

            if (map.get("fileProduct") != null) {
                String fileProduct = map.get("fileProduct").toString();
                biddingStrategyAnalysis.setFileProduct(fileProduct);
            } else {
                MultipartFile fileProduct = fileMap.get("fileProduct");
                String fileName1 = fileProduct.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileProduct.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingStrategyAnalysis.setFileProduct(filePath.getPath());
            }

            if (map.get("filePriceLimit") != null) {
                String filePriceLimit = map.get("filePriceLimit").toString();
                biddingStrategyAnalysis.setFilePriceLimit(filePriceLimit);
            } else {
                MultipartFile filePriceLimit = fileMap.get("filePriceLimit");
                String fileName1 = filePriceLimit.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    filePriceLimit.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingStrategyAnalysis.setFilePriceLimit(filePath.getPath());
            }

            if (map.get("fileCompeInfo") != null) {
                String fileCompeInfo = map.get("fileCompeInfo").toString();
                biddingStrategyAnalysis.setFileCompeInfo(fileCompeInfo);
            } else {
                MultipartFile fileCompeInfo = fileMap.get("fileCompeInfo");
                String fileName1 = fileCompeInfo.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileCompeInfo.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingStrategyAnalysis.setFileCompeInfo(filePath.getPath());
            }

            if (map.get("fileQualityLevels") != null) {
                String fileQualityLevels = map.get("fileQualityLevels").toString();
                biddingStrategyAnalysis.setFileQualityLevels(fileQualityLevels);
            } else {
                MultipartFile fileQualityLevels = fileMap.get("fileQualityLevels");
                String fileName1 = fileQualityLevels.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileQualityLevels.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingStrategyAnalysis.setFileQualityLevels(filePath.getPath());
            }

            if (map.get("fileCompeGro") != null) {
                String fileCompeGro = map.get("fileCompeGro").toString();
                biddingStrategyAnalysis.setFileCompeGro(fileCompeGro);
            } else {
                MultipartFile fileCompeGro = fileMap.get("fileCompeGro");
                String fileName1 = fileCompeGro.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileCompeGro.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingStrategyAnalysis.setFileCompeGro(filePath.getPath());
            }

            if (map.get("fileQuotationEstimate") != null) {
                String fileQuotationEstimate = map.get("fileQuotationEstimate").toString();
                biddingStrategyAnalysis.setFileQuotationEstimate(fileQuotationEstimate);
            } else {
                MultipartFile fileQuotationEstimate = fileMap.get("fileQuotationEstimate");
                String fileName1 = fileQuotationEstimate.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileQuotationEstimate.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingStrategyAnalysis.setFileQuotationEstimate(filePath.getPath());
            }

            if (map.get("fileQuotationOther") != null) {
                String fileQuotationOther = map.get("fileQuotationOther").toString();
                biddingStrategyAnalysis.setFileQuotationOther(fileQuotationOther);
            } else {
                MultipartFile fileQuotationOther = fileMap.get("fileQuotationOther");
                String fileName1 = fileQuotationOther.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileQuotationOther.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingStrategyAnalysis.setFileQuotationOther(filePath.getPath());
            }

            if (map.get("fileNationalPrice") != null) {
                String fileNationalPrice = map.get("fileNationalPrice").toString();
                biddingStrategyAnalysis.setFileNationalPrice(fileNationalPrice);
            } else {
                MultipartFile fileNationalPrice = fileMap.get("fileNationalPrice");
                String fileName1 = fileNationalPrice.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileNationalPrice.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingStrategyAnalysis.setFileNationalPrice(filePath.getPath());
            }

            String suggestion = map.get("suggestion").toString();
            biddingStrategyAnalysis.setSuggestion(suggestion);

            //第二块，可以直接引用开始（里面有4处需要更改）
            if (isSubmit.equals("0")) {
                //0 进行中 1 未进行 2 草稿 3 待审核 4 审核通过 5 审核驳回 6跳过此阶段
                biddingStrategyAnalysis.setGoStatus("3");
                //开始走审批流程
                //如果角色是招标专员
                AppFlowApply appFlowApply = null;
                AppFlowApproval appFlowApproval = null;
                if (biddingUserRole.getRoleId().equals("6")) {
                    appFlowApply = new AppFlowApply();
                    appFlowApply.setId(idWorker.nextId() + "");
                    appFlowApply.setAddDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    appFlowApply.setDelflag("0");
                    //********1,每个阶段都需要更改开始**********
                    appFlowApply.setPhaseId(biddingProject1.getStrategyAnalysisId());
                    //*********1,每个阶段都需要更改结束*********
                    appFlowApply.setProjectId(biddingProject1.getId());
                    appFlowApply.setCurrentNode("0");
                    appFlowApply.setFlowId("0");
                    appFlowApply.setStatus("0");
                    appFlowApply.setUserId(userId);
                    //往申请表里插数据
                    appFlowApplyMapper.insert(appFlowApply);
                    //查找下一个审批用户
                    BiddingUser biddingUser = appFlowNodeMapper.selectAppUser(appFlowApply.getFlowId(), appFlowApply.getCurrentNode());
                    appFlowApproval = new AppFlowApproval();
                    appFlowApproval.setId(idWorker.nextId() + "");
                    //********2,每个阶段都需要更改开始**********
                    appFlowApproval.setProjectPhaseId(biddingProject1.getStrategyAnalysisId());
                    //*********2,每个阶段都需要更改结束**********

                    appFlowApproval.setDelflag("0");
                    String nextNode = (Integer.valueOf(appFlowApply.getCurrentNode()) + 1) + "";
                    appFlowApproval.setFlowNodeId(nextNode);
                    appFlowApproval.setApplyId(appFlowApply.getId());
                    appFlowApproval.setApproveResult("0");
                    appFlowApproval.setUserId(biddingUser.getId());
                    //往审批表里插数据，为了后续查看是否有待审批的记录
                    appFlowApprovalMapper.insert(appFlowApproval);
                    //如果角色是商务经理
                } else if (biddingUserRole.getRoleId().equals("3")) {
                    appFlowApply = new AppFlowApply();
                    appFlowApply.setId(idWorker.nextId() + "");
                    appFlowApply.setAddDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    appFlowApply.setDelflag("0");

                    //**************,3，每个阶段都需要改开始****************
                    appFlowApply.setPhaseId(biddingProject1.getStrategyAnalysisId());
                    appFlowApply.setFlowId("4");
                    appFlowApply.setCurrentNode("12");
                    //**************,3，每个阶段都需要改结束*****************
                    appFlowApply.setStatus("0");
                    appFlowApply.setProjectId(biddingProject1.getId());
                    appFlowApply.setUserId(userId);
                    //往申请表里插数据
                    appFlowApplyMapper.insert(appFlowApply);
                    //查找下一个审批用户
                    BiddingUser biddingUser = appFlowNodeMapper.selectAppUserSw(appFlowApply.getFlowId(), appFlowApply.getCurrentNode(), userId);
                    appFlowApproval = new AppFlowApproval();
                    appFlowApproval.setId(idWorker.nextId() + "");

                    //************4，每个阶段都需要改开始************
                    appFlowApproval.setProjectPhaseId(biddingProject1.getStrategyAnalysisId());
                    //***********,4，每个阶段都需要改结束*************
                    appFlowApproval.setDelflag("0");
                    String nextNode = (Integer.valueOf(appFlowApply.getCurrentNode()) + 1) + "";
                    appFlowApproval.setFlowNodeId(nextNode);
                    appFlowApproval.setUserId(biddingUser.getId());
                    appFlowApproval.setApproveResult("0");
                    appFlowApproval.setApplyId(appFlowApply.getId());
                    //往审批表里插数据，为了后续查看是否有待审批的记录
                    appFlowApprovalMapper.insert(appFlowApproval);
                }
            } else if (isSubmit.equals("1")) {
                //0 进行中 1 未进行 2 草稿 3 待审核 4 审核通过 5 审核驳回 6跳过此阶段
                biddingStrategyAnalysis.setGoStatus("2");
            }

            //第二块，可以直接引用结束
            //第三块可以直接引用开始(有一处需要更改)
            if (addContent != null && addContent.size() > 0) {
                for (List<String> list : addContent) {
                    BiddingSettingsExtra biddingSettingsExtra = new BiddingSettingsExtra();
                    String name1 = list.get(0);
                    String contentTypeId = list.get(1);
                    String isNull = list.get(2);
                    String value = list.get(3);
                    biddingSettingsExtra.setId(idWorker.nextId() + "");
                    biddingSettingsExtra.setDelflag("0");
                    biddingSettingsExtra.setProjectId(biddingProject1.getId());
                    biddingSettingsExtra.setContentTypeId(contentTypeId);
                    biddingSettingsExtra.setIsNull(isNull);
                    biddingSettingsExtra.setName(name1);
                    biddingSettingsExtra.setValue(value);
                    //************1，每个阶段都需要改开始************
                    biddingSettingsExtra.setProjectPhaseId(biddingProject1.getStrategyAnalysisId());
                    //************1，每个阶段都需要改开始************
                    //往额外设置表里插数据
                    biddingSettingsExtraMapper.insert(biddingSettingsExtra);
                }
            }
            //第三块可以直接引用结束

            //将文件解读的数据插入
            biddingStrategyAnalysisMapper.insert(biddingStrategyAnalysis);
            return new Result<>(true, StatusCode.OK, "保存成功");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new Result<>(true, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }

    /**
     * 信息填报
     *
     * @param map
     * @param userId
     * @param fileMap
     * @param addContent1
     * @return
     */
    public Result insertInfoFilling(Map<String, String> map, String userId, Map<String, MultipartFile> fileMap, List<String> addContent1) {

        try {
            //第一块，直接引用开始
            String isSubmit = map.get("isSubmit").toString();
            List<List<String>> addContent = new ArrayList<>();
            ArrayList arrayList = new ArrayList();
            if (addContent1 != null && addContent1.size() > 0) {
                int icount = 0;
                for (String s : addContent1) {
                    arrayList.add(s);
                    icount++;
                    if (icount % 4 == 0) {
                        addContent.add(arrayList);
                        arrayList = new ArrayList();
                    }
                }
            }
            //根据userId查询用户角色
            BiddingUserRole biddingUserRole = biddingUserRoleMapper.selectByUserId(userId);
            String projectId = map.get("projectId").toString();
            String projectPhaseId = map.get("projectPhaseId").toString();
            BiddingProject biddingProject = new BiddingProject();
            biddingProject.setId(projectId);
            BiddingProject biddingProject1 = biddingProjectMapper.selectOne(biddingProject);
            //查不到记录则退出
            if (biddingProject1 == null) {
                return new Result<>(false, StatusCode.ERROR, "该条记录不存在");
            }
            //修改对应阶段的内容设置的版本号
            biddingContentSettingsMapper.updateAllNum(biddingProject1.getVersionNum(), projectPhaseId);
            //将内容设置数据备份到内容设置备份表
            biddingContentBakMapper.copySetting(projectPhaseId, biddingProject1.getVersionNum());
            //获取内容设置表中的字段信息
            List<BiddingContentBak> settingsList = biddingContentBakMapper.selectByPhaseId(projectPhaseId, biddingProject1.getVersionNum());
            if (settingsList != null) {
                for (BiddingContentBak biddingContentBak : settingsList) {
                    if ((map.get(biddingContentBak.getEnName())) != null) {
                        String value = (map.get(biddingContentBak.getEnName())).toString();
                        BiddingProjectData biddingProjectData = new BiddingProjectData();
                        biddingProjectData.setId(idWorker.nextId() + "");
                        biddingProjectData.setContentSettingsId(biddingContentBak.getId());
                        biddingProjectData.setProjectId(biddingProject1.getId());
                        biddingProjectData.setValue(value);
                        //获取内容设置表里的属性值，并插入project_data
                        biddingProjectDataMapper.insert(biddingProjectData);
                    }
                }
            }
            //第一块，直接引用结束

            biddingProject1.setInfoFillingId(idWorker.nextId() + "");
            biddingProject1.setProjectPhaseNow("5");
            //更新project表
            biddingProjectMapper.updateByPrimaryKeySelective(biddingProject1);
            //判断文件夹是否存在，不存在则新建
            String docPath1 = uploadInfoFilling + "/" + biddingProject1.getId();
            File docPath = new File(docPath1);
            if (!docPath.exists() && !docPath.isDirectory()) {
                docPath.mkdirs();
            }
            BiddingInfoFilling biddingInfoFilling = new BiddingInfoFilling();
            biddingInfoFilling.setId(biddingProject1.getInfoFillingId());
            //开始拿参数
            if (map.get("fileOnlineFilling") != null) {
                String fileOnlineFilling = map.get("fileOnlineFilling").toString();
                biddingInfoFilling.setFileOnlineFilling(fileOnlineFilling);
            } else {
                MultipartFile fileOnlineFilling = fileMap.get("fileOnlineFilling");
                String fileName1 = fileOnlineFilling.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileOnlineFilling.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingInfoFilling.setFileOnlineFilling(filePath.getPath());
            }

            if (map.get("fileApplicationSeal") != null) {
                String fileApplicationSeal = map.get("fileApplicationSeal").toString();
                biddingInfoFilling.setFileApplicationSeal(fileApplicationSeal);
            } else {
                MultipartFile fileApplicationSeal = fileMap.get("fileApplicationSeal");
                String fileName1 = fileApplicationSeal.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileApplicationSeal.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingInfoFilling.setFileApplicationSeal(filePath.getPath());
            }

            if (map.get("fileInfoList") != null) {
                String fileInfoList = map.get("fileInfoList").toString();
                biddingInfoFilling.setFileInfoList(fileInfoList);
            } else {
                MultipartFile fileInfoList = fileMap.get("fileInfoList");
                String fileName1 = fileInfoList.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileInfoList.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingInfoFilling.setFileInfoList(filePath.getPath());
            }

            if (map.get("fileNameOa") != null) {
                String fileNameOa = map.get("fileNameOa").toString();
                biddingInfoFilling.setFileNameOa(fileNameOa);
            } else {
                MultipartFile fileNameOa = fileMap.get("fileNameOa");
                String fileName1 = fileNameOa.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileNameOa.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingInfoFilling.setFileNameOa(filePath.getPath());
            }

            if (map.get("fileDetailsSpecial") != null) {
                String fileDetailsSpecial = map.get("fileDetailsSpecial").toString();
                biddingInfoFilling.setFileDetailsSpecial(fileDetailsSpecial);
            } else {
                MultipartFile fileDetailsSpecial = fileMap.get("fileDetailsSpecial");
                String fileName1 = fileDetailsSpecial.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileDetailsSpecial.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingInfoFilling.setFileDetailsSpecial(filePath.getPath());
            }

            String SubmissionTime = map.get("SubmissionTime").toString();
            biddingInfoFilling.setSubmissionTime(SubmissionTime);

            if (map.get("filePriceApp") != null) {
                String filePriceApp = map.get("filePriceApp").toString();
                biddingInfoFilling.setFilePriceApp(filePriceApp);
            } else {
                MultipartFile filePriceApp = fileMap.get("filePriceApp");
                String fileName1 = filePriceApp.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    filePriceApp.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingInfoFilling.setFilePriceApp(filePath.getPath());
            }

            if (map.get("filePriceLetter") != null) {
                String filePriceLetter = map.get("filePriceLetter").toString();
                biddingInfoFilling.setFilePriceLetter(filePriceLetter);
            } else {
                MultipartFile filePriceLetter = fileMap.get("filePriceLetter");
                String fileName1 = filePriceLetter.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    filePriceLetter.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingInfoFilling.setFilePriceLetter(filePath.getPath());
            }

            if (map.get("fileInfoChange") != null) {
                String fileInfoChange = map.get("fileInfoChange").toString();
                biddingInfoFilling.setFileInfoChange(fileInfoChange);
            } else {
                MultipartFile fileInfoChange = fileMap.get("fileInfoChange");
                String fileName1 = fileInfoChange.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileInfoChange.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingInfoFilling.setFileInfoChange(filePath.getPath());
            }

            if (map.get("fileOther") != null) {
                String fileOther = map.get("fileOther").toString();
                biddingInfoFilling.setFileOther(fileOther);
            } else {
                MultipartFile fileOther = fileMap.get("fileOther");
                String fileName1 = fileOther.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileOther.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingInfoFilling.setFileOther(filePath.getPath());
            }


            String suggestion = map.get("suggestion").toString();
            biddingInfoFilling.setSuggestion(suggestion);

            //第二块，可以直接引用开始（里面有4处需要更改）
            if (isSubmit.equals("0")) {
                //0 进行中 1 未进行 2 草稿 3 待审核 4 审核通过 5 审核驳回 6跳过此阶段
                biddingInfoFilling.setGoStatus("3");
                //开始走审批流程
                //如果角色是招标专员
                AppFlowApply appFlowApply = null;
                AppFlowApproval appFlowApproval = null;
                if (biddingUserRole.getRoleId().equals("6")) {
                    appFlowApply = new AppFlowApply();
                    appFlowApply.setId(idWorker.nextId() + "");
                    appFlowApply.setAddDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    appFlowApply.setDelflag("0");
                    //********1,每个阶段都需要更改开始**********
                    appFlowApply.setPhaseId(biddingProject1.getInfoFillingId());
                    //*********1,每个阶段都需要更改结束*********
                    appFlowApply.setProjectId(biddingProject1.getId());
                    appFlowApply.setCurrentNode("0");
                    appFlowApply.setFlowId("0");
                    appFlowApply.setStatus("0");
                    appFlowApply.setUserId(userId);
                    //往申请表里插数据
                    appFlowApplyMapper.insert(appFlowApply);
                    //查找下一个审批用户
                    BiddingUser biddingUser = appFlowNodeMapper.selectAppUser(appFlowApply.getFlowId(), appFlowApply.getCurrentNode());
                    appFlowApproval = new AppFlowApproval();
                    appFlowApproval.setId(idWorker.nextId() + "");
                    //********2,每个阶段都需要更改开始**********
                    appFlowApproval.setProjectPhaseId(biddingProject1.getInfoFillingId());
                    //*********2,每个阶段都需要更改结束**********

                    appFlowApproval.setDelflag("0");
                    String nextNode = (Integer.valueOf(appFlowApply.getCurrentNode()) + 1) + "";
                    appFlowApproval.setFlowNodeId(nextNode);
                    appFlowApproval.setApplyId(appFlowApply.getId());
                    appFlowApproval.setApproveResult("0");
                    appFlowApproval.setUserId(biddingUser.getId());
                    //往审批表里插数据，为了后续查看是否有待审批的记录
                    appFlowApprovalMapper.insert(appFlowApproval);
                    //如果角色是商务经理
                } else if (biddingUserRole.getRoleId().equals("3")) {
                    appFlowApply = new AppFlowApply();
                    appFlowApply.setId(idWorker.nextId() + "");
                    appFlowApply.setAddDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    appFlowApply.setDelflag("0");

                    //**************,3，每个阶段都需要改开始****************
                    appFlowApply.setPhaseId(biddingProject1.getInfoFillingId());
                    appFlowApply.setFlowId("5");
                    appFlowApply.setCurrentNode("15");
                    //**************,3，每个阶段都需要改结束*****************
                    appFlowApply.setStatus("0");
                    appFlowApply.setProjectId(biddingProject1.getId());
                    appFlowApply.setUserId(userId);
                    //往申请表里插数据
                    appFlowApplyMapper.insert(appFlowApply);
                    //查找下一个审批用户
                    BiddingUser biddingUser = appFlowNodeMapper.selectAppUserSw(appFlowApply.getFlowId(), appFlowApply.getCurrentNode(), userId);
                    appFlowApproval = new AppFlowApproval();
                    appFlowApproval.setId(idWorker.nextId() + "");

                    //************4，每个阶段都需要改开始************
                    appFlowApproval.setProjectPhaseId(biddingProject1.getInfoFillingId());
                    //***********,4，每个阶段都需要改结束*************
                    appFlowApproval.setDelflag("0");
                    String nextNode = (Integer.valueOf(appFlowApply.getCurrentNode()) + 1) + "";
                    appFlowApproval.setFlowNodeId(nextNode);
                    appFlowApproval.setUserId(biddingUser.getId());
                    appFlowApproval.setApproveResult("0");
                    appFlowApproval.setApplyId(appFlowApply.getId());
                    //往审批表里插数据，为了后续查看是否有待审批的记录
                    appFlowApprovalMapper.insert(appFlowApproval);
                }
            } else if (isSubmit.equals("1")) {
                //0 进行中 1 未进行 2 草稿 3 待审核 4 审核通过 5 审核驳回 6跳过此阶段
                biddingInfoFilling.setGoStatus("2");
            }
            //第二块，可以直接引用结束
            //第三块可以直接引用开始(有一处需要更改)
            if (addContent != null && addContent.size() > 0) {
                for (List<String> list : addContent) {
                    BiddingSettingsExtra biddingSettingsExtra = new BiddingSettingsExtra();
                    String name1 = list.get(0);
                    String contentTypeId = list.get(1);
                    String isNull = list.get(2);
                    String value = list.get(3);
                    biddingSettingsExtra.setId(idWorker.nextId() + "");
                    biddingSettingsExtra.setDelflag("0");
                    biddingSettingsExtra.setProjectId(biddingProject1.getId());
                    biddingSettingsExtra.setContentTypeId(contentTypeId);
                    biddingSettingsExtra.setIsNull(isNull);
                    biddingSettingsExtra.setName(name1);
                    biddingSettingsExtra.setValue(value);
                    //************1，每个阶段都需要改开始************
                    biddingSettingsExtra.setProjectPhaseId(biddingProject1.getInfoFillingId());
                    //************1，每个阶段都需要改开始************
                    //往额外设置表里插数据
                    biddingSettingsExtraMapper.insert(biddingSettingsExtra);
                }
            }
            //第三块可以直接引用结束

            //将文件解读的数据插入
            biddingInfoFillingMapper.insert(biddingInfoFilling);
            return new Result<>(true, StatusCode.OK, "保存成功");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new Result<>(true, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }

    /**
     * 官方公告
     *
     * @param map
     * @param userId
     * @param fileMap
     * @param addContent1
     * @return
     */
    public Result officialNotice(Map<String, String> map, String userId, Map<String, MultipartFile> fileMap, List<String> addContent1) {

        try {
            //第一块，直接引用开始
            String isSubmit = map.get("isSubmit").toString();
            List<List<String>> addContent = new ArrayList<>();
            ArrayList arrayList = new ArrayList();
            if (addContent1 != null && addContent1.size() > 0) {
                int icount = 0;
                for (String s : addContent1) {
                    arrayList.add(s);
                    icount++;
                    if (icount % 4 == 0) {
                        addContent.add(arrayList);
                        arrayList = new ArrayList();
                    }
                }
            }
            //根据userId查询用户角色
            BiddingUserRole biddingUserRole = biddingUserRoleMapper.selectByUserId(userId);
            String projectId = map.get("projectId").toString();
            String projectPhaseId = map.get("projectPhaseId").toString();
            BiddingProject biddingProject = new BiddingProject();
            biddingProject.setId(projectId);
            BiddingProject biddingProject1 = biddingProjectMapper.selectOne(biddingProject);
            //查不到记录则退出
            if (biddingProject1 == null) {
                return new Result<>(false, StatusCode.ERROR, "该条记录不存在");
            }
            //修改对应阶段的内容设置的版本号
            biddingContentSettingsMapper.updateAllNum(biddingProject1.getVersionNum(), projectPhaseId);
            //将内容设置数据备份到内容设置备份表
            biddingContentBakMapper.copySetting(projectPhaseId, biddingProject1.getVersionNum());
            //获取内容设置表中的字段信息
            List<BiddingContentBak> settingsList = biddingContentBakMapper.selectByPhaseId(projectPhaseId, biddingProject1.getVersionNum());
            if (settingsList != null) {
                for (BiddingContentBak biddingContentBak : settingsList) {
                    if ((map.get(biddingContentBak.getEnName())) != null) {
                        String value = (map.get(biddingContentBak.getEnName())).toString();
                        BiddingProjectData biddingProjectData = new BiddingProjectData();
                        biddingProjectData.setId(idWorker.nextId() + "");
                        biddingProjectData.setContentSettingsId(biddingContentBak.getId());
                        biddingProjectData.setProjectId(biddingProject1.getId());
                        biddingProjectData.setValue(value);
                        //获取内容设置表里的属性值，并插入project_data
                        biddingProjectDataMapper.insert(biddingProjectData);
                    }
                }
            }
            //第一块，直接引用结束

            biddingProject1.setOfficialNoticeId(idWorker.nextId() + "");
            biddingProject1.setProjectPhaseNow("6");
            //更新project表
            biddingProjectMapper.updateByPrimaryKeySelective(biddingProject1);
            //判断文件夹是否存在，不存在则新建
            String docPath1 = uploadOfficialNotice + "/" + biddingProject1.getId();
            File docPath = new File(docPath1);
            if (!docPath.exists() && !docPath.isDirectory()) {
                docPath.mkdirs();
            }
            BiddingOfficialNotice biddingOfficialNotice = new BiddingOfficialNotice();
            biddingOfficialNotice.setId(biddingProject1.getOfficialNoticeId());
            //开始拿参数
            if (map.get("fileAnnounce") != null) {
                String fileAnnounce = map.get("fileAnnounce").toString();
                biddingOfficialNotice.setFileAnnounce(fileAnnounce);
            } else {
                MultipartFile fileAnnounce = fileMap.get("fileAnnounce");
                String fileName1 = fileAnnounce.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    fileAnnounce.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingOfficialNotice.setFileAnnounce(filePath.getPath());
            }

            if (map.get("distributorRequire") != null) {
                String distributorRequire = map.get("distributorRequire").toString();
                biddingOfficialNotice.setDistributorRequire(distributorRequire);
            } else {
                MultipartFile distributorRequire = fileMap.get("distributorRequire");
                String fileName1 = distributorRequire.getOriginalFilename();
                String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
                File filePath = new File(docPath, fileName);
                try {
                    distributorRequire.transferTo(filePath);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
                }
                biddingOfficialNotice.setDistributorRequire(filePath.getPath());
            }

            String suggestion = map.get("suggestion").toString();
            biddingOfficialNotice.setSuggestion(suggestion);

            //第二块，可以直接引用开始（里面有4处需要更改）
            if (isSubmit.equals("0")) {
                //0 进行中 1 未进行 2 草稿 3 待审核 4 审核通过 5 审核驳回 6跳过此阶段
                biddingOfficialNotice.setGoStatus("3");
                //开始走审批流程
                //如果角色是招标专员
                AppFlowApply appFlowApply = null;
                AppFlowApproval appFlowApproval = null;
                if (biddingUserRole.getRoleId().equals("6")) {
                    appFlowApply = new AppFlowApply();
                    appFlowApply.setId(idWorker.nextId() + "");
                    appFlowApply.setAddDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    appFlowApply.setDelflag("0");
                    //********1,每个阶段都需要更改开始**********
                    appFlowApply.setPhaseId(biddingProject1.getOfficialNoticeId());
                    //*********1,每个阶段都需要更改结束*********
                    appFlowApply.setProjectId(biddingProject1.getId());
                    appFlowApply.setCurrentNode("0");
                    appFlowApply.setFlowId("0");
                    appFlowApply.setStatus("0");
                    appFlowApply.setUserId(userId);
                    //往申请表里插数据
                    appFlowApplyMapper.insert(appFlowApply);
                    //查找下一个审批用户
                    BiddingUser biddingUser = appFlowNodeMapper.selectAppUser(appFlowApply.getFlowId(), appFlowApply.getCurrentNode());
                    appFlowApproval = new AppFlowApproval();
                    appFlowApproval.setId(idWorker.nextId() + "");
                    //********2,每个阶段都需要更改开始**********
                    appFlowApproval.setProjectPhaseId(biddingProject1.getOfficialNoticeId());
                    //*********2,每个阶段都需要更改结束**********

                    appFlowApproval.setDelflag("0");
                    String nextNode = (Integer.valueOf(appFlowApply.getCurrentNode()) + 1) + "";
                    appFlowApproval.setFlowNodeId(nextNode);
                    appFlowApproval.setApplyId(appFlowApply.getId());
                    appFlowApproval.setApproveResult("0");
                    appFlowApproval.setUserId(biddingUser.getId());
                    //往审批表里插数据，为了后续查看是否有待审批的记录
                    appFlowApprovalMapper.insert(appFlowApproval);
                    //如果角色是商务经理
                } else if (biddingUserRole.getRoleId().equals("3")) {
                    appFlowApply = new AppFlowApply();
                    appFlowApply.setId(idWorker.nextId() + "");
                    appFlowApply.setAddDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    appFlowApply.setDelflag("0");

                    //**************,3，每个阶段都需要改开始****************
                    appFlowApply.setPhaseId(biddingProject1.getOfficialNoticeId());
                    appFlowApply.setFlowId("6");
                    appFlowApply.setCurrentNode("18");
                    //**************,3，每个阶段都需要改结束*****************
                    appFlowApply.setStatus("0");
                    appFlowApply.setProjectId(biddingProject1.getId());
                    appFlowApply.setUserId(userId);
                    //往申请表里插数据
                    appFlowApplyMapper.insert(appFlowApply);
                    //查找下一个审批用户
                    BiddingUser biddingUser = appFlowNodeMapper.selectAppUserSw(appFlowApply.getFlowId(), appFlowApply.getCurrentNode(), userId);
                    appFlowApproval = new AppFlowApproval();
                    appFlowApproval.setId(idWorker.nextId() + "");

                    //************4，每个阶段都需要改开始************
                    appFlowApproval.setProjectPhaseId(biddingProject1.getOfficialNoticeId());
                    //***********,4，每个阶段都需要改结束*************
                    appFlowApproval.setDelflag("0");
                    String nextNode = (Integer.valueOf(appFlowApply.getCurrentNode()) + 1) + "";
                    appFlowApproval.setFlowNodeId(nextNode);
                    appFlowApproval.setUserId(biddingUser.getId());
                    appFlowApproval.setApproveResult("0");
                    appFlowApproval.setApplyId(appFlowApply.getId());
                    //往审批表里插数据，为了后续查看是否有待审批的记录
                    appFlowApprovalMapper.insert(appFlowApproval);
                }
            } else if (isSubmit.equals("1")) {
                //0 进行中 1 未进行 2 草稿 3 待审核 4 审核通过 5 审核驳回 6跳过此阶段
                biddingOfficialNotice.setGoStatus("2");
            }

            //第二块，可以直接引用结束
            //第三块可以直接引用开始(有一处需要更改)
            if (addContent != null && addContent.size() > 0) {
                for (List<String> list : addContent) {
                    BiddingSettingsExtra biddingSettingsExtra = new BiddingSettingsExtra();
                    String name1 = list.get(0);
                    String contentTypeId = list.get(1);
                    String isNull = list.get(2);
                    String value = list.get(3);
                    biddingSettingsExtra.setId(idWorker.nextId() + "");
                    biddingSettingsExtra.setDelflag("0");
                    biddingSettingsExtra.setProjectId(biddingProject1.getId());
                    biddingSettingsExtra.setContentTypeId(contentTypeId);
                    biddingSettingsExtra.setIsNull(isNull);
                    biddingSettingsExtra.setName(name1);
                    biddingSettingsExtra.setValue(value);
                    //************1，每个阶段都需要改开始************
                    biddingSettingsExtra.setProjectPhaseId(biddingProject1.getOfficialNoticeId());
                    //************1，每个阶段都需要改开始************
                    //往额外设置表里插数据
                    biddingSettingsExtraMapper.insert(biddingSettingsExtra);
                }
            }
            //第三块可以直接引用结束

            //将文件解读的数据插入
            biddingOfficialNoticeMapper.insert(biddingOfficialNotice);
            return new Result<>(true, StatusCode.OK, "保存成功");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new Result<>(true, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }
}