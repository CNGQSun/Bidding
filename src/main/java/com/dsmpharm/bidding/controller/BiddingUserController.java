package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingUser;
import com.dsmpharm.bidding.service.BiddingUserService;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
@CrossOrigin
@RestController
@RequestMapping("/biddingUser")
public class BiddingUserController {

	@Resource
	private BiddingUserService biddingUserService;

	/**
	 * 添加用户，点击提交
	 * @param map
	 * @return
	 */
	@ApiOperation(value="添加用户 提交" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "用户名", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "phoneNumber", value = "手机号", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "role", value = "角色Id", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping("/sub")
	public Result insertSub(@RequestParam Map map){
		BiddingUser biddingUser=new BiddingUser();
		String name = map.get("name").toString();
		String email = map.get("email").toString();
		String phoneNumber = map.get("phoneNumber").toString();
		String role = map.get("role").toString();//此处为role_id
		biddingUser.setName(name);
		biddingUser.setEmail(email);
		biddingUser.setPhoneNumber(phoneNumber);
		Result result=biddingUserService.insertUserSub(biddingUser,role);
		return result;
	}

	/**
	 * 添加用户，点击保存
	 * @param map
	 * @return
	 */
	@ApiOperation(value="添加用户 保存" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "用户名", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "phoneNumber", value = "手机号", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "role", value = "角色Id", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping("/pre")
	public Result insertPre(@RequestParam Map map){
		BiddingUser biddingUser=new BiddingUser();
		String name = map.get("name").toString();
		String email = map.get("email").toString();
		String phoneNumber = map.get("phoneNumber").toString();
		String role = map.get("role").toString();//此处为role_id
		biddingUser.setName(name);
		biddingUser.setEmail(email);
		biddingUser.setPhoneNumber(phoneNumber);
		Result result=biddingUserService.insertUserPre(biddingUser,role);
		return result;
	}

	/**
	 * 分页、条件查询全部用户，带参数
	 * @param map
	 * @return
	 */
	@ApiOperation(value="分页、条件查询全部用户，带参数" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "currentPage", value = "当前页", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "pageSize", value = "每页展示条数", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "name", value = "用户名", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "roleId", value = "角色Id", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping(value = "/list")
	public Result findAll(@RequestParam Map map){
		Result result = biddingUserService.selectAll(map);
		return result;
	}
	/**
	 * 根据ID查询用户详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据ID查询用户详情" )
	@ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "query", dataType = "String")

	@GetMapping
	public Result findById(@RequestParam String id){
		Result result =  biddingUserService.findById(id);
		return result;
	}

	/**
	 * 根据id修改用户信息
	 * @param map
	 * @return
	 */
	@ApiOperation(value="根据id修改用户信息" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "userId", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "name", value = "用户名", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "phoneNumber", value = "手机号", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "role", value = "角色Id", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping("/update")
	public Result update(@RequestParam Map map) {
		BiddingUser biddingUser=new BiddingUser();
		String id = map.get("id").toString();
		String name = map.get("name").toString();
		String email = map.get("email").toString();
		String phoneNumber = map.get("phoneNumber").toString();
		String role = map.get("role").toString();//此处为role_id
		biddingUser.setId(id);
		biddingUser.setName(name);
		biddingUser.setEmail(email);
		biddingUser.setPhoneNumber(phoneNumber);
		Result result=biddingUserService.updateById(biddingUser,role);
		return result;
	}



	/**
	 * 根据ID删除用户信息
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据ID删除用户信息" )
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "query", dataType = "String")
	@PostMapping("/del")
	public Result deleteById(@RequestParam String id){
		Result result=biddingUserService.deleteById(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}
	//
	///**
	//* 条件查询，无分页
	//*/
	//@PostMapping(value = "/list")
	//public Result findSearch(@RequestBody BiddingUser biddingUser) {
	//List<BiddingUser> biddingUsers = biddingUserService.list(biddingUser);
	//	return new Result<>(true, StatusCode.OK, "查询成功", biddingUsers);
	//}
	//
	///**
	//* 条件查询，无分页
	//*/
	//@PostMapping(value = "/list/{currentPage}/{pageSize}")
	//public Result findSearch(@RequestBody BiddingUser biddingUser, @PathVariable int currentPage, @PathVariable int pageSize) {
	//	List<BiddingUser> biddingUsers = biddingUserService.list(biddingUser, currentPage, pageSize);
	//	Integer count = biddingUserService.selectCount(biddingUser);
	//	return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingUser>(count, biddingUsers));
	//}
}