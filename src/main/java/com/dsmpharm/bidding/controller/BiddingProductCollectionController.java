package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingProductCollection;
import com.dsmpharm.bidding.service.BiddingProductCollectionService;
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
@RequestMapping("/biddingProductCollection")
public class BiddingProductCollectionController {

	@Resource
	private BiddingProductCollectionService biddingProductCollectionService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody BiddingProductCollection biddingProductCollection){
		biddingProductCollectionService.insert(biddingProductCollection);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingProductCollection> list = biddingProductCollectionService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		BiddingProductCollection biddingProductCollection =  biddingProductCollectionService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingProductCollection);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingProductCollection biddingProductCollection) {
		biddingProductCollection.setId(id);
		biddingProductCollectionService.updateById(biddingProductCollection);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		biddingProductCollectionService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingProductCollection biddingProductCollection) {
	List<BiddingProductCollection> biddingProductCollections = biddingProductCollectionService.list(biddingProductCollection);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingProductCollections);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingProductCollection biddingProductCollection, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingProductCollection> biddingProductCollections = biddingProductCollectionService.list(biddingProductCollection, currentPage, pageSize);
		Integer count = biddingProductCollectionService.selectCount(biddingProductCollection);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingProductCollection>(count, biddingProductCollections));
	}
}