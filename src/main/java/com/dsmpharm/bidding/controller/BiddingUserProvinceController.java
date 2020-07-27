package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingUserProvince;
import com.dsmpharm.bidding.service.BiddingUserProvinceService;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/07/27
 */
@RestController
@RequestMapping("/biddingUserProvince")
public class BiddingUserProvinceController {

	@Resource
	private BiddingUserProvinceService biddingUserProvinceService;

	/**
	 * 添加用户省份，点击提交
	 * @param biddingUserProvince
	 * @return
	 */
	@PostMapping("/sub")
	public Result insertSub(@RequestBody BiddingUserProvince biddingUserProvince){
		Result result=biddingUserProvinceService.insertSub(biddingUserProvince);
		return result;
	}

	/**
	 * 添加用户省份，点击保存
	 * @param biddingUserProvince
	 * @return
	 */
	@PostMapping("/pro")
	public Result insertPro(@RequestBody BiddingUserProvince biddingUserProvince){
		Result result=biddingUserProvinceService.insertPro(biddingUserProvince);
		return result;
	}

	/**
	 * 根据ID查询用户省份信息
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		Result result =  biddingUserProvinceService.findById(id);
		return result;
	}

	/**
	 * 根据Id删除用户省份信息
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		Result result =biddingUserProvinceService.deleteById(id);
		return result;
	}

	/**
	 * 根据Id删除多个用户省份信息
	 * @param ids
	 * @return
	 */
	@DeleteMapping
	public Result deleteByIds(@RequestParam(value = "ids") String ids){
		Result result =biddingUserProvinceService.deleteByIds(ids);
		return result;
	}

	/**
	 * 根据id修改用户省份信息
	 * @param id
	 * @param biddingUserProvince
	 * @return
	 */
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingUserProvince biddingUserProvince) {
		biddingUserProvince.setId(id);
		Result result=biddingUserProvinceService.updateById(biddingUserProvince);
		return result;
	}

	/**
	 * 分页、条件查询所有用户省份信息
	 * @param map
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody Map map, @PathVariable int currentPage, @PathVariable int pageSize) {
		Result result = biddingUserProvinceService.list(map, currentPage, pageSize);
		return result;
	}


	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingUserProvince> list = biddingUserProvinceService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}








	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingUserProvince biddingUserProvince) {
	List<BiddingUserProvince> biddingUserProvinces = biddingUserProvinceService.list(biddingUserProvince);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingUserProvinces);
	}


}