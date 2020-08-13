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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param addContent
     * @return
     */
    public Result insert(Map<String, String> map, String userId, Map<String, MultipartFile> fileMap, List<List<String>> addContent) {
        try {
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
            biddingContentBakMapper.copySetting(projectPhaseId);
            //获取内容设置表中的字段信息
            List<BiddingContentBak> settingsList = biddingContentBakMapper.selectByPhaseId(projectPhaseId, versionNum);
            for (BiddingContentBak biddingContentBak : settingsList) {
                String value = map.get(biddingContentBak.getEnName());
                BiddingProjectData biddingProjectData = new BiddingProjectData();
                biddingProjectData.setId(idWorker.nextId() + "");
                biddingProjectData.setContentSettingsId(biddingContentBak.getId());
                biddingProjectData.setProjectId(biddingProject.getId());
                biddingProjectData.setValue(value);
                //获取内容设置表里的属性值，并插入project_data
                biddingProjectDataMapper.insert(biddingProjectData);
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
                    return new Result<>(false, StatusCode.ERROR, "服务器错误");
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
                    return new Result<>(false, StatusCode.ERROR, "服务器错误");
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
            biddingProjectBulid.setGoStatus("3");//0 进行中 1 未进行 2 草稿 3 待审核 4 审核通过 5 审核驳回 6跳过此阶段
            //开始走审批流程
            //如果角色是招标专员
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
            //将项目数据插入
            biddingProjectMapper.insert(biddingProject);
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
            //将立项数据插入
            biddingProjectBulidMapper.insert(biddingProjectBulid);
            BiddingProjectType biddingProjectType = biddingProjectTypeMapper.selectByPrimaryKey(typeId);
            Map resultMap = new HashMap();
            resultMap.put("projectPhase", biddingProjectType.getProjectPhaseId());
            return new Result<>(true, StatusCode.OK, "保存成功", resultMap);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
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
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
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
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
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
            return new Result<>(false, StatusCode.ERROR, "服务器错误");
        }
    }

    public Result insertDocInter(Map map, String userId, Map<String, MultipartFile> fileMap, List<List<String>> addContent) {
        try {
            //第一块，直接引用开始
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
            biddingContentBakMapper.copySetting(projectPhaseId);
            //获取内容设置表中的字段信息
            List<BiddingContentBak> settingsList = biddingContentBakMapper.selectByPhaseId(projectPhaseId, biddingProject1.getVersionNum());
            if (settingsList!=null){
                for (BiddingContentBak biddingContentBak : settingsList) {
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
            //第一块，直接引用结束

            biddingProject1.setDocInterpretationId(idWorker.nextId() + "");
            biddingProject1.setProjectPhaseNow("2");
            //更新project表
            biddingProjectMapper.updateByPrimaryKeySelective(biddingProject1);
            //判断文件夹是否存在，不存在则新建
            String docPath1 = uploadDocTerpretation + "/" + biddingProject.getId();
            File docPath = new File(docPath1);
            if (!docPath.exists() && !docPath.isDirectory()) {
                docPath.mkdirs();
            }
            BiddingDocInterpretation biddingDocInterpretation = new BiddingDocInterpretation();
            biddingDocInterpretation.setId(biddingProject1.getDocInterpretationId());
            //0 进行中 1 未进行 2 草稿 3 待审核 4 审核通过 5 审核驳回 6跳过此阶段
            biddingDocInterpretation.setGoStatus("3");
            //开始拿参数
            String type = map.get("type").toString();
            biddingDocInterpretation.setType(type);
            String range = map.get("range").toString();
            biddingDocInterpretation.setRange(range);
            //String noReason = map.get("noReason").toString();
            //biddingDocInterpretation.setNoReason(noReason);
            //String timeRangeStart = map.get("timeRangeStart").toString();
            //biddingDocInterpretation.setTimeRangeStart(timeRangeStart);
            //String timeRangeEnd = map.get("timeRangeEnd").toString();
            //biddingDocInterpretation.setTimeRangeEnd(timeRangeEnd);
            //String commonName = map.get("commonName").toString();
            //biddingDocInterpretation.setCommonName(commonName);
            //String standards = map.get("standards").toString();
            //biddingDocInterpretation.setStandards(standards);
            //String qualityLevel = map.get("qualityLevel").toString();
            //biddingDocInterpretation.setQualityLevel(qualityLevel);
            //String priceLimit = map.get("priceLimit").toString();
            //biddingDocInterpretation.setPriceLimit(priceLimit);
            //String priceLimitReference = map.get("priceLimitReference").toString();
            //biddingDocInterpretation.setPriceLimitReference(priceLimitReference);
            //String priceLimitExplain = map.get("priceLimitExplain").toString();
            //biddingDocInterpretation.setPriceLimitExplain(priceLimitExplain);
            //
            //
            //if (map.get("fileBuyRules") != null) {
            //    String fileBuyRules = map.get("fileBuyRules").toString();
            //    biddingDocInterpretation.setFileBuyRules(fileBuyRules);
            //} else {
            //    MultipartFile fileBuyRules = fileMap.get("fileBuyRules");
            //    String fileName1 = fileBuyRules.getOriginalFilename();
            //    String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
            //    File filePath = new File(docPath, fileName);
            //    try {
            //        fileBuyRules.transferTo(filePath);
            //    } catch (IOException e) {
            //        log.error(e.toString(), e);
            //        return new Result<>(false, StatusCode.ERROR, "服务器错误");
            //    }
            //    biddingDocInterpretation.setFileBuyRules(filePath.getPath());
            //}
            //
            //if (map.get("fileQuotedPrice") != null) {
            //    String fileQuotedPrice = map.get("fileQuotedPrice").toString();
            //    biddingDocInterpretation.setFileQuotedPrice(fileQuotedPrice);
            //} else {
            //    MultipartFile fileQuotedPrice = fileMap.get("fileQuotedPrice");
            //    String fileName1 = fileQuotedPrice.getOriginalFilename();
            //    String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
            //    File filePath = new File(docPath, fileName);
            //    try {
            //        fileQuotedPrice.transferTo(filePath);
            //    } catch (IOException e) {
            //        log.error(e.toString(), e);
            //        return new Result<>(false, StatusCode.ERROR, "服务器错误");
            //    }
            //    biddingDocInterpretation.setFileQuotedPrice(filePath.getPath());
            //}
            //
            //if (map.get("fileSolicitingOpinions") != null) {
            //    String fileSolicitingOpinions = map.get("fileSolicitingOpinions").toString();
            //    biddingDocInterpretation.setFileSolicitingOpinions(fileSolicitingOpinions);
            //} else {
            //    MultipartFile fileSolicitingOpinions = fileMap.get("fileSolicitingOpinions");
            //    String fileName1 = fileSolicitingOpinions.getOriginalFilename();
            //    String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
            //    File filePath = new File(docPath, fileName);
            //    try {
            //        fileSolicitingOpinions.transferTo(filePath);
            //    } catch (IOException e) {
            //        log.error(e.toString(), e);
            //        return new Result<>(false, StatusCode.ERROR, "服务器错误");
            //    }
            //    biddingDocInterpretation.setFileSolicitingOpinions(filePath.getPath());
            //}
            //String industryInfluence = map.get("industryInfluence").toString();
            //biddingDocInterpretation.setIndustryInfluence(industryInfluence);
            //String solicitingOpinions = map.get("solicitingOpinions").toString();
            //biddingDocInterpretation.setSolicitingOpinions(solicitingOpinions);
            //String fileSolicitingOpinions = map.get("fileSolicitingOpinions").toString();
            //biddingDocInterpretation.setFileSolicitingOpinions(fileSolicitingOpinions);
            //String suggestion = map.get("suggestion").toString();
            //biddingDocInterpretation.setSuggestion(suggestion);

            //第二块，可以直接引用开始（里面有4处需要更改）
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
            //第二块，可以直接引用结束
            //第三块可以直接引用开始(有一处需要更改)
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
            //第三块可以直接引用结束

            //将文件解读的数据插入
            biddingDocInterpretationMapper.insert(biddingDocInterpretation);
            return new Result<>(true, StatusCode.OK, "保存成功");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new Result<>(true, StatusCode.ERROR, "服务器错误");
        }
    }
}