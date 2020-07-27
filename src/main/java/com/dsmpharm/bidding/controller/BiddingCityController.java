package com.dsmpharm.bidding.controller;


import com.dsmpharm.bidding.pojo.BiddingCity;
import com.dsmpharm.bidding.service.BiddingCityService;
import com.dsmpharm.bidding.utils.PageResult;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/07/27
 */
@RestController
@RequestMapping("/biddingCity")
public class BiddingCityController {

	@Resource
	private BiddingCityService biddingCityService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody BiddingCity biddingCity){
		biddingCityService.insert(biddingCity);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingCity> list = biddingCityService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable Integer id){
		BiddingCity biddingCity =  biddingCityService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingCity);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable Integer id, @RequestBody BiddingCity biddingCity) {
		biddingCity.setId(id);
		biddingCityService.updateById(biddingCity);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable Integer id){
		biddingCityService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingCity biddingCity) {
	List<BiddingCity> biddingCitys = biddingCityService.list(biddingCity);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingCitys);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingCity biddingCity, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingCity> biddingCitys = biddingCityService.list(biddingCity, currentPage, pageSize);
		Integer count = biddingCityService.selectCount(biddingCity);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingCity>(count, biddingCitys));
	}
}