package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingContentTypeService;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/** 
 * <br/>
 * Created by Grant on 2020/08/03
 */
@Api(tags = "内容设置新增中的类型接口")
@RestController
@RequestMapping("/biddingContentType")
public class BiddingContentTypeController {

	@Resource
	private BiddingContentTypeService biddingContentTypeService;


	/**
	 * 查询全部内容类型
	 * @return
	 */
	@ApiOperation(value="新增中‘类型’下拉框的接口" )
	@GetMapping
	public Result findAll(){
		Result result=biddingContentTypeService.selectAllNoDel();
		return result;
	}

	///**
	// * 添加
	// */
	//@PostMapping
	//public Result insert(@RequestBody BiddingContentType biddingContentType){
	//	biddingContentTypeService.insert(biddingContentType);
	//	return new Result<>(true, StatusCode.OK, "保存成功");
	//}
	///**
	//* 根据ID查询
	//*/
	//@GetMapping(value = "/{id}")
	//public Result findById(@PathVariable String id){
	//	BiddingContentType biddingContentType =  biddingContentTypeService.findById(id);
	//	return new Result<>(true, StatusCode.OK, "查询成功", biddingContentType);
	//}
	//
	///**
	//* 更新
	//*/
	//@PutMapping(value = "/{id}")
	//public Result update(@PathVariable String id, @RequestBody BiddingContentType biddingContentType) {
	//	biddingContentType.setId(id);
	//	biddingContentTypeService.updateById(biddingContentType);
	//	return new Result<>(true, StatusCode.OK, "修改成功");
	//}
	//
	//
	///**
	//* 删除
	//*/
	//@DeleteMapping(value = "/{id}")
	//public Result deleteById(@PathVariable String id){
	//	biddingContentTypeService.delete(id);
	//	return new Result<>(true, StatusCode.OK, "删除成功");
	//}
	//
	///**
	//* 条件查询，无分页
	//*/
	//@PostMapping(value = "/list")
	//public Result findSearch(@RequestBody BiddingContentType biddingContentType) {
	//List<BiddingContentType> biddingContentTypes = biddingContentTypeService.list(biddingContentType);
	//	return new Result<>(true, StatusCode.OK, "查询成功", biddingContentTypes);
	//}
	//
	///**
	//* 条件查询，无分页
	//*/
	//@PostMapping(value = "/list/{currentPage}/{pageSize}")
	//public Result findSearch(@RequestBody BiddingContentType biddingContentType, @PathVariable int currentPage, @PathVariable int pageSize) {
	//	List<BiddingContentType> biddingContentTypes = biddingContentTypeService.list(biddingContentType, currentPage, pageSize);
	//	Integer count = biddingContentTypeService.selectCount(biddingContentType);
	//	return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingContentType>(count, biddingContentTypes));
	//}
}