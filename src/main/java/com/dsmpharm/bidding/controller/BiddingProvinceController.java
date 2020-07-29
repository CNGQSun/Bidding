package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingProvinceService;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/** 
 * <br/>
 * Created by Grant on 2020/07/27
 */
@CrossOrigin
@RestController
@RequestMapping("/biddingProvince")
public class BiddingProvinceController {

	@Resource
	private BiddingProvinceService biddingProvinceService;


	/**
	 * 查询全部省份信息
	 */
	@ApiOperation(value="查询全部省份信息" )

	@GetMapping
	public Result findAll(){
		Result result= biddingProvinceService.selectAllPro();
		return result;
	}

	///**
	//* 添加
	//*/
	//@PostMapping
	//public Result insert(@RequestBody BiddingProvince biddingProvince){
	//	biddingProvinceService.insert(biddingProvince);
	//	return new Result<>(true, StatusCode.OK, "保存成功");
	//}
	//
	//
	//
	///**
	//* 根据ID查询
	//*/
	//@GetMapping(value = "/{id}")
	//public Result findById(@PathVariable Integer id){
	//	BiddingProvince biddingProvince =  biddingProvinceService.findById(id);
	//	return new Result<>(true, StatusCode.OK, "查询成功", biddingProvince);
	//}
	//
	///**
	//* 更新
	//*/
	//@PutMapping(value = "/{id}")
	//public Result update(@PathVariable Integer id, @RequestBody BiddingProvince biddingProvince) {
	//	biddingProvince.setId(id);
	//	biddingProvinceService.updateById(biddingProvince);
	//	return new Result<>(true, StatusCode.OK, "修改成功");
	//}
	//
	//
	///**
	//* 删除
	//*/
	//@DeleteMapping(value = "/{id}")
	//public Result deleteById(@PathVariable Integer id){
	//	biddingProvinceService.delete(id);
	//	return new Result<>(true, StatusCode.OK, "删除成功");
	//}
	//
	///**
	//* 条件查询，无分页
	//*/
	//@PostMapping(value = "/list")
	//public Result findSearch(@RequestBody BiddingProvince biddingProvince) {
	//List<BiddingProvince> biddingProvinces = biddingProvinceService.list(biddingProvince);
	//	return new Result<>(true, StatusCode.OK, "查询成功", biddingProvinces);
	//}
	//
	///**
	//* 条件查询，无分页
	//*/
	//@PostMapping(value = "/list/{currentPage}/{pageSize}")
	//public Result findSearch(@RequestBody BiddingProvince biddingProvince, @PathVariable int currentPage, @PathVariable int pageSize) {
	//	List<BiddingProvince> biddingProvinces = biddingProvinceService.list(biddingProvince, currentPage, pageSize);
	//	Integer count = biddingProvinceService.selectCount(biddingProvince);
	//	return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingProvince>(count, biddingProvinces));
	//}
}