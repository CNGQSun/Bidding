package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingProductService;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/07/23
 */
@CrossOrigin
@RestController
@RequestMapping("/biddingProduct")
@Api(tags = "产品主数据相关接口")
public class BiddingProductController {

	@Value("${upload.localtion.product}")
	private String uploadLocation;//上传文件保存的本地目录，使用@Value获取全局配置文件中配置的属性值
	private static Logger log = LoggerFactory.getLogger(BiddingProductController.class);
	@Resource
	private BiddingProductService biddingProductService;

	/**
	 * 添加产品 提交
	 * @param map
	 * @return
	 */
	@ApiOperation(value="添加产品 提交" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "产品名称", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "code", value = "产品code", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "commonName", value = "通用名", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "standards", value = "规格", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "enName", value = "英文名", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "dosageForm", value = "剂型", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping("/sub")
	public Result insertSub(@RequestParam Map map){
		Result result=biddingProductService.insertSub(map);
		return result;
	}

	/**
	 * 添加产品 保存
	 * @param map
	 * @return
	 */
	@ApiOperation(value="添加产品 保存" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "产品名称", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "code", value = "产品code", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "commonName", value = "通用名", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "standards", value = "规格", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "enName", value = "英文名", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "dosageForm", value = "剂型", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping("/pre")
	public Result insertPro(@RequestParam Map map){
		Result result=biddingProductService.insertPro(map);
		return result;
	}

	/**
	 * 根据ID查询产品信息
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据ID查询产品信息" )
	@ApiImplicitParam(name = "id", value = "产品ID", required = true, paramType = "query", dataType = "String")
	@GetMapping
	public Result findById(@RequestParam String id){
		Result result =biddingProductService.findById(id);
		return result;
	}

	/**
	 * 根据ID删除产品信息
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据ID删除产品信息" )
	@ApiImplicitParam(name = "id", value = "产品ID", required = true, paramType = "query", dataType = "String")

	@PostMapping("/del")
	public Result deleteById(@RequestParam String id){
		Result result =biddingProductService.delete(id);
		return result;
	}

	/**
	 * 根据IDS批量删除产品信息
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="根据IDS批量删除产品信息" )
	@ApiImplicitParam(name = "ids", value = "ids为产品id组成的字符串,','分割,例：1,2,3", required = true, paramType = "query", dataType = "String")

	@PostMapping("/del/batch")
	public Result deleteByIds(@RequestParam(value = "ids") String ids){
		Result result =biddingProductService.deleteIds(ids);
		return result;
	}

	/**
	 * 根据ID更新产品信息
	 * @param map
	 * @return
	 */
	@ApiOperation(value="根据ID更新产品信息" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "产品ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "name", value = "产品名称", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "code", value = "产品code", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "commonName", value = "通用名", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "standards", value = "规格", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "enName", value = "英文名", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "dosageForm", value = "剂型", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping("/update")
	public Result update(@RequestParam Map map) {
		Result result =biddingProductService.updateById(map);
		return result;
	}

	/**
	 * 分页、条件查询全部未删除产品信息
	 */
	@ApiOperation(value="分页、条件查询全部未删除产品信息" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "currentPage", value = "当前页", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "pageSize", value = "每页展示条数", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "name", value = "产品名", required = true, paramType = "query", dataType = "String"),
	})
	@PostMapping(value = "/list")
	public Result findSearch(@RequestParam Map map) {
		Result result = biddingProductService.list(map);
		return result;
	}

	/**
	 * 测试上传
	 * @param file
	 * @return
	 */
	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return "上传失败，请选择文件";
		}
		String fileName = file.getOriginalFilename();
		File filePath = new File(uploadLocation, fileName);
		try {
			file.transferTo(filePath);
			log.info("上传成功");
			return "上传成功";
		} catch (IOException e) {
			log.error(e.toString(), e);
		}
		return "上传失败！";
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