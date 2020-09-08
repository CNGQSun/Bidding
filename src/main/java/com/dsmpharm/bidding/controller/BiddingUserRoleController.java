package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingUserRoleService;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
@CrossOrigin
@RestController
@RequestMapping("/biddingUserRole")
@Api(tags = "用户角色相关接口")
public class BiddingUserRoleController {
	private static Logger log = LoggerFactory.getLogger(BiddingUserRoleController.class);

	@Resource
	private BiddingUserRoleService biddingUserRoleService;

	/**
	 * 根据角色去查询用户的详细信息
	 * @param roleId
	 * @return
	 */
	@ApiOperation(value="根据角色去查询用户的详细信息")
	@ApiImplicitParam(name = "roleId", value = "角色ID(1为商务统括，2为大区经理，3为商务经理)", required = true, paramType = "query", dataType = "String")
	@GetMapping(value = "/role")
	public Result findByRole(@RequestParam String roleId){
		Result result =biddingUserRoleService.findByRole(roleId);
		return result;
	}
	/**
	 * 查询所有商务经理的详细信息
	 * @return
	 */
	@ApiOperation(value="查询所有商务经理的详细信息（用户省份中姓名下拉框）")
	@GetMapping(value = "/role/3")
	public Result find3ByRole(){
		String role="3";
		Result result =biddingUserRoleService.findByRole(role);
		return result;
	}
}