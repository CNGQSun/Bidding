package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingPhaseFile;
import com.dsmpharm.bidding.service.BiddingPhaseFileService;
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
@RequestMapping("/biddingPhaseFile")
public class BiddingPhaseFileController {

	@Resource
	private BiddingPhaseFileService biddingPhaseFileService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody BiddingPhaseFile biddingPhaseFile){
		biddingPhaseFileService.insert(biddingPhaseFile);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingPhaseFile> list = biddingPhaseFileService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		BiddingPhaseFile biddingPhaseFile =  biddingPhaseFileService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingPhaseFile);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingPhaseFile biddingPhaseFile) {
		biddingPhaseFile.setId(id);
		biddingPhaseFileService.updateById(biddingPhaseFile);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		biddingPhaseFileService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingPhaseFile biddingPhaseFile) {
	List<BiddingPhaseFile> biddingPhaseFiles = biddingPhaseFileService.list(biddingPhaseFile);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingPhaseFiles);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingPhaseFile biddingPhaseFile, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingPhaseFile> biddingPhaseFiles = biddingPhaseFileService.list(biddingPhaseFile, currentPage, pageSize);
		Integer count = biddingPhaseFileService.selectCount(biddingPhaseFile);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingPhaseFile>(count, biddingPhaseFiles));
	}
}