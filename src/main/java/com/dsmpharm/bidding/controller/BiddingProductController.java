package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingProduct;
import com.dsmpharm.bidding.service.BiddingProductService;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
	@ApiOperation(value="添加产品 提交" )
	@ApiImplicitParam(name = "biddingProduct", value = "产品实体（只需提供页面输入的参数）", required = true, paramType = "body", dataType = "BiddingProduct")

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
	@ApiOperation(value="添加产品 保存" )
	@ApiImplicitParam(name = "biddingProduct", value = "产品实体（只需提供页面输入的参数）", required = true, paramType = "body", dataType = "BiddingProduct")

	@PostMapping("/pre")
	public Result insertPro(@RequestBody BiddingProduct biddingProduct){
		Result result=biddingProductService.insertPro(biddingProduct);
		return result;
	}

	/**
	 * 根据ID查询产品信息
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据ID查询产品信息" )
	@ApiImplicitParam(name = "id", value = "id作为拼接路径", required = true, paramType = "path", dataType = "String")

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
	@ApiOperation(value="根据ID删除产品信息" )
	@ApiImplicitParam(name = "id", value = "id作为拼接路径", required = true, paramType = "path", dataType = "String")

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
	@ApiOperation(value="根据IDS批量删除产品信息" )
	@ApiImplicitParam(name = "ids", value = "ids为id组成的字符串,','分割,例：1,2,3", required = true, paramType = "query", dataType = "String")

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
	@ApiOperation(value="根据ID更新产品信息" )
	@ApiImplicitParam(name = "id", value = "id作为拼接路径", required = true, paramType = "path", dataType = "String")

	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingProduct biddingProduct) {
		biddingProduct.setId(id);
		Result result =biddingProductService.updateById(biddingProduct);
		return result;
	}

	/**
	 * 分页、条件查询全部未删除产品信息
	 */
	@ApiOperation(value="分页、条件查询全部未删除产品信息" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "currentPage", value = "当前页", required = true, paramType = "path", dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "每页展示条数", required = true, paramType = "path", dataType = "int"),
			@ApiImplicitParam(name = "map", value = "查询参数（name：String）", required = true, paramType = "body", dataType = "Map")
	})


	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody Map map, @PathVariable int currentPage, @PathVariable int pageSize) {
		Result result = biddingProductService.list(map, currentPage, pageSize);
		return result;
	}

	///**
	//* 查询全部
	//*/
	//@GetMapping
	//public Result findAll(){
	//	List<BiddingProduct> list = biddingProductService.selectAll();
	//	return new Result<>(true, StatusCode.OK, "查询成功", list);
	//}
	//
	//
	//
	//
	//
	//
	//
	//
	///**
	//* 条件查询，无分页
	//*/
	//@PostMapping(value = "/list")
	//public Result findSearch(@RequestBody BiddingProduct biddingProduct) {
	//List<BiddingProduct> biddingProducts = biddingProductService.list(biddingProduct);
	//	return new Result<>(true, StatusCode.OK, "查询成功", biddingProducts);
	//}
}