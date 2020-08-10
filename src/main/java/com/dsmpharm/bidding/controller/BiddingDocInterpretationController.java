package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingDocInterpretation;
import com.dsmpharm.bidding.service.BiddingDocInterpretationService;
import com.dsmpharm.bidding.utils.PageResult;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/08
 */
@CrossOrigin
@RestController
@RequestMapping("/biddingDocInterpretation")
public class BiddingDocInterpretationController {

	@Resource
	private BiddingDocInterpretationService biddingDocInterpretationService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody BiddingDocInterpretation biddingDocInterpretation){
		biddingDocInterpretationService.insert(biddingDocInterpretation);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingDocInterpretation> list = biddingDocInterpretationService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		BiddingDocInterpretation biddingDocInterpretation =  biddingDocInterpretationService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingDocInterpretation);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingDocInterpretation biddingDocInterpretation) {
		biddingDocInterpretation.setId(id);
		biddingDocInterpretationService.updateById(biddingDocInterpretation);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		biddingDocInterpretationService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingDocInterpretation biddingDocInterpretation) {
	List<BiddingDocInterpretation> biddingDocInterpretations = biddingDocInterpretationService.list(biddingDocInterpretation);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingDocInterpretations);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingDocInterpretation biddingDocInterpretation, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingDocInterpretation> biddingDocInterpretations = biddingDocInterpretationService.list(biddingDocInterpretation, currentPage, pageSize);
		Integer count = biddingDocInterpretationService.selectCount(biddingDocInterpretation);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingDocInterpretation>(count, biddingDocInterpretations));
	}
}