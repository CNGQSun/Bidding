package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.AppFlowLine;
import com.dsmpharm.bidding.service.AppFlowLineService;
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
@RequestMapping("/appFlowLine")
public class AppFlowLineController {

	@Resource
	private AppFlowLineService appFlowLineService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody AppFlowLine appFlowLine){
		appFlowLineService.insert(appFlowLine);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<AppFlowLine> list = appFlowLineService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		AppFlowLine appFlowLine =  appFlowLineService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", appFlowLine);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody AppFlowLine appFlowLine) {
		appFlowLine.setId(id);
		appFlowLineService.updateById(appFlowLine);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		appFlowLineService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody AppFlowLine appFlowLine) {
	List<AppFlowLine> appFlowLines = appFlowLineService.list(appFlowLine);
		return new Result<>(true, StatusCode.OK, "查询成功", appFlowLines);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody AppFlowLine appFlowLine, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<AppFlowLine> appFlowLines = appFlowLineService.list(appFlowLine, currentPage, pageSize);
		Integer count = appFlowLineService.selectCount(appFlowLine);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<AppFlowLine>(count, appFlowLines));
	}
}