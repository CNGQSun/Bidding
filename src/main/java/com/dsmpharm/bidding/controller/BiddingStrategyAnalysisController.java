package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingStrategyAnalysis;
import com.dsmpharm.bidding.service.BiddingStrategyAnalysisService;
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
@RequestMapping("/biddingStrategyAnalysis")
public class BiddingStrategyAnalysisController {
	private static Logger log = LoggerFactory.getLogger(BiddingStrategyAnalysisController.class);

	@Resource
	private BiddingStrategyAnalysisService biddingStrategyAnalysisService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody BiddingStrategyAnalysis biddingStrategyAnalysis){
		biddingStrategyAnalysisService.insert(biddingStrategyAnalysis);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingStrategyAnalysis> list = biddingStrategyAnalysisService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		BiddingStrategyAnalysis biddingStrategyAnalysis =  biddingStrategyAnalysisService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingStrategyAnalysis);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingStrategyAnalysis biddingStrategyAnalysis) {
		biddingStrategyAnalysis.setId(id);
		biddingStrategyAnalysisService.updateById(biddingStrategyAnalysis);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		biddingStrategyAnalysisService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingStrategyAnalysis biddingStrategyAnalysis) {
	List<BiddingStrategyAnalysis> biddingStrategyAnalysiss = biddingStrategyAnalysisService.list(biddingStrategyAnalysis);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingStrategyAnalysiss);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingStrategyAnalysis biddingStrategyAnalysis, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingStrategyAnalysis> biddingStrategyAnalysiss = biddingStrategyAnalysisService.list(biddingStrategyAnalysis, currentPage, pageSize);
		Integer count = biddingStrategyAnalysisService.selectCount(biddingStrategyAnalysis);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingStrategyAnalysis>(count, biddingStrategyAnalysiss));
	}
}