package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingProduct;
import com.dsmpharm.bidding.service.BiddingProductService;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
	 * 添加产品 提交
	 * @param biddingProduct
	 * @return
	 */
	@PostMapping("/sub")
	public Result insertSub(@RequestBody BiddingProduct biddingProduct){
		Result result=biddingProductService.insertSub(biddingProduct);
		return result;
	}

	/**
	 * 添加产品 保存
	 * @param biddingProduct
	 * @return
	 */
	@PostMapping("/pro")
	public Result insertPro(@RequestBody BiddingProduct biddingProduct){
		Result result=biddingProductService.insertPro(biddingProduct);
		return result;
	}

	/**
	 * 根据ID查询产品信息
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		Result result =biddingProductService.findById(id);
		return result;
	}

	/**
	 * 根据ID删除产品信息
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		Result result =biddingProductService.delete(id);
		return result;
	}

	/**
	 * 根据IDS批量删除产品信息
	 * @param ids
	 * @return
	 */
	@DeleteMapping
	public Result deleteByIds(@RequestParam(value = "ids") String ids){
		Result result =biddingProductService.deleteIds(ids);
		return result;
	}

	/**
	 * 根据ID更新产品信息
	 * @param id
	 * @param biddingProduct
	 * @return
	 */
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingProduct biddingProduct) {
		biddingProduct.setId(id);
		Result result =biddingProductService.updateById(biddingProduct);
		return result;
	}

	/**
	 * 分页、条件查询全部未删除产品信息
	 */
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody Map map, @PathVariable int currentPage, @PathVariable int pageSize) {
		Result result = biddingProductService.list(map, currentPage, pageSize);
		return result;
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
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingProduct biddingProduct) {
	List<BiddingProduct> biddingProducts = biddingProductService.list(biddingProduct);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingProducts);
	}


}