package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingUserProvinceService;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/07/27
 */
@CrossOrigin
@RestController
@RequestMapping("/biddingUserProvince")
@Api(tags = "商务人员省份覆盖相关接口")
public class BiddingUserProvinceController {
	private static Logger log = LoggerFactory.getLogger(BiddingUserProvinceController.class);

	@Resource
	private BiddingUserProvinceService biddingUserProvinceService;

	/**
	 * 添加用户省份，点击提交
	 * @param map
	 * @return
	 */
	@ApiOperation(value="添加用户省份，点击提交" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "proId", value = "省份ID", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping("/sub")
	public Result insertSub(@RequestParam Map map){
		Result result=biddingUserProvinceService.insertSub(map);
		return result;
	}

	/**
	 * 添加用户省份，点击保存
	 * @param map
	 * @return
	 */
	@ApiOperation(value="添加用户省份，点击保存" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "proId", value = "省份ID", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping("/pre")
	public Result insertPro(@RequestParam Map map){
		Result result=biddingUserProvinceService.insertPro(map);
		return result;
	}

	/**
	 * 根据ID查询用户省份信息
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据ID查询用户省份信息" )
	@ApiImplicitParam(name = "id", value = "userProvince的ID", required = true, paramType = "query", dataType = "String")
	@GetMapping
	public Result findById(@RequestParam String id){
		Result result =  biddingUserProvinceService.findById(id);
		return result;
	}

	/**
	 * 根据Id删除用户省份信息
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据ID删除用户省份信息" )
	@ApiImplicitParam(name = "id", value = "userProvince的ID", required = true, paramType = "query", dataType = "String")
	@PostMapping("/del")
	public Result deleteById(@RequestParam String id){
		Result result =biddingUserProvinceService.deleteById(id);
		return result;
	}

	/**
	 * 根据IdS删除多个用户省份信息
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="根据IDS批量删除用户省份信息" )
	@ApiImplicitParam(name = "ids", value = "ids为id组成的字符串,','分割,例：1,2,3", required = true, paramType = "query", dataType = "String")
	@PostMapping("/del/batch")
	public Result deleteByIds(@RequestParam(value = "ids") String ids){
		Result result =biddingUserProvinceService.deleteByIds(ids);
		return result;
	}

	/**
	 * 根据id修改用户省份信息
	 * @param map
	 * @return
	 */
	@ApiOperation(value="根据id修改用户省份信息" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "userProvince的ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "proId", value = "省份ID", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping("/update")
	public Result update(@RequestParam Map map) {
		Result result=biddingUserProvinceService.updateById(map);
		return result;
	}

	/**
	 * 分页、条件查询所有用户省份信息
	 * @param map
	 * @return
	 */
	@ApiOperation(value="分页、条件查询所有用户省份信息" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "currentPage", value = "当前页", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "pageSize", value = "每页展示条数", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "name", value = "用户名", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "proId", value = "省份ID", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping(value = "/list")
	public Result findSearch(@RequestParam Map map) {
		Result result = biddingUserProvinceService.list(map);
		return result;
	}
}