package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingUserFramework;
import com.dsmpharm.bidding.service.BiddingUserFrameworkService;
import com.dsmpharm.bidding.utils.PageResult;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
	* 添加人员架构,点击提交
	*/
	@PostMapping("/sub")
	public Result insertSub(@RequestBody BiddingUserFramework biddingUserFramework){
		Result result=biddingUserFrameworkService.insertFrameSub(biddingUserFramework);
		return result ;
	}
	/**
	 * 添加人员架构,点击保存
	 */
	@PostMapping("/pre")
	public Result insertPre(@RequestBody BiddingUserFramework biddingUserFramework){
		Result result=biddingUserFrameworkService.insertFramePre(biddingUserFramework);
		return result ;
	}

	/**
	 * 对已保存的架构关系进行修改，并提交
	 */
	@PutMapping(value = "/sub/{id}")
	public Result updateSub(@PathVariable String id, @RequestBody BiddingUserFramework biddingUserFramework) {
		biddingUserFramework.setId(id);
		Result result = biddingUserFrameworkService.updateById(biddingUserFramework);
		return result;
	}

	/**
	 * 根据ID查询用户架构关系及用户信息
	 */
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		Result result=  biddingUserFrameworkService.findById(id);
		return result;
	}

	/**
	* 查询全部未删除的用户架构关系
	*/
	@GetMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findAll(@RequestBody Map map,@PathVariable int currentPage, @PathVariable int pageSize){
		Result result = biddingUserFrameworkService.selectAll(map,currentPage,pageSize);
		return result;
	}

	/**
	 * 根据id删除单个用户架构关系
	 */
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		Result result = biddingUserFrameworkService.delete(id);
		return result;
	}

	/**
	 * 根据id删除多个用户架构关系
	 */
	@DeleteMapping()
	public Result deleteByIds(@RequestParam(value = "ids") String ids){
		Result result = biddingUserFrameworkService.deleteIds(ids);
		return result;
	}

	///**
	//* 更新
	//*/
	//@PutMapping(value = "/{id}")
	//public Result update(@PathVariable String id, @RequestBody BiddingUserFramework biddingUserFramework) {
	//	biddingUserFramework.setId(id);
	//	biddingUserFrameworkService.updateById(biddingUserFramework);
	//	return new Result<>(true, StatusCode.OK, "修改成功");
	//}


	///**
	//* 删除
	//*/
	//@DeleteMapping(value = "/{id}")
	//public Result deleteById(@PathVariable String id){
	//	biddingUserFrameworkService.delete(id);
	//	return new Result<>(true, StatusCode.OK, "删除成功");
	//}

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