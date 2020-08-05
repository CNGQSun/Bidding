package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingProjectPhaseService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/** 
 * <br/>
 * Created by Grant on 2020/08/03
 */
@CrossOrigin
@RestController
@RequestMapping("/biddingProjectPhase")
public class BiddingProjectPhaseController {

	@Resource
	private BiddingProjectPhaseService biddingProjectPhaseService;

	///**
	//* 添加
	//*/
	//@PostMapping
	//public Result insert(@RequestBody BiddingProjectPhase biddingProjectPhase){
	//	biddingProjectPhaseService.insert(biddingProjectPhase);
	//	return new Result<>(true, StatusCode.OK, "保存成功");
	//}
	//
	///**
	//* 查询全部
	//*/
	//@GetMapping
	//public Result findAll(){
	//	List<BiddingProjectPhase> list = biddingProjectPhaseService.selectAll();
	//	return new Result<>(true, StatusCode.OK, "查询成功", list);
	//}
	//
	///**
	//* 根据ID查询
	//*/
	//@GetMapping(value = "/{id}")
	//public Result findById(@PathVariable String id){
	//	BiddingProjectPhase biddingProjectPhase =  biddingProjectPhaseService.findById(id);
	//	return new Result<>(true, StatusCode.OK, "查询成功", biddingProjectPhase);
	//}
	//
	///**
	//* 更新
	//*/
	//@PutMapping(value = "/{id}")
	//public Result update(@PathVariable String id, @RequestBody BiddingProjectPhase biddingProjectPhase) {
	//	biddingProjectPhase.setId(id);
	//	biddingProjectPhaseService.updateById(biddingProjectPhase);
	//	return new Result<>(true, StatusCode.OK, "修改成功");
	//}
	//
	//
	///**
	//* 删除
	//*/
	//@DeleteMapping(value = "/{id}")
	//public Result deleteById(@PathVariable String id){
	//	biddingProjectPhaseService.delete(id);
	//	return new Result<>(true, StatusCode.OK, "删除成功");
	//}
	//
	///**
	//* 条件查询，无分页
	//*/
	//@PostMapping(value = "/list")
	//public Result findSearch(@RequestBody BiddingProjectPhase biddingProjectPhase) {
	//List<BiddingProjectPhase> biddingProjectPhases = biddingProjectPhaseService.list(biddingProjectPhase);
	//	return new Result<>(true, StatusCode.OK, "查询成功", biddingProjectPhases);
	//}
	//
	///**
	//* 条件查询，无分页
	//*/
	//@PostMapping(value = "/list/{currentPage}/{pageSize}")
	//public Result findSearch(@RequestBody BiddingProjectPhase biddingProjectPhase, @PathVariable int currentPage, @PathVariable int pageSize) {
	//	List<BiddingProjectPhase> biddingProjectPhases = biddingProjectPhaseService.list(biddingProjectPhase, currentPage, pageSize);
	//	Integer count = biddingProjectPhaseService.selectCount(biddingProjectPhase);
	//	return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingProjectPhase>(count, biddingProjectPhases));
	//}
}