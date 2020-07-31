package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingUserFrameworkService;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
@CrossOrigin
@RestController
@RequestMapping("/biddingUserFramework")
public class BiddingUserFrameworkController {

	@Resource
	private BiddingUserFrameworkService biddingUserFrameworkService;

	/**
	* 添加人员架构,点击提交
	*/
	@ApiOperation(value="添加人员架构,点击提交" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "商务经理ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "parentId", value = "大区经理ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "graParentId", value = "商务统括ID", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping("/sub")
	public Result insertSub(@RequestParam Map map){
		Result result=biddingUserFrameworkService.insertFrameSub(map);
		return result ;
	}

	/**
	 * 添加人员架构,点击保存
	 * @param map
	 * @return
	 */
	@ApiOperation(value="添加人员架构,点击保存" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "商务经理ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "parentId", value = "大区经理ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "graParentId", value = "商务统括ID", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping("/pre")
	public Result insertPre(@RequestParam Map map){
		Result result=biddingUserFrameworkService.insertFramePre(map);
		return result ;
	}

	/**
	 * 对已保存的架构关系进行修改，并提交
	 * @param map
	 * @return
	 */
	@ApiOperation(value="对已保存的架构关系进行修改，并提交" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "UserFramework的ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "userId", value = "商务经理ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "parentId", value = "大区经理ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "graParentId", value = "商务统括ID", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping("/update")
	public Result updateSub(@RequestParam Map map) {
		Result result = biddingUserFrameworkService.updateById(map);
		return result;
	}

	/**
	 * 根据ID查询用户架构关系及用户信息
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据ID查询用户架构关系及用户信息" )
	@ApiImplicitParam(name = "id", value = "UserFramework的ID", required = true, paramType = "query", dataType = "String")
	@GetMapping
	public Result findById(@RequestParam String id){
		Result result=  biddingUserFrameworkService.findById(id);
		return result;
	}

	/**
	 * 分页、条件查询全部未删除的用户架构关系
	 * @param map
	 * @return
	 */
	@ApiOperation(value="分页、条件查询全部未删除的用户架构关系" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "currentPage", value = "当前页", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "pageSize", value = "每页展示条数", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "userId", value = "商务经理ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "parentId", value = "大区经理ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "graParentId", value = "商务统括ID", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping(value = "/list")
	public Result findAll(@RequestParam Map map){
		Result result = biddingUserFrameworkService.selectAll(map);
		return result;
	}

	/**
	 * 根据id删除单个用户架构关系
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据ID删除单个用户架构关系" )
	@ApiImplicitParam(name = "id", value = "UserFramework的ID", required = true, paramType = "query", dataType = "String")
	@PostMapping("/del")
	public Result deleteById(@RequestParam String id){
		Result result = biddingUserFrameworkService.delete(id);
		return result;
	}

	/**
	 * 根据id删除多个用户架构关系
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="根据IDS批量删除用户架构关系" )
	@ApiImplicitParam(name = "ids", value = "ids为id组成的字符串,','分割,例：1,2,3", required = true, paramType = "query", dataType = "String")
	@PostMapping("/del/batch")
	public Result deleteByIds(@RequestParam(value = "ids") String ids){
		Result result = biddingUserFrameworkService.deleteIds(ids);
		return result;
	}

	///**
	//* 条件查询，无分页
	//*/
	//@PostMapping(value = "/list")
	//public Result findSearch(@RequestBody BiddingUserFramework biddingUserFramework) {
	//List<BiddingUserFramework> biddingUserFrameworks = biddingUserFrameworkService.list(biddingUserFramework);
	//	return new Result<>(true, StatusCode.OK, "查询成功", biddingUserFrameworks);
	//}
	//
	///**
	//* 条件查询，无分页
	//*/
	//@PostMapping(value = "/list/{currentPage}/{pageSize}")
	//public Result findSearch(@RequestBody BiddingUserFramework biddingUserFramework, @PathVariable int currentPage, @PathVariable int pageSize) {
	//	List<BiddingUserFramework> biddingUserFrameworks = biddingUserFrameworkService.list(biddingUserFramework, currentPage, pageSize);
	//	Integer count = biddingUserFrameworkService.selectCount(biddingUserFramework);
	//	return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingUserFramework>(count, biddingUserFrameworks));
	//}
}