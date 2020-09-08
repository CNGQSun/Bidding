package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingProvinceService;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/** 
 * <br/>
 * Created by Grant on 2020/07/27
 */
@CrossOrigin
@RestController
@RequestMapping("/biddingProvince")
@Api(tags = "省份相关接口")
public class BiddingProvinceController {
	private static Logger log = LoggerFactory.getLogger(BiddingProvinceController.class);

	@Resource
	private BiddingProvinceService biddingProvinceService;


	/**
	 * 查询全部省份信息
	 */
	@ApiOperation(value="查询全部省份信息" )

	@GetMapping
	public Result findAll(){
		Result result= biddingProvinceService.selectAllPro();
		return result;
	}
}