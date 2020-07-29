package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingUser;
import com.dsmpharm.bidding.service.BiddingUserService;
import com.dsmpharm.bidding.utils.Result;
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
	@ApiImplicitParam(name = "map", value = "添加参数（name(姓名)：String;email(邮箱)：String;phoneNumber(电话):String;role(角色id):String）", required = true, paramType = "body", dataType = "Map")

	@PostMapping("/sub")
	public Result insertSub(@RequestBody Map map){
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
	@ApiImplicitParam(name = "map", value = "添加参数（name(姓名)：String;email(邮箱)：String;phoneNumber(电话):String;role(角色id):String）", required = true, paramType = "body", dataType = "Map")

	@PostMapping("/pre")
	public Result insertPre(@RequestBody Map map){
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
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value="分页、条件查询全部用户，带参数" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "currentPage", value = "当前页", required = true, paramType = "path", dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "每页展示条数", required = true, paramType = "path", dataType = "int"),
			@ApiImplicitParam(name = "map", value = "查询参数（name（用户名）：String;roleId（角色Id）:String）", required = true, paramType = "body", dataType = "Map")
	})
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findAll(@RequestBody Map map,@PathVariable int currentPage, @PathVariable int pageSize){
		Result result = biddingUserService.selectAll(map,currentPage,pageSize);
		return result;
	}
	/**
	 * 根据ID查询用户详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据ID查询用户详情" )
	@ApiImplicitParam(name = "id", value = "id作为拼接路径", required = true, paramType = "path", dataType = "String")

	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		Result result =  biddingUserService.findById(id);
		return result;
	}

	/**
	 * 根据id修改用户信息
	 * @param id
	 * @param map
	 * @return
	 */
	@ApiOperation(value="根据id修改用户信息" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "id作为拼接路径", required = true, paramType = "path", dataType = "String"),
			@ApiImplicitParam(name = "map", value = "添加参数（name(姓名)：String;email(邮箱)：String;phoneNumber(电话):String;role(角色id):String）", required = true, paramType = "body", dataType = "Map")
	})
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody Map map) {
		BiddingUser biddingUser=new BiddingUser();
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

	//
	///**
	//* 删除
	//*/
	//@DeleteMapping(value = "/{id}")
	//public Result deleteById(@PathVariable String id){
	//	biddingUserService.delete(id);
	//	return new Result<>(true, StatusCode.OK, "删除成功");
	//}
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