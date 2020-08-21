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
			@ApiImplicitParam(name = "name", value = "项目名", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "status", value = "项目状态", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping(value = "/database/list")
	public Result findList(HttpServletRequest request, @RequestParam Map map) {
		String authorization = request.getHeader("Authorization");
		String userId = jwtUtil.parseJWT(authorization).getId();
		Result result = biddingPriceInfoService.databaseList(map, userId);
		return result;
	}

}