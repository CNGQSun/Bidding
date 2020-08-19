package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingProjectService;
import com.dsmpharm.bidding.utils.JwtUtil;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <br/>
 * Created by Grant on 2020/08/08
 */
@CrossOrigin
@RestController
//@Controller
@RequestMapping("/biddingProject")
@Api(tags = "项目管理相关接口")
public class BiddingProjectController {

    @Resource
    private BiddingProjectService biddingProjectService;

    @Resource
    private JwtUtil jwtUtil;

    /**
     * 点击新增 加载内容设置
     *
     * @param projectPhaseId
     * @return
     */
    @ApiOperation(value = "点击新增，调用此接口，加载内容设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
    })
    @GetMapping(value = "/content")
    public Result findById(@RequestParam String projectPhaseId) {
        Result result = biddingProjectService.findContent(projectPhaseId);
        return result;
    }

    /**
     * 查询所有登录用户创建的项目
     *
     * @param request
     * @param map
     * @return
     */
    @ApiOperation(value = "分页、条件查询全部登录用户创建的项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示条数", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "项目名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "项目状态", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping(value = "/list")
    public Result findSearch(HttpServletRequest request, @RequestParam Map map) {
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingProjectService.list(map, userId);
        return result;
    }

    /**
     * 查询所有登录用户待办的项目
     *
     * @param request
     * @param map
     * @return
     */
    @ApiOperation(value = "分页、条件查询全部登录用户待办的项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示条数", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "项目名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "项目状态", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping(value = "/list/deal")
    public Result findSearchDeal(HttpServletRequest request, @RequestParam Map map) {
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingProjectService.listDeal(map, userId);
        return result;
    }

    /**
     * 立项
     *
     * @param request
     * @param map
     * @param addContent
     * @return
     */
    @ApiOperation(value = "提交立项内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSubmit", value = "提交或保存，0为提交，1为保存", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "addContent", value = "单独的内容设置，格式{{name,contentTypeId,isNull,value},{}}", required = true, paramType = "query", dataType = "body"),
            @ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "docPublicTime", value = "文件发布时间", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "typeId", value = "项目类型ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "项目名称", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "source", value = "项目来源", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "provinceId", value = "省份ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "cityId", value = "城市ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "productId", value = "产品ID，多个ID拼接：1,2,3", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileFormal", value = "正式稿文件(文件或字符串)", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileAsk", value = "征求稿文件(文件或字符串)", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "docInterTime", value = "文件解读时间", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "submitTime", value = "递交/填报资料时间", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "publicTime", value = "公示期", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "appealTime", value = "申诉期", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "noticeTime", value = "公告期", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "suggestion", value = "意见", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "Authorization", value = "请求头中存储token", required = true, paramType = "query", dataType = "String"),
    })

    @PostMapping("/build")
    public Result insert(HttpServletRequest request, @RequestParam Map map, @RequestParam(value = "addContent") List<List<String>> addContent) {

        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        Map<String, MultipartFile> fileMap = params.getFileMap();
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingProjectService.insert(map, userId, fileMap,addContent);
        return result;
    }

    /**
     * 修改立项
     *
     * @param request
     * @param map
     * @param addContent
     * @return
     */
    @ApiOperation(value = "修改立项内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSubmit", value = "提交或保存，0为提交，1为保存", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "addContent", value = "单独的内容设置，格式{{name,contentTypeId,isNull,value},{}}", required = true, paramType = "query", dataType = "body"),
            @ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "docPublicTime", value = "文件发布时间", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "typeId", value = "项目类型ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "项目名称", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "source", value = "项目来源", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "provinceId", value = "省份ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "cityId", value = "城市ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "productId", value = "产品ID，多个ID拼接：1,2,3", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileFormal", value = "正式稿文件(文件或字符串)", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileAsk", value = "征求稿文件(文件或字符串)", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "docInterTime", value = "文件解读时间", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "submitTime", value = "递交/填报资料时间", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "publicTime", value = "公示期", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "appealTime", value = "申诉期", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "noticeTime", value = "公告期", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "suggestion", value = "意见", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "Authorization", value = "请求头中存储token", required = true, paramType = "query", dataType = "String"),
    })

    @PostMapping("/build/update")
    public Result updateBuild(HttpServletRequest request, @RequestParam Map map, @RequestParam(value = "addContent") List<List<String>> addContent) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        Map<String, MultipartFile> fileMap = params.getFileMap();
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingProjectService.updateBuild(map, userId, fileMap,addContent);
        return result;
    }

    /**
     * 文件解读
     * @param request
     * @param map
     * @param addContent
     * @return
     */
    @ApiOperation(value = "提交文件解读内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSubmit", value = "提交或保存，0为提交，1为保存", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isSkip", value = "是否跳过竞品收集，0为跳过，1为不跳过", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "addContent", value = "单独的内容设置，格式{{name,contentTypeId,isNull,value},{}}", required = true, paramType = "query", dataType = "body"),
            @ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "Authorization", value = "请求头中存储token", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "参加招标的品种", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "range", value = "实施范围", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "noReason", value = "不参加本次招标的原因", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "timeRangeStart", value = "执行时间范围起始", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "timeRangeEnd", value = "执行时间范围终止", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "commonName", value = "通用名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "standards", value = "规格", required = true, paramType = "query", dataType = "String"),
            //@ApiImplicitParam(name = "qualityLevel", value = "质量层次", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "priceLimit", value = "限价制定", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "priceLimitReference", value = "限价的参考值", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "priceLimitExplain", value = "限价的说明", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileBuyRules", value = "采购规则（文件或输入）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileQuotedPrice", value = "报价（文件或输入）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "industryInfluence", value = "行业影响", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "solicitingOpinions", value = "征求稿意见", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileSolicitingOpinions", value = "征求稿意见文件（文件或输入）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "suggestion", value = "意见填写", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping("/docInter")
    public Result insertDocInter(HttpServletRequest request, @RequestParam Map map, @RequestParam(value = "addContent") List<List<String>> addContent) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        Map<String, MultipartFile> fileMap = params.getFileMap();
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingProjectService.insertDocInter(map, userId, fileMap, addContent);
        return result;
    }

    /**
     * 修改文件解读
     * @param request
     * @param map
     * @param addContent
     * @return
     */
    @ApiOperation(value = "修改文件解读内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSubmit", value = "提交或保存，0为提交，1为保存", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isSkip", value = "是否跳过竞品收集，0为跳过，1为不跳过", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "addContent", value = "单独的内容设置，格式{{name,contentTypeId,isNull,value},{}}", required = true, paramType = "query", dataType = "body"),
            @ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "Authorization", value = "请求头中存储token", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "参加招标的品种", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "range", value = "实施范围", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "noReason", value = "不参加本次招标的原因", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "timeRangeStart", value = "执行时间范围起始", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "timeRangeEnd", value = "执行时间范围终止", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "commonName", value = "通用名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "standards", value = "规格", required = true, paramType = "query", dataType = "String"),
            //@ApiImplicitParam(name = "qualityLevel", value = "质量层次", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "priceLimit", value = "限价制定", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "priceLimitReference", value = "限价的参考值", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "priceLimitExplain", value = "限价的说明", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileBuyRules", value = "采购规则（文件或输入）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileQuotedPrice", value = "报价（文件或输入）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "industryInfluence", value = "行业影响", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "solicitingOpinions", value = "征求稿意见", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileSolicitingOpinions", value = "征求稿意见文件（文件或输入）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "suggestion", value = "意见填写", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping("/docInter/update")
    public Result updateDocInter(HttpServletRequest request, @RequestParam Map map, @RequestParam(value = "addContent") List<List<String>> addContent) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        Map<String, MultipartFile> fileMap = params.getFileMap();
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingProjectService.updateDocInter(map, userId, fileMap, addContent);
        return result;
    }

    /**
     * 竞品收集
     * @param request
     * @param map
     * @param addContent
     * @return
     */
    @ApiOperation(value = "提交竞品收集内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSubmit", value = "提交或保存，0为提交，1为保存", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "addContent", value = "单独的内容设置，格式{{name,contentTypeId,isNull,value},{}}", required = true, paramType = "query", dataType = "body"),
            @ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "Authorization", value = "请求头中存储token", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileText", value = "文件或内容（多个文件）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "suggestion", value = "意见填写", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping("/proCollection")
    public Result insertProCollection(HttpServletRequest request, @RequestParam Map map, @RequestParam(value = "addContent") List<List<String>> addContent) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> fileText = params.getFiles("fileText");
        Map<String, MultipartFile> fileMap = params.getFileMap();
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingProjectService.insertProCollection(map, userId, fileText,addContent,fileMap);
        return result;
    }

    /**
     * 修改竞品收集
     * @param request
     * @param map
     * @param addContent
     * @return
     */
    @ApiOperation(value = "修改竞品收集内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSubmit", value = "提交或保存，0为提交，1为保存", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "addContent", value = "单独的内容设置，格式{{name,contentTypeId,isNull,value},{}}", required = true, paramType = "query", dataType = "body"),
            @ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "Authorization", value = "请求头中存储token", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileText", value = "文件或内容（多个文件）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "suggestion", value = "意见填写", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping("/proCollection/update")
    public Result updateProCollection(HttpServletRequest request, @RequestParam Map map, @RequestParam(value = "addContent") List<List<String>> addContent) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> fileText = params.getFiles("fileText");
        Map<String, MultipartFile> fileMap = params.getFileMap();
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingProjectService.updateProCollection(map, userId, fileText,addContent,fileMap);
        return result;
    }

    /**
     * 策略分析
     * @param request
     * @param map
     * @param addContent
     * @return
     */
    @ApiOperation(value = "提交策略分析内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSubmit", value = "提交或保存，0为提交，1为保存", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "addContent", value = "单独的内容设置，格式{{name,contentTypeId,isNull,value},{}}", required = true, paramType = "query", dataType = "body"),
            @ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "Authorization", value = "请求头中存储token", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileProductInfo", value = "产品信息（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileQualityLevel", value = "产品质量层次（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileProductGro", value = "产品分组（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileProduct", value = "产品（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "filePriceLimit", value = "其他产品限价（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileCompeInfo", value = "竞品信息（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileQualityLevels", value = "竞品质量层次（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileCompeGro", value = "竞品分组（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileQuotationEstimate", value = "报价预估（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileQuotationOther", value = "其他报价预估（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileNationalPrice", value = "全国联动价格（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "suggestion", value = "意见填写", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping("/strategyAnalysis")
    public Result insertStrategyAnalysis(HttpServletRequest request, @RequestParam Map map, @RequestParam(value = "addContent") List<List<String>> addContent) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        Map<String, MultipartFile> fileMap = params.getFileMap();
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingProjectService.insertStrategyAnalysis(map, userId, fileMap, addContent);
        return result;
    }

    /**
     * 修改策略分析
     * @param request
     * @param map
     * @param addContent
     * @return
     */
    @ApiOperation(value = "提交策略分析内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSubmit", value = "提交或保存，0为提交，1为保存", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "addContent", value = "单独的内容设置，格式{{name,contentTypeId,isNull,value},{}}", required = true, paramType = "query", dataType = "body"),
            @ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "Authorization", value = "请求头中存储token", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileProductInfo", value = "产品信息（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileQualityLevel", value = "产品质量层次（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileProductGro", value = "产品分组（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileProduct", value = "产品（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "filePriceLimit", value = "其他产品限价（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileCompeInfo", value = "竞品信息（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileQualityLevels", value = "竞品质量层次（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileCompeGro", value = "竞品分组（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileQuotationEstimate", value = "报价预估（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileQuotationOther", value = "其他报价预估（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileNationalPrice", value = "全国联动价格（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "suggestion", value = "意见填写", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping("/strategyAnalysis/update")
    public Result updateStrategyAnalysis(HttpServletRequest request, @RequestParam Map map, @RequestParam(value = "addContent") List<List<String>> addContent) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        Map<String, MultipartFile> fileMap = params.getFileMap();
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingProjectService.updateStrategyAnalysis(map, userId, fileMap, addContent);
        return result;
    }

    /**
     * 信息填报
     * @param request
     * @param map
     * @param addContent
     * @return
     */
    @ApiOperation(value = "提交信息填报内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSubmit", value = "提交或保存，0为提交，1为保存", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "addContent", value = "单独的内容设置，格式{{name,contentTypeId,isNull,value},{}}", required = true, paramType = "query", dataType = "body"),
            @ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "Authorization", value = "请求头中存储token", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileOnlineFilling", value = "线上填报（文件上传）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileApplicationSeal", value = "盖章申请（文件上传）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileInfoList", value = "资料清单（文件上传）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileNameOa", value = "OA文件名（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileDetailsSpecial", value = "特殊文件明细（文件上传）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "SubmissionTime", value = "递交时间记录", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "filePriceApp", value = "价格申请（文件上传）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "filePriceLetter", value = "价格申诉函（文件上传）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileInfoChange", value = "信息变更申诉函（文件上传）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileOther", value = "其他文件（文件上传）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "suggestion", value = "意见填写", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping("/infoFilling")
    public Result insertInfoFilling(HttpServletRequest request, @RequestParam Map map, @RequestParam(value = "addContent") List<List<String>> addContent) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        Map<String, MultipartFile> fileMap = params.getFileMap();
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingProjectService.insertInfoFilling(map, userId, fileMap, addContent);
        return result;
    }

    /**
     * 修改信息填报
     * @param request
     * @param map
     * @param addContent
     * @return
     */
    @ApiOperation(value = "提交信息填报内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSubmit", value = "提交或保存，0为提交，1为保存", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "addContent", value = "单独的内容设置，格式{{name,contentTypeId,isNull,value},{}}", required = true, paramType = "query", dataType = "body"),
            @ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "Authorization", value = "请求头中存储token", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileOnlineFilling", value = "线上填报（文件上传）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileApplicationSeal", value = "盖章申请（文件上传）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileInfoList", value = "资料清单（文件上传）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileNameOa", value = "OA文件名（文件或内容）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileDetailsSpecial", value = "特殊文件明细（文件上传）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "SubmissionTime", value = "递交时间记录", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "filePriceApp", value = "价格申请（文件上传）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "filePriceLetter", value = "价格申诉函（文件上传）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileInfoChange", value = "信息变更申诉函（文件上传）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileOther", value = "其他文件（文件上传）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "suggestion", value = "意见填写", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping("/infoFilling/update")
    public Result updateInfoFilling(HttpServletRequest request, @RequestParam Map map, @RequestParam(value = "addContent") List<List<String>> addContent) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        Map<String, MultipartFile> fileMap = params.getFileMap();
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingProjectService.updateInfoFilling(map, userId, fileMap, addContent);
        return result;
    }

    /**
     * 官方公告
     * @param request
     * @param map
     * @param addContent
     * @return
     */
    @ApiOperation(value = "提交官方公告内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSubmit", value = "提交或保存，0为提交，1为保存", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "addContent", value = "单独的内容设置，格式{{name,contentTypeId,isNull,value},{}}", required = true, paramType = "query", dataType = "body"),
            @ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "Authorization", value = "请求头中存储token", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "file_announce", value = "公告文件(上传文件)", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "distributorRequire", value = "配送商要求（上传文件）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "suggestion", value = "意见填写", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping("/officialNotice")
    public Result insertofficialNotice(HttpServletRequest request, @RequestParam Map map, @RequestParam(value = "addContent") List<List<String>> addContent) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        Map<String, MultipartFile> fileMap = params.getFileMap();
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingProjectService.officialNotice(map, userId, fileMap, addContent);
        return result;
    }

    /**
     * 修改官方公告
     * @param request
     * @param map
     * @param addContent
     * @return
     */
    @ApiOperation(value = "提交官方公告内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSubmit", value = "提交或保存，0为提交，1为保存", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "addContent", value = "单独的内容设置，格式{{name,contentTypeId,isNull,value},{}}", required = true, paramType = "query", dataType = "body"),
            @ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "Authorization", value = "请求头中存储token", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "file_announce", value = "公告文件(上传文件)", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "distributorRequire", value = "配送商要求（上传文件）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "suggestion", value = "意见填写", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping("/officialNotice/update")
    public Result updateOfficialNotice(HttpServletRequest request, @RequestParam Map map, @RequestParam(value = "addContent") List<List<String>> addContent) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        Map<String, MultipartFile> fileMap = params.getFileMap();
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingProjectService.updateOfficialNotice(map, userId, fileMap, addContent);
        return result;
    }

    /**
     * 项目总结
     * @param request
     * @param map
     * @param addContent
     * @return
     */
    @ApiOperation(value = "提交项目总结内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSubmit", value = "提交或保存，0为提交，1为保存", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "addContent", value = "单独的内容设置，格式{{name,contentTypeId,isNull,value},{}}", required = true, paramType = "query", dataType = "body"),
            @ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "Authorization", value = "请求头中存储token", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "参与竞标产品状态 成功为0，失败为1", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "productId", value = "产品ID，多个用,隔开", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "lastRoundDecline", value = "较上一轮降幅", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "competitiveProduc", value = "竞品情况", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "failureReasons", value = "失败原因", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "nextStep", value = "下一步工作", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "estimatedTime", value = "预估下一轮启动时间", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "启动时间", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isClinical", value = "临床 是0 否1", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isRecord", value = "备案 是0 否1", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "suggestion", value = "意见填写", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping("/projectSummary")
    public Result insertProjectSummary(HttpServletRequest request, @RequestParam Map map, @RequestParam(value = "addContent") List<List<String>> addContent) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        Map<String, MultipartFile> fileMap = params.getFileMap();
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingProjectService.insertProjectSummary(map, userId, fileMap, addContent);
        return result;
    }

    /**
     * 修改项目总结
     * @param request
     * @param map
     * @param addContent
     * @return
     */
    @ApiOperation(value = "提交项目总结内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSubmit", value = "提交或保存，0为提交，1为保存", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "addContent", value = "单独的内容设置，格式{{name,contentTypeId,isNull,value},{}}", required = true, paramType = "query", dataType = "body"),
            @ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "Authorization", value = "请求头中存储token", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "参与竞标产品状态 成功为0，失败为1", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "productId", value = "产品ID，多个用,隔开", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "lastRoundDecline", value = "较上一轮降幅", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "competitiveProduc", value = "竞品情况", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "failureReasons", value = "失败原因", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "nextStep", value = "下一步工作", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "estimatedTime", value = "预估下一轮启动时间", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "启动时间", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isClinical", value = "临床 是0 否1", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isRecord", value = "备案 是0 否1", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "suggestion", value = "意见填写", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping("/projectSummary/update")
    public Result updateProjectSummary(HttpServletRequest request, @RequestParam Map map, @RequestParam(value = "addContent") List<List<String>> addContent) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        Map<String, MultipartFile> fileMap = params.getFileMap();
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingProjectService.updateProjectSummary(map, userId, fileMap, addContent);
        return result;
    }


    /**
     * 查看详情
     * @param request
     * @param map
     * @return
     */
    @ApiOperation(value = "点击可查看具体阶段详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "Authorization", value = "请求头中存储token", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, paramType = "query", dataType = "String"),
    })
    @GetMapping("/info")
    public Result selectInfo(HttpServletRequest request,@RequestParam Map map) {
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingProjectService.selectInfo(map, userId);
        return result;
    }




}