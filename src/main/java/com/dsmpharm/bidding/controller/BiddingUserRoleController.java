package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingUserRoleService;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
@RestController
@RequestMapping("/biddingUserRole")
public class BiddingUserRoleController {

	@Resource
	private BiddingUserRoleService biddingUserRoleService;

	/**
	 * 根据角色去查询用户的详细信息
	 * @param role
	 * @return
	 */
	@ApiOperation(value="根据角色去查询用户的详细信息")
	@ApiImplicitParam(name = "role", value = "role作为拼接路径", required = true, paramType = "path", dataType = "String")

	@GetMapping(value = "/role/{role}")
	public Result findByRole(@PathVariable String role){
		Result result =biddingUserRoleService.findByRole(role);
		return result;
	}
	/**
	 * 查询所有商务经理的详细信息
	 * @return
	 */
	@ApiOperation(value="查询所有商务经理的详细信息")
	@GetMapping(value = "/role/3")
	public Result find3ByRole(){
		String role="3";
		Result result =biddingUserRoleService.findByRole(role);
		return result;
	}
	///**
	// * 查询所有大区经理的详细信息
	// * @return
	// */
	//@GetMapping(value = "/role/2")
	//public Result find2ByRole(){
	//	String role="2";
	//	Result result =biddingUserRoleService.findByRole(role);
	//	return result;
	//}
	///**
	// * 查询所有商务统括的详细信息
	// * @return
	// */
	//@GetMapping(value = "/role/1")
	//public Result find1ByRole(){
	//	String role="1";
	//	Result result =biddingUserRoleService.findByRole(role);
	//	return result;
	//}

	///**
	//* 添加
	//*/
	//@PostMapping
	//public Result insert(@RequestBody BiddingUserRole biddingUserRole){
	//	biddingUserRoleService.insert(biddingUserRole);
	//	return new Result<>(true, StatusCode.OK, "保存成功");
	//}
	//
	///**
	//* 查询全部
	//*/
	//@GetMapping
	//public Result findAll(){
	//	List<BiddingUserRole> list = biddingUserRoleService.selectAll();
	//	return new Result<>(true, StatusCode.OK, "查询成功", list);
	//}
	//
	///**
	//* 根据ID查询
	//*/
	//@GetMapping(value = "/{id}")
	//public Result findById(@PathVariable String id){
	//	BiddingUserRole biddingUserRole =  biddingUserRoleService.findById(id);
	//	return new Result<>(true, StatusCode.OK, "查询成功", biddingUserRole);
	//}
	//
	///**
	//* 更新
	//*/
	//@PutMapping(value = "/{id}")
	//public Result update(@PathVariable String id, @RequestBody BiddingUserRole biddingUserRole) {
	//	biddingUserRole.setId(id);
	//	biddingUserRoleService.updateById(biddingUserRole);
	//	return new Result<>(true, StatusCode.OK, "修改成功");
	//}
	//
	//
	///**
	//* 删除
	//*/
	//@DeleteMapping(value = "/{id}")
	//public Result deleteById(@PathVariable String id){
	//	biddingUserRoleService.delete(id);
	//	return new Result<>(true, StatusCode.OK, "删除成功");
	//}
	//
	///**
	//* 条件查询，无分页
	//*/
	//@PostMapping(value = "/list")
	//public Result findSearch(@RequestBody BiddingUserRole biddingUserRole) {
	//List<BiddingUserRole> biddingUserRoles = biddingUserRoleService.list(biddingUserRole);
	//	return new Result<>(true, StatusCode.OK, "查询成功", biddingUserRoles);
	//}
	//
	///**
	//* 条件查询，无分页
	//*/
	//@PostMapping(value = "/list/{currentPage}/{pageSize}")
	//public Result findSearch(@RequestBody BiddingUserRole biddingUserRole, @PathVariable int currentPage, @PathVariable int pageSize) {
	//	List<BiddingUserRole> biddingUserRoles = biddingUserRoleService.list(biddingUserRole, currentPage, pageSize);
	//	Integer count = biddingUserRoleService.selectCount(biddingUserRole);
	//	return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingUserRole>(count, biddingUserRoles));
	//}
}