package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingProjectTypeService;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/** 
 * <br/>
 * Created by Grant on 2020/08/03
 */
@CrossOrigin
@RestController
@RequestMapping("/biddingProjectType")
@Api(tags = "项目类型相关接口")
public class BiddingProjectTypeController {
	private static Logger log = LoggerFactory.getLogger(BiddingProjectTypeController.class);

	@Resource
	private BiddingProjectTypeService biddingProjectTypeService;

	/**
	 * 获取所有未删除的项目类型
	 * @return
	 */
	@ApiOperation(value="立项中“项目类型”接口" )
	@GetMapping
	public Result findAll(){
		Result result = biddingProjectTypeService.selectNoDel();
		return result;
	}
	///**
	//* 添加
	//*/
	//@PostMapping
	//public Result insert(@RequestBody BiddingProjectType biddingProjectType){
	//	biddingProjectTypeService.insert(biddingProjectType);
	//	return new Result<>(true, StatusCode.OK, "保存成功");
	//}
	//

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