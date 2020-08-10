package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.AppFlowApproval;
import com.dsmpharm.bidding.service.AppFlowApprovalService;
import com.dsmpharm.bidding.utils.PageResult;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/10
 */
@CrossOrigin
@RestController
@RequestMapping("/appFlowApproval")
public class AppFlowApprovalController {

	@Resource
	private AppFlowApprovalService appFlowApprovalService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody AppFlowApproval appFlowApproval){
		appFlowApprovalService.insert(appFlowApproval);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<AppFlowApproval> list = appFlowApprovalService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		AppFlowApproval appFlowApproval =  appFlowApprovalService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", appFlowApproval);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody AppFlowApproval appFlowApproval) {
		appFlowApproval.setId(id);
		appFlowApprovalService.updateById(appFlowApproval);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		appFlowApprovalService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody AppFlowApproval appFlowApproval) {
	List<AppFlowApproval> appFlowApprovals = appFlowApprovalService.list(appFlowApproval);
		return new Result<>(true, StatusCode.OK, "查询成功", appFlowApprovals);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody AppFlowApproval appFlowApproval, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<AppFlowApproval> appFlowApprovals = appFlowApprovalService.list(appFlowApproval, currentPage, pageSize);
		Integer count = appFlowApprovalService.selectCount(appFlowApproval);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<AppFlowApproval>(count, appFlowApprovals));
	}
}