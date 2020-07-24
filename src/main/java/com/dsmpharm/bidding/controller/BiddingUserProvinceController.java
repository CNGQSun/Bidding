package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingUserProvince;
import com.dsmpharm.bidding.service.BiddingUserProvinceService;
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
@RequestMapping("/biddingUserProvince")
public class BiddingUserProvinceController {

	@Resource
	private BiddingUserProvinceService biddingUserProvinceService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody BiddingUserProvince biddingUserProvince){
		biddingUserProvinceService.insert(biddingUserProvince);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingUserProvince> list = biddingUserProvinceService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		BiddingUserProvince biddingUserProvince =  biddingUserProvinceService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingUserProvince);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingUserProvince biddingUserProvince) {
		biddingUserProvince.setId(id);
		biddingUserProvinceService.updateById(biddingUserProvince);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		biddingUserProvinceService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingUserProvince biddingUserProvince) {
	List<BiddingUserProvince> biddingUserProvinces = biddingUserProvinceService.list(biddingUserProvince);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingUserProvinces);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingUserProvince biddingUserProvince, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingUserProvince> biddingUserProvinces = biddingUserProvinceService.list(biddingUserProvince, currentPage, pageSize);
		Integer count = biddingUserProvinceService.selectCount(biddingUserProvince);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingUserProvince>(count, biddingUserProvinces));
	}
}