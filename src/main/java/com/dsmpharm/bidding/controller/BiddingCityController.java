package com.dsmpharm.bidding.controller;


import com.dsmpharm.bidding.service.BiddingCityService;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/** 
 * <br/>
 * Created by Grant on 2020/07/27
 */
@CrossOrigin
@RestController
@RequestMapping("/biddingCity")
@Api(tags = "城市相关接口")
public class BiddingCityController {
	private static Logger log = LoggerFactory.getLogger(BiddingCityController.class);

	@Resource
	private BiddingCityService biddingCityService;
	/**
	* 根据省份ID查询城市信息
	*/
	@ApiOperation(value="根据省份ID查询对应城市" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "proId", value = "省份ID", required = true, paramType = "query", dataType = "String"),
	})
	@GetMapping("/city")
	public Result findById(@RequestParam String proId){
		Result result= biddingCityService.findByProId(proId);
		return result;
	}
}