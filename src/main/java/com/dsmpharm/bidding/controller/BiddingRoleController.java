package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingRole;
import com.dsmpharm.bidding.service.BiddingRoleService;
import com.dsmpharm.bidding.utils.PageResult;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
@RestController
@RequestMapping("/biddingRole")
public class BiddingRoleController {

	@Resource
	private BiddingRoleService biddingRoleService;

	/**
	* 添加
	*/
	@ApiOperation(value = "添加角色", notes = "添加一条角色信息")
	@ApiImplicitParam(name = "role", value = "角色详细实体role", required = true, dataType = "Role")
	@PostMapping
	public Result insert(@RequestBody BiddingRole biddingRole){
		biddingRoleService.insert(biddingRole);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingRole> list = biddingRoleService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		BiddingRole biddingRole =  biddingRoleService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingRole);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingRole biddingRole) {
		biddingRole.setId(id);
		biddingRoleService.updateById(biddingRole);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		biddingRoleService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingRole biddingRole) {
	List<BiddingRole> biddingRoles = biddingRoleService.list(biddingRole);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingRoles);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingRole biddingRole, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingRole> biddingRoles = biddingRoleService.list(biddingRole, currentPage, pageSize);
		Integer count = biddingRoleService.selectCount(biddingRole);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingRole>(count, biddingRoles));
	}
}