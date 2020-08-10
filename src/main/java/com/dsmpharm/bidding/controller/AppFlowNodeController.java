package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.AppFlowNode;
import com.dsmpharm.bidding.service.AppFlowNodeService;
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
@RequestMapping("/appFlowNode")
public class AppFlowNodeController {

	@Resource
	private AppFlowNodeService appFlowNodeService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody AppFlowNode appFlowNode){
		appFlowNodeService.insert(appFlowNode);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<AppFlowNode> list = appFlowNodeService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		AppFlowNode appFlowNode =  appFlowNodeService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", appFlowNode);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody AppFlowNode appFlowNode) {
		appFlowNode.setId(id);
		appFlowNodeService.updateById(appFlowNode);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		appFlowNodeService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody AppFlowNode appFlowNode) {
	List<AppFlowNode> appFlowNodes = appFlowNodeService.list(appFlowNode);
		return new Result<>(true, StatusCode.OK, "查询成功", appFlowNodes);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody AppFlowNode appFlowNode, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<AppFlowNode> appFlowNodes = appFlowNodeService.list(appFlowNode, currentPage, pageSize);
		Integer count = appFlowNodeService.selectCount(appFlowNode);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<AppFlowNode>(count, appFlowNodes));
	}
}