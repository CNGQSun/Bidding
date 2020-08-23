package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingPriceInfoService;
import com.dsmpharm.bidding.service.BiddingProjectService;
import com.dsmpharm.bidding.utils.JwtUtil;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

}