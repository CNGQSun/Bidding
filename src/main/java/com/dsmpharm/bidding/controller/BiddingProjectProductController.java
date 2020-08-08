package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingProjectProduct;
import com.dsmpharm.bidding.service.BiddingProjectProductService;
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
@RequestMapping("/biddingProjectProduct")
public class BiddingProjectProductController {

	@Resource
	private BiddingProjectProductService biddingProjectProductService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody BiddingProjectProduct biddingProjectProduct){
		biddingProjectProductService.insert(biddingProjectProduct);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingProjectProduct> list = biddingProjectProductService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		BiddingProjectProduct biddingProjectProduct =  biddingProjectProductService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingProjectProduct);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingProjectProduct biddingProjectProduct) {
		biddingProjectProduct.setId(id);
		biddingProjectProductService.updateById(biddingProjectProduct);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		biddingProjectProductService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingProjectProduct biddingProjectProduct) {
	List<BiddingProjectProduct> biddingProjectProducts = biddingProjectProductService.list(biddingProjectProduct);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingProjectProducts);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingProjectProduct biddingProjectProduct, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingProjectProduct> biddingProjectProducts = biddingProjectProductService.list(biddingProjectProduct, currentPage, pageSize);
		Integer count = biddingProjectProductService.selectCount(biddingProjectProduct);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingProjectProduct>(count, biddingProjectProducts));
	}
}