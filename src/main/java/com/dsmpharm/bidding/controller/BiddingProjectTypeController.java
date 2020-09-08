package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingProjectTypeService;
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
 * Created by Grant on 2020/08/03
 */
@CrossOrigin
@RestController
@RequestMapping("/biddingProjectType")
@Api(tags = "项目类型相关接口")
public class BiddingProjectTypeController {
	private static Logger log = LoggerFactory.getLogger(BiddingProjectTypeController.class);

	@Resource
	private BiddingProjectTypeService biddingProjectTypeService;

	/**
	 * 获取所有未删除的项目类型
	 * @return
	 */
	@ApiOperation(value="立项中“项目类型”接口" )
	@GetMapping
	public Result findAll(){
		Result result = biddingProjectTypeService.selectNoDel();
		return result;
	}
}