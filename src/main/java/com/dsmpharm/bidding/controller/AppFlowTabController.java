package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.AppFlowTab;
import com.dsmpharm.bidding.service.AppFlowTabService;
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
@RequestMapping("/appFlowTab")
public class AppFlowTabController {

	@Resource
	private AppFlowTabService appFlowTabService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody AppFlowTab appFlowTab){
		appFlowTabService.insert(appFlowTab);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<AppFlowTab> list = appFlowTabService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		AppFlowTab appFlowTab =  appFlowTabService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", appFlowTab);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody AppFlowTab appFlowTab) {
		appFlowTab.setId(id);
		appFlowTabService.updateById(appFlowTab);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		appFlowTabService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody AppFlowTab appFlowTab) {
	List<AppFlowTab> appFlowTabs = appFlowTabService.list(appFlowTab);
		return new Result<>(true, StatusCode.OK, "查询成功", appFlowTabs);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody AppFlowTab appFlowTab, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<AppFlowTab> appFlowTabs = appFlowTabService.list(appFlowTab, currentPage, pageSize);
		Integer count = appFlowTabService.selectCount(appFlowTab);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<AppFlowTab>(count, appFlowTabs));
	}
}