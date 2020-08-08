package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingInfoFilling;
import com.dsmpharm.bidding.service.BiddingInfoFillingService;
import com.dsmpharm.bidding.utils.PageResult;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/08
 */
@RestController
@RequestMapping("/biddingInfoFilling")
public class BiddingInfoFillingController {

	@Resource
	private BiddingInfoFillingService biddingInfoFillingService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody BiddingInfoFilling biddingInfoFilling){
		biddingInfoFillingService.insert(biddingInfoFilling);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingInfoFilling> list = biddingInfoFillingService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		BiddingInfoFilling biddingInfoFilling =  biddingInfoFillingService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingInfoFilling);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingInfoFilling biddingInfoFilling) {
		biddingInfoFilling.setId(id);
		biddingInfoFillingService.updateById(biddingInfoFilling);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		biddingInfoFillingService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingInfoFilling biddingInfoFilling) {
	List<BiddingInfoFilling> biddingInfoFillings = biddingInfoFillingService.list(biddingInfoFilling);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingInfoFillings);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingInfoFilling biddingInfoFilling, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingInfoFilling> biddingInfoFillings = biddingInfoFillingService.list(biddingInfoFilling, currentPage, pageSize);
		Integer count = biddingInfoFillingService.selectCount(biddingInfoFilling);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingInfoFilling>(count, biddingInfoFillings));
	}
}