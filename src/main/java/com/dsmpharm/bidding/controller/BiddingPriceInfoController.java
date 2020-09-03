package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingPriceInfoService;
import com.dsmpharm.bidding.service.BiddingProjectService;
import com.dsmpharm.bidding.utils.JwtUtil;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <br/>
 * Created by Grant on 2020/08/21
 */
@CrossOrigin
@RestController
@RequestMapping("/biddingPriceInfo")
@Api(tags = "资料库相关接口")
public class BiddingPriceInfoController {
    private static Logger log = LoggerFactory.getLogger(BiddingPriceInfoController.class);

    @Resource
    private BiddingPriceInfoService biddingPriceInfoService;
    @Resource
    private BiddingProjectService biddingProjectService;

    @Resource
    private JwtUtil jwtUtil;

    /**
     * 资料库中项目展示
     *
     * @param request
     * @param map
     * @return
     */
    @ApiOperation(value = "资料库中项目展示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示条数", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "proId", value = "省份ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "cityId", value = "城市ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "项目名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "proLabel", value = "级别 （国标、省标、地市标）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "typeId", value = "项目类型ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "查询开始时间", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "查询终止时间", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping(value = "/database/list")
    public Result findList(HttpServletRequest request, @RequestParam Map map) {
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingPriceInfoService.databaseList(map, userId);
        return result;
    }

    /**
     * 资料库中申诉函展示
     *
     * @param request
     * @param map
     * @return
     */
    @ApiOperation(value = "资料库中申诉函展示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示条数", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "qualityLevel", value = "产品质量层次", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "proId", value = "省份ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "productId", value = "产品Id", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping(value = "/database/appeal")
    public Result findAppealList(HttpServletRequest request, @RequestParam Map map) {
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = biddingPriceInfoService.databaseAppealList(map, userId);
        return result;
    }

    /**
     * 首页地图展示
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "首页地图展示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productEnName", value = "产品英文名（不能为空，需要默认值）", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping(value = "/index")
    public Result findIndexInfo(@RequestParam Map map) {
        Result result = biddingPriceInfoService.findIndexInfo(map);
        return result;
    }

    /**
     * 导出项目
     *
     * @param projectId
     * @return
     */
    @ApiOperation(value = "导出项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, paramType = "query", dataType = "String"),
    })
    @GetMapping(value = "/export")
    public Result exportProject(HttpServletResponse response, String projectId) {
        Result result = biddingPriceInfoService.exportProject(projectId, response);
        return result;
    }

	/**
	 * 上传中标文件
	 *
	 * @param file
	 * @return
	 */
	@ApiOperation(value = "上传中标文件")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "file", value = "上传的文件", required = true, paramType = "query", dataType = "String"),
	})
	@GetMapping(value = "/upload")
	public Result uploadFile(HttpServletResponse response,HttpServletRequest request, @RequestParam MultipartFile file) {
		Result result = biddingPriceInfoService.uploadFile(response,request,file);
		return result;
	}

    /**
     * 导出中标文件
     * @return
     */
    @ApiOperation(value = "上传中标文件")
    @GetMapping(value = "/exportBid")
    public Result downloadFile(HttpServletResponse response) {
        Result result = biddingPriceInfoService.downloadFile(response);
        return result;
    }
}