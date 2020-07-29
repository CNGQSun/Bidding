package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingUserFramework;
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
	@ApiImplicitParam(name = "biddingUserFramework", value = "人员架构实体（只需提供页面输入的参数）", required = true, paramType = "body", dataType = "BiddingUserFramework")

	@PostMapping("/sub")
	public Result insertSub(@RequestBody BiddingUserFramework biddingUserFramework){
		Result result=biddingUserFrameworkService.insertFrameSub(biddingUserFramework);
		return result ;
	}
	/**
	 * 添加人员架构,点击保存
	 */
	@ApiOperation(value="添加人员架构,点击保存" )
	@ApiImplicitParam(name = "biddingUserFramework", value = "人员架构实体（只需提供页面输入的参数）", required = true, paramType = "body", dataType = "BiddingUserFramework")

	@PostMapping("/pre")
	public Result insertPre(@RequestBody BiddingUserFramework biddingUserFramework){
		Result result=biddingUserFrameworkService.insertFramePre(biddingUserFramework);
		return result ;
	}

	/**
	 * 对已保存的架构关系进行修改，并提交
	 */
	@ApiOperation(value="对已保存的架构关系进行修改，并提交" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "id作为拼接路径", required = true, paramType = "path", dataType = "String"),
			@ApiImplicitParam(name = "biddingUserFramework", value = "人员架构实体（只需提供页面输入的参数）", required = true, paramType = "body", dataType = "BiddingUserFramework")
	})
	@PutMapping(value = "/sub/{id}")
	public Result updateSub(@PathVariable String id, @RequestBody BiddingUserFramework biddingUserFramework) {
		biddingUserFramework.setId(id);
		Result result = biddingUserFrameworkService.updateById(biddingUserFramework);
		return result;
	}

	/**
	 * 根据ID查询用户架构关系及用户信息
	 */
	@ApiOperation(value="根据ID查询用户架构关系及用户信息" )
	@ApiImplicitParam(name = "id", value = "id作为拼接路径", required = true, paramType = "path", dataType = "String")

	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		Result result=  biddingUserFrameworkService.findById(id);
		return result;
	}

	/**
	 * 分页、条件查询全部未删除的用户架构关系
	 * @param map
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value="分页、条件查询全部未删除的用户架构关系" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "currentPage", value = "当前页", required = true, paramType = "path", dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "每页展示条数", required = true, paramType = "path", dataType = "int"),
			@ApiImplicitParam(name = "map", value = "查询参数（userId（用户id）：String;parentId（用户上一级Id）:String;graParentId（再上一级Id）:String）", required = true, paramType = "body", dataType = "Map")
	})

	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findAll(@RequestBody Map map, @PathVariable int currentPage, @PathVariable int pageSize){
		Result result = biddingUserFrameworkService.selectAll(map,currentPage,pageSize);
		return result;
	}

	/**
	 * 根据id删除单个用户架构关系
	 */
	@ApiOperation(value="根据ID删除单个用户架构关系" )
	@ApiImplicitParam(name = "id", value = "id作为拼接路径", required = true, paramType = "path", dataType = "String")

	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		Result result = biddingUserFrameworkService.delete(id);
		return result;
	}

	/**
	 * 根据id删除多个用户架构关系
	 */
	@ApiOperation(value="根据IDS批量删除用户架构关系" )
	@ApiImplicitParam(name = "ids", value = "ids为id组成的字符串,','分割,例：1,2,3", required = true, paramType = "query", dataType = "String")

	@DeleteMapping()
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