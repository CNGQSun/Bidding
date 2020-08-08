package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingContentBak;
import com.dsmpharm.bidding.service.BiddingContentBakService;
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
@RestController
@RequestMapping("/biddingContentBak")
public class BiddingContentBakController {

	@Resource
	private BiddingContentBakService biddingContentBakService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody BiddingContentBak biddingContentBak){
		biddingContentBakService.insert(biddingContentBak);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingContentBak> list = biddingContentBakService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		BiddingContentBak biddingContentBak =  biddingContentBakService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingContentBak);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingContentBak biddingContentBak) {
		biddingContentBak.setId(id);
		biddingContentBakService.updateById(biddingContentBak);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		biddingContentBakService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingContentBak biddingContentBak) {
	List<BiddingContentBak> biddingContentBaks = biddingContentBakService.list(biddingContentBak);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingContentBaks);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingContentBak biddingContentBak, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingContentBak> biddingContentBaks = biddingContentBakService.list(biddingContentBak, currentPage, pageSize);
		Integer count = biddingContentBakService.selectCount(biddingContentBak);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingContentBak>(count, biddingContentBaks));
	}
}