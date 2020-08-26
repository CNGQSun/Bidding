package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingProjectData;
import com.dsmpharm.bidding.service.BiddingProjectDataService;
import com.dsmpharm.bidding.utils.PageResult;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/08
 */
@CrossOrigin
@RestController
@RequestMapping("/biddingProjectData")
public class BiddingProjectDataController {
	private static Logger log = LoggerFactory.getLogger(BiddingProjectDataController.class);

	@Resource
	private BiddingProjectDataService biddingProjectDataService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody BiddingProjectData biddingProjectData){
		biddingProjectDataService.insert(biddingProjectData);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingProjectData> list = biddingProjectDataService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		BiddingProjectData biddingProjectData =  biddingProjectDataService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingProjectData);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingProjectData biddingProjectData) {
		biddingProjectData.setId(id);
		biddingProjectDataService.updateById(biddingProjectData);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		biddingProjectDataService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingProjectData biddingProjectData) {
	List<BiddingProjectData> biddingProjectDatas = biddingProjectDataService.list(biddingProjectData);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingProjectDatas);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingProjectData biddingProjectData, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingProjectData> biddingProjectDatas = biddingProjectDataService.list(biddingProjectData, currentPage, pageSize);
		Integer count = biddingProjectDataService.selectCount(biddingProjectData);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingProjectData>(count, biddingProjectDatas));
	}
}