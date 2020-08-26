package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingUser;
import com.dsmpharm.bidding.service.BiddingUserService;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Api(tags = "各部门人员主数据相关接口")
public class BiddingUserController {
	private static Logger log = LoggerFactory.getLogger(BiddingUserController.class);

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
			//@ApiImplicitParam(name = "password", value = "用户密码", required = true, paramType = "query", dataType = "String"),
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

	/**
	 * 根据IDS批量删除用户信息
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="根据IDS批量删除用户信息" )
	@ApiImplicitParam(name = "ids", value = "ids为用户id组成的字符串,','分割,例：1,2,3", required = true, paramType = "query", dataType = "String")
	@PostMapping("/del/batch")
	public Result deleteByIdS(@RequestParam String ids){
		Result result=biddingUserService.deleteByIdS(ids);
		return result;
	}


	/**
	 * 登录
	 * @param map
	 * @return
	 */
	@ApiOperation(value="用户登录" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "userId，这些用户已设置密码：80010645，80016105，80017179，80018118，80018208，80019042，80019176", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "password", value = "登录密码（该用户对应的手机号）", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "Authorization", value = "不是参数，登录成功之后，需在此请求头中存入token", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping(value = "/login")
	private Result login(@RequestParam Map map){
		Result result=biddingUserService.login(map);
		return result;
	}
}