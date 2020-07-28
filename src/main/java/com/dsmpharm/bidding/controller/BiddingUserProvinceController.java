package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingUserProvince;
import com.dsmpharm.bidding.service.BiddingUserProvinceService;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/07/27
 */
@RestController
@RequestMapping("/biddingUserProvince")
public class BiddingUserProvinceController {

	@Resource
	private BiddingUserProvinceService biddingUserProvinceService;

	/**
	 * 添加用户省份，点击提交
	 * @param biddingUserProvince
	 * @return
	 */
	@ApiOperation(value="添加用户省份，点击提交" )
	@ApiImplicitParam(name = "biddingUserProvince", value = "用户省份实体（只需提供页面输入的参数）", required = true, paramType = "body", dataType = "BiddingUserProvince")

	@PostMapping("/sub")
	public Result insertSub(@RequestBody BiddingUserProvince biddingUserProvince){
		Result result=biddingUserProvinceService.insertSub(biddingUserProvince);
		return result;
	}

	/**
	 * 添加用户省份，点击保存
	 * @param biddingUserProvince
	 * @return
	 */
	@ApiOperation(value="添加用户省份，点击保存" )
	@ApiImplicitParam(name = "biddingUserProvince", value = "用户省份实体（只需提供页面输入的参数）", required = true, paramType = "body", dataType = "BiddingUserProvince")

	@PostMapping("/pre")
	public Result insertPro(@RequestBody BiddingUserProvince biddingUserProvince){
		Result result=biddingUserProvinceService.insertPro(biddingUserProvince);
		return result;
	}

	/**
	 * 根据ID查询用户省份信息
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据ID查询用户省份信息" )
	@ApiImplicitParam(name = "id", value = "id作为拼接路径", required = true, paramType = "path", dataType = "String")

	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		Result result =  biddingUserProvinceService.findById(id);
		return result;
	}

	/**
	 * 根据Id删除用户省份信息
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据ID删除用户省份信息" )
	@ApiImplicitParam(name = "id", value = "id作为拼接路径", required = true, paramType = "path", dataType = "String")

	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		Result result =biddingUserProvinceService.deleteById(id);
		return result;
	}

	/**
	 * 根据Id删除多个用户省份信息
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="根据IDS批量删除用户省份信息" )
	@ApiImplicitParam(name = "ids", value = "ids为id组成的字符串,','分割,例：1,2,3", required = true, paramType = "query", dataType = "String")

	@DeleteMapping
	public Result deleteByIds(@RequestParam(value = "ids") String ids){
		Result result =biddingUserProvinceService.deleteByIds(ids);
		return result;
	}

	/**
	 * 根据id修改用户省份信息
	 * @param id
	 * @param biddingUserProvince
	 * @return
	 */
	@ApiOperation(value="根据id修改用户省份信息" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "id作为拼接路径", required = true, paramType = "path", dataType = "String"),
			@ApiImplicitParam(name = "biddingUserProvince", value = "用户省份实体（只需提供页面输入的参数）", required = true, paramType = "body", dataType = "BiddingUserProvince")
	})
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingUserProvince biddingUserProvince) {
		biddingUserProvince.setId(id);
		Result result=biddingUserProvinceService.updateById(biddingUserProvince);
		return result;
	}

	/**
	 * 分页、条件查询所有用户省份信息
	 * @param map
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value="分页、条件查询所有用户省份信息" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "currentPage", value = "当前页", required = true, paramType = "path", dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "每页展示条数", required = true, paramType = "path", dataType = "int"),
			@ApiImplicitParam(name = "map", value = "查询参数（name（用户名）：String;proId（省份Id）:String;）", required = true, paramType = "body", dataType = "Map")
	})
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody Map map, @PathVariable int currentPage, @PathVariable int pageSize) {
		Result result = biddingUserProvinceService.list(map, currentPage, pageSize);
		return result;
	}


	///**
	//* 查询全部
	//*/
	//@GetMapping
	//public Result findAll(){
	//	List<BiddingUserProvince> list = biddingUserProvinceService.selectAll();
	//	return new Result<>(true, StatusCode.OK, "查询成功", list);
	//}
	//
	//
	//
	//
	//
	//
	//
	//
	///**
	//* 条件查询，无分页
	//*/
	//@PostMapping(value = "/list")
	//public Result findSearch(@RequestBody BiddingUserProvince biddingUserProvince) {
	//List<BiddingUserProvince> biddingUserProvinces = biddingUserProvinceService.list(biddingUserProvince);
	//	return new Result<>(true, StatusCode.OK, "查询成功", biddingUserProvinces);
	//}


}