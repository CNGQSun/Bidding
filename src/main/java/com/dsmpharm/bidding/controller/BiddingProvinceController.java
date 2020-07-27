package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingProvince;
import com.dsmpharm.bidding.service.BiddingProvinceService;
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
@RequestMapping("/biddingProvince")
public class BiddingProvinceController {

	@Resource
	private BiddingProvinceService biddingProvinceService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody BiddingProvince biddingProvince){
		biddingProvinceService.insert(biddingProvince);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingProvince> list = biddingProvinceService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable Integer id){
		BiddingProvince biddingProvince =  biddingProvinceService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingProvince);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable Integer id, @RequestBody BiddingProvince biddingProvince) {
		biddingProvince.setId(id);
		biddingProvinceService.updateById(biddingProvince);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable Integer id){
		biddingProvinceService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingProvince biddingProvince) {
	List<BiddingProvince> biddingProvinces = biddingProvinceService.list(biddingProvince);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingProvinces);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingProvince biddingProvince, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingProvince> biddingProvinces = biddingProvinceService.list(biddingProvince, currentPage, pageSize);
		Integer count = biddingProvinceService.selectCount(biddingProvince);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingProvince>(count, biddingProvinces));
	}
}