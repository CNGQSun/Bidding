package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.controller.BiddingProductController;
import com.dsmpharm.bidding.mapper.*;
import com.dsmpharm.bidding.pojo.*;
import com.dsmpharm.bidding.utils.DateUtils;
import com.dsmpharm.bidding.utils.IdWorker;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.apache.ibatis.session.RowBounds;
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
    private IdWorker idWorker;

    public Result insert(Map<String, String> map, String userId, Map<String, MultipartFile> fileMap,List<List<String>> addContent) {
        //根据userId查询用户角色
       BiddingUserRole biddingUserRole= biddingUserRoleMapper.selectByUserId(userId);
        BiddingProject biddingProject = new BiddingProject();
        BiddingProjectBulid biddingProjectBulid = new BiddingProjectBulid();
        biddingProject.setId(idWorker.nextId() + "");
        biddingProject.setStatus("0");
        biddingProject.setDelflag("0");
        String projectPhaseId = map.get("projectPhaseId").toString();
        //List<BiddingContentSettings> settingsList = biddingContentSettingsMapper.selectByPhaseId(projectPhaseId);
            String versionNum=idWorker.nextId()+"";
        biddingProject.setVersionNum(versionNum);
        //设置所有内容设置版本号
        int i=biddingContentSettingsMapper.updateAllNum(versionNum,projectPhaseId);
        //将内容设置数据备份到内容设置备份表
        biddingContentBakMapper.copySetting(projectPhaseId);
        //获取内容设置表中的字段信息
        List<BiddingContentBak> settingsList = biddingContentBakMapper.selectByPhaseId(projectPhaseId,versionNum);
        for (BiddingContentBak biddingContentBak : settingsList) {
            String value = map.get(biddingContentBak.getEnName());
            BiddingProjectData biddingProjectData=new BiddingProjectData();
            biddingProjectData.setId(idWorker.nextId()+"");
            biddingProjectData.setContentSettingsId(biddingContentBak.getId());
            biddingProjectData.setProjectId(biddingProject.getId());
            biddingProjectData.setValue(value);
            biddingProjectDataMapper.insert(biddingProjectData);
        }
        biddingProjectBulid.setId(idWorker.nextId() + "");
        biddingProject.setProjectBulidId(biddingProjectBulid.getId());
        biddingProject.setProjectPhaseNow("1");
        //判断文件夹是否存在，不存在则新建
        String docPath1 = uploadBuild + "/" + biddingProject.getId();
        File  docPath= new File(docPath1);
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
        biddingProjectBulid.setProductId(provinceId);
        String cityId = map.get("cityId").toString();
        biddingProjectBulid.setCityId(cityId);
        //产品ID有多个，需重点关注
        String productId = map.get("productId").toString();
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
        String proLabel = map.get("proLabel").toString();
        biddingProjectBulid.setProLabel(proLabel);
        String suggestion = map.get("suggestion").toString();
        biddingProjectBulid.setSuggestion(suggestion);
        String goStatus = map.get("goStatus").toString();
        biddingProjectBulid.setGoStatus(goStatus);
        //开始走审批流程
        //如果角色是招标专员
        AppFlowApply appFlowApply=null;
        AppFlowApproval appFlowApproval=null;
        if (biddingUserRole.getRoleId().equals("6")){
            appFlowApply=new AppFlowApply();
            appFlowApply.setId(idWorker.nextId()+"");
            appFlowApply.setAddDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            appFlowApply.setDelflag("0");
            appFlowApply.setPhaseId(biddingProject.getProjectBulidId());
            appFlowApply.setCurrentNode("0");
            appFlowApply.setFlowId("0");
            appFlowApply.setStatus("3");
            appFlowApply.setProjectId(biddingProject.getId());
            appFlowApply.setUserId(userId);
            appFlowApplyMapper.insert(appFlowApply);
            //查找下一个审批用户
            BiddingUser biddingUser=appFlowNodeMapper.selectAppUser(appFlowApply.getFlowId(),appFlowApply.getCurrentNode());
            appFlowApproval=new AppFlowApproval();
            appFlowApproval.setId(idWorker.nextId()+"");
            appFlowApproval.setProjectPhaseId(biddingProject.getProjectBulidId());
            appFlowApproval.setDelflag("0");
            String nextNode=(Integer.valueOf(appFlowApply.getCurrentNode())+1)+"";
            appFlowApproval.setFlowNodeId(nextNode);
            appFlowApproval.setApplyId(appFlowApply.getId());
            appFlowApprovalMapper.insert(appFlowApproval);
            //如果角色是商务经理
        }else if (biddingUserRole.getRoleId().equals("3")){
            appFlowApply=new AppFlowApply();
            appFlowApply.setId(idWorker.nextId()+"");
            appFlowApply.setAddDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            appFlowApply.setDelflag("0");
            appFlowApply.setPhaseId(biddingProject.getProjectBulidId());
            appFlowApply.setCurrentNode("3");
            appFlowApply.setFlowId("1");
            appFlowApply.setStatus("3");
            appFlowApply.setProjectId(biddingProject.getId());
            appFlowApply.setUserId(userId);
            //查找下一个审批用户
            BiddingUser biddingUser=appFlowNodeMapper.selectAppUserSw(appFlowApply.getFlowId(),appFlowApply.getCurrentNode(),userId);
            appFlowApproval=new AppFlowApproval();
            appFlowApproval.setId(idWorker.nextId()+"");
            appFlowApproval.setProjectPhaseId(biddingProject.getProjectBulidId());
            appFlowApproval.setDelflag("0");
            String nextNode=(Integer.valueOf(appFlowApply.getCurrentNode())+1)+"";
            appFlowApproval.setFlowNodeId(nextNode);
            appFlowApproval.setApplyId(appFlowApply.getId());
            appFlowApprovalMapper.insert(appFlowApproval);
        }
        biddingProjectMapper.insert(biddingProject);
        for (List<String> list : addContent) {
            BiddingSettingsExtra biddingSettingsExtra=new BiddingSettingsExtra();
            String name1 = list.get(0);
            String contentTypeId = list.get(1);
            String isNull = list.get(2);
            String value = list.get(3);
            biddingSettingsExtra.setId(idWorker.nextId()+"");
            biddingSettingsExtra.setDelflag("0");
            biddingSettingsExtra.setProjectId(biddingProject.getId());
            biddingSettingsExtra.setContentTypeId(contentTypeId);
            biddingSettingsExtra.setIsNull(isNull);
            biddingSettingsExtra.setName(name1);
            biddingSettingsExtra.setValue(value);
            biddingSettingsExtra.setProjectPhaseId(biddingProject.getProjectBulidId());
            biddingSettingsExtraMapper.insert(biddingSettingsExtra);
        }
        BiddingProjectType biddingProjectType = biddingProjectTypeMapper.selectByPrimaryKey(typeId);
        Map resultMap=new HashMap();
        resultMap.put("projectPhase",biddingProjectType.getProjectPhaseId());
        return new Result<>(true, StatusCode.OK, "保存成功",resultMap);
    }

    public List<BiddingProject> selectAll() {
        return biddingProjectMapper.selectAll();
    }

    public BiddingProject findById(String id) {
        return biddingProjectMapper.selectByPrimaryKey(id);
    }

    public void updateById(BiddingProject biddingProject) {
        biddingProjectMapper.updateByPrimaryKey(biddingProject);
    }

    public void delete(String id) {
        biddingProjectMapper.deleteByPrimaryKey(id);
    }

    public List<BiddingProject> list(BiddingProject biddingProject) {
        return biddingProjectMapper.select(biddingProject);
    }

    public List<BiddingProject> list(BiddingProject biddingProject, int currentPage, int pageSize) {
        biddingProjectMapper.selectCount(biddingProject);
        // 构造分页数据
        RowBounds bounds = new RowBounds(getStartRecord(currentPage, pageSize), pageSize);
        return biddingProjectMapper.selectByRowBounds(biddingProject, bounds);
    }

    public Integer selectCount(BiddingProject biddingProject) {
        return biddingProjectMapper.selectCount(biddingProject);
    }

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }

    /**
     * 点击新增加载内容设置                                                                                                                                                                                                                                                                                                                                                                                                         o
     * @param projectPhaseId
     * @return
     */
    //public Result findContent(String projectPhaseId) {
    //    //BiddingProject biddingProject=new BiddingProject();
    //    //biddingProject.setId(idWorker.nextId()+"");
    //    String versionNum=idWorker.nextId()+"";
    //    //biddingProject.setVersionNum(versionNum);
    //    //biddingProjectMapper.insert(biddingProject);
    //    //设置所有内容设置版本号
    //    int i=biddingContentSettingsMapper.updateAllNum(versionNum);
    //    //将内容设置数据备份到内容设置备份表
    //    biddingContentBakMapper.copySetting();
    //    //将版本号插入到project表中
    //    //int k=biddingContentSettingsMapper.updateAllNumNull(versionNum);
    //    List<BiddingContentBak> list=biddingContentBakMapper.selctByPhaseId(projectPhaseId,versionNum);
    //    return new Result<>(true, StatusCode.OK, "查询成功",list);
    //}
    public Result findContent(String projectPhaseId) {
        List <BiddingContentSettings> settingsList= biddingContentSettingsMapper.selectByPhaseId(projectPhaseId);
        return new Result<>(true, StatusCode.OK, "查询成功",settingsList);
    }
}