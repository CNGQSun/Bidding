package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingUser;
import com.dsmpharm.bidding.service.BiddingUserService;
import com.dsmpharm.bidding.utils.PageResult;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
@RestController
@RequestMapping("/biddingUser")
public class BiddingUserController {

	@Resource
	private BiddingUserService biddingUserService;

	/**
	* 添加用户，点击提交
	*/
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
	 */
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
	*/
	@GetMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findAll(@RequestBody Map map,@PathVariable int currentPage, @PathVariable int pageSize){
		Result result = biddingUserService.selectAll(map,currentPage,pageSize);
		return result;
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		BiddingUser biddingUser =  biddingUserService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingUser);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingUser biddingUser) {
		biddingUser.setId(id);
		biddingUserService.updateById(biddingUser);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		biddingUserService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingUser biddingUser) {
	List<BiddingUser> biddingUsers = biddingUserService.list(biddingUser);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingUsers);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingUser biddingUser, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingUser> biddingUsers = biddingUserService.list(biddingUser, currentPage, pageSize);
		Integer count = biddingUserService.selectCount(biddingUser);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingUser>(count, biddingUsers));
	}
}