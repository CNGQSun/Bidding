package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingUserFramework;
import com.dsmpharm.bidding.service.BiddingUserFrameworkService;
import com.dsmpharm.bidding.utils.PageResult;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
@RestController
@RequestMapping("/biddingUserFramework")
public class BiddingUserFrameworkController {

	@Resource
	private BiddingUserFrameworkService biddingUserFrameworkService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody BiddingUserFramework biddingUserFramework){
		biddingUserFrameworkService.insert(biddingUserFramework);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingUserFramework> list = biddingUserFrameworkService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		BiddingUserFramework biddingUserFramework =  biddingUserFrameworkService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingUserFramework);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingUserFramework biddingUserFramework) {
		biddingUserFramework.setId(id);
		biddingUserFrameworkService.updateById(biddingUserFramework);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		biddingUserFrameworkService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingUserFramework biddingUserFramework) {
	List<BiddingUserFramework> biddingUserFrameworks = biddingUserFrameworkService.list(biddingUserFramework);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingUserFrameworks);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingUserFramework biddingUserFramework, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingUserFramework> biddingUserFrameworks = biddingUserFrameworkService.list(biddingUserFramework, currentPage, pageSize);
		Integer count = biddingUserFrameworkService.selectCount(biddingUserFramework);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingUserFramework>(count, biddingUserFrameworks));
	}
}