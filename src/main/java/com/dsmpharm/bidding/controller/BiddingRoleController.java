package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingRoleService;
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
 * Created by Grant on 2020/07/23
 */
@CrossOrigin
@RestController
@RequestMapping("/biddingRole")
@Api(tags = "角色相关接口")
public class BiddingRoleController {
	private static Logger log = LoggerFactory.getLogger(BiddingRoleController.class);

	@Resource
	private BiddingRoleService biddingRoleService;

	/**
	* 查询所有角色名称
	*/
	@ApiOperation(value="查询所有角色名称" )
	@GetMapping
	public Result findAll(){
		Result result = biddingRoleService.selectAll();
		return result;
	}
}