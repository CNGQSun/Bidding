package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingContentSettingsService;
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
 * Created by Grant on 2020/08/03
 */
@Api(tags = "内容设置相关接口")
@CrossOrigin
@RestController
@RequestMapping("/biddingContentSettings")
public class BiddingContentSettingsController {
	private static Logger log = LoggerFactory.getLogger(BiddingContentSettingsController.class);

	@Resource
	private BiddingContentSettingsService biddingContentSettingsService;

	/**
	 * 新增内容设置
	 * @param map
	 * @return
	 */
	@ApiOperation(value="新增中‘提交’的接口" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "名称", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "isNull", value = "是否必填（必填为0，非必填为1）", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "contentTypeId", value = "类型ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "projectPhaseId", value = "项目阶段的ID ID对应的阶段见bidding_project_phase表", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping("/sub")
	public Result insert(@RequestParam Map map){
		Result result=biddingContentSettingsService.insertSet(map);
		return result;
	}

	/**
	 * 根据ID查询内容设置详细信息
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据ID查询内容设置详细信息（修改中数据回显）" )
	@ApiImplicitParam(name = "id", value = "contentSettingsID", required = true, paramType = "query", dataType = "String")

	@GetMapping()
	public Result findById(@RequestParam String id){
		Result result =  biddingContentSettingsService.findById(id);
		return result;
	}

	/**
	 * 根据ID修改内容设置详细信息
	 * @param map
	 * @return
	 */
	@ApiOperation(value="根据ID修改内容设置详细信息（修改）" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "contentSettingsID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "name", value = "名称", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "isNull", value = "是否必填（必填为0，非必填为1）", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "contentTypeId", value = "类型ID", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping("/update")
	public Result update(@RequestParam Map map) {
		Result result=biddingContentSettingsService.updateById(map);
		return result;
	}

	/**
	 * 分页、条件查询所有内容设置
	 * @param map
	 * @return
	 */
	@ApiOperation(value="分页、条件查询所有内容设置(包括首页数据展示、搜索)" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "名称", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "projectPhaseId", value = "项目阶段的ID ID对应的阶段名称见bidding_project_phase表", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "currentPage", value = "当前页", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "pageSize", value = "每页展示条数", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping(value = "/list")
	public Result findSearch(@RequestParam Map map) {
		Result result = biddingContentSettingsService.list(map);
		return result;
	}

	/**
	 * 根据ID删除内容设置信息
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据ID删除内容设置信息（删除）" )
	@ApiImplicitParam(name = "id", value = "contentSettingsID", required = true, paramType = "query", dataType = "String")

	@PostMapping("/del")
	public Result deleteById(@RequestParam String id){
		Result result = biddingContentSettingsService.deleteById(id);
		return result;
	}

	/**
	 * 根据IDS批量删除内容设置信息
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="根据IDS批量删除内容设置信息（批量删除）" )
	@ApiImplicitParam(name = "ids", value = "ids为contentSettingsID组成的字符串,','分割,例：1,2,3", required = true, paramType = "query", dataType = "String")

	@PostMapping("/del/batch")
	public Result deleteByIds(@RequestParam String ids){
		Result result = biddingContentSettingsService.deleteByIds(ids);
		return result;
	}
}