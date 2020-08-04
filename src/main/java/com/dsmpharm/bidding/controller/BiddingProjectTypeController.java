package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingProjectTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/** 
 * <br/>
 * Created by Grant on 2020/08/03
 */
@RestController
@RequestMapping("/biddingProjectType")
public class BiddingProjectTypeController {

	@Resource
	private BiddingProjectTypeService biddingProjectTypeService;

	///**
	//* 添加
	//*/
	//@PostMapping
	//public Result insert(@RequestBody BiddingProjectType biddingProjectType){
	//	biddingProjectTypeService.insert(biddingProjectType);
	//	return new Result<>(true, StatusCode.OK, "保存成功");
	//}
	//
	///**
	//* 查询全部
	//*/
	//@GetMapping
	//public Result findAll(){
	//	List<BiddingProjectType> list = biddingProjectTypeService.selectAll();
	//	return new Result<>(true, StatusCode.OK, "查询成功", list);
	//}
	//
	///**
	//* 根据ID查询
	//*/
	//@GetMapping(value = "/{id}")
	//public Result findById(@PathVariable String id){
	//	BiddingProjectType biddingProjectType =  biddingProjectTypeService.findById(id);
	//	return new Result<>(true, StatusCode.OK, "查询成功", biddingProjectType);
	//}
	//
	///**
	//* 更新
	//*/
	//@PutMapping(value = "/{id}")
	//public Result update(@PathVariable String id, @RequestBody BiddingProjectType biddingProjectType) {
	//	biddingProjectType.setId(id);
	//	biddingProjectTypeService.updateById(biddingProjectType);
	//	return new Result<>(true, StatusCode.OK, "修改成功");
	//}
	//
	//
	///**
	//* 删除
	//*/
	//@DeleteMapping(value = "/{id}")
	//public Result deleteById(@PathVariable String id){
	//	biddingProjectTypeService.delete(id);
	//	return new Result<>(true, StatusCode.OK, "删除成功");
	//}
	//
	///**
	//* 条件查询，无分页
	//*/
	//@PostMapping(value = "/list")
	//public Result findSearch(@RequestBody BiddingProjectType biddingProjectType) {
	//List<BiddingProjectType> biddingProjectTypes = biddingProjectTypeService.list(biddingProjectType);
	//	return new Result<>(true, StatusCode.OK, "查询成功", biddingProjectTypes);
	//}
	//
	///**
	//* 条件查询，无分页
	//*/
	//@PostMapping(value = "/list/{currentPage}/{pageSize}")
	//public Result findSearch(@RequestBody BiddingProjectType biddingProjectType, @PathVariable int currentPage, @PathVariable int pageSize) {
	//	List<BiddingProjectType> biddingProjectTypes = biddingProjectTypeService.list(biddingProjectType, currentPage, pageSize);
	//	Integer count = biddingProjectTypeService.selectCount(biddingProjectType);
	//	return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingProjectType>(count, biddingProjectTypes));
	//}
}