package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingProduct;
import com.dsmpharm.bidding.service.BiddingProductService;
import com.dsmpharm.bidding.utils.PageResult;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
@RestController
@RequestMapping("/biddingProduct")
public class BiddingProductController {

	@Resource
	private BiddingProductService biddingProductService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody BiddingProduct biddingProduct){
		biddingProductService.insert(biddingProduct);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingProduct> list = biddingProductService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		BiddingProduct biddingProduct =  biddingProductService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingProduct);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingProduct biddingProduct) {
		biddingProduct.setId(id);
		biddingProductService.updateById(biddingProduct);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		biddingProductService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingProduct biddingProduct) {
	List<BiddingProduct> biddingProducts = biddingProductService.list(biddingProduct);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingProducts);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingProduct biddingProduct, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingProduct> biddingProducts = biddingProductService.list(biddingProduct, currentPage, pageSize);
		Integer count = biddingProductService.selectCount(biddingProduct);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingProduct>(count, biddingProducts));
	}
}