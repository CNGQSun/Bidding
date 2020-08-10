package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.AppFlowApply;
import com.dsmpharm.bidding.service.AppFlowApplyService;
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
@RequestMapping("/appFlowApply")
public class AppFlowApplyController {

	@Resource
	private AppFlowApplyService appFlowApplyService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody AppFlowApply appFlowApply){
		appFlowApplyService.insert(appFlowApply);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<AppFlowApply> list = appFlowApplyService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		AppFlowApply appFlowApply =  appFlowApplyService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", appFlowApply);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody AppFlowApply appFlowApply) {
		appFlowApply.setId(id);
		appFlowApplyService.updateById(appFlowApply);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		appFlowApplyService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody AppFlowApply appFlowApply) {
	List<AppFlowApply> appFlowApplys = appFlowApplyService.list(appFlowApply);
		return new Result<>(true, StatusCode.OK, "查询成功", appFlowApplys);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody AppFlowApply appFlowApply, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<AppFlowApply> appFlowApplys = appFlowApplyService.list(appFlowApply, currentPage, pageSize);
		Integer count = appFlowApplyService.selectCount(appFlowApply);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<AppFlowApply>(count, appFlowApplys));
	}
}