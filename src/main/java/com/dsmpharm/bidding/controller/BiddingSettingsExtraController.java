package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingSettingsExtra;
import com.dsmpharm.bidding.service.BiddingSettingsExtraService;
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
@RequestMapping("/biddingSettingsExtra")
public class BiddingSettingsExtraController {

	@Resource
	private BiddingSettingsExtraService biddingSettingsExtraService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody BiddingSettingsExtra biddingSettingsExtra){
		biddingSettingsExtraService.insert(biddingSettingsExtra);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingSettingsExtra> list = biddingSettingsExtraService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		BiddingSettingsExtra biddingSettingsExtra =  biddingSettingsExtraService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingSettingsExtra);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingSettingsExtra biddingSettingsExtra) {
		biddingSettingsExtra.setId(id);
		biddingSettingsExtraService.updateById(biddingSettingsExtra);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		biddingSettingsExtraService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingSettingsExtra biddingSettingsExtra) {
	List<BiddingSettingsExtra> biddingSettingsExtras = biddingSettingsExtraService.list(biddingSettingsExtra);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingSettingsExtras);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingSettingsExtra biddingSettingsExtra, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingSettingsExtra> biddingSettingsExtras = biddingSettingsExtraService.list(biddingSettingsExtra, currentPage, pageSize);
		Integer count = biddingSettingsExtraService.selectCount(biddingSettingsExtra);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingSettingsExtra>(count, biddingSettingsExtras));
	}
}