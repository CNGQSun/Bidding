package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingProjectBulid;
import com.dsmpharm.bidding.service.BiddingProjectBulidService;
import com.dsmpharm.bidding.utils.PageResult;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/08
 */
@CrossOrigin
@RestController
@RequestMapping("/biddingProjectBulid")
public class BiddingProjectBulidController {
	private static Logger log = LoggerFactory.getLogger(BiddingProjectBulidController.class);

	@Resource
	private BiddingProjectBulidService biddingProjectBulidService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody BiddingProjectBulid biddingProjectBulid){
		biddingProjectBulidService.insert(biddingProjectBulid);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingProjectBulid> list = biddingProjectBulidService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		BiddingProjectBulid biddingProjectBulid =  biddingProjectBulidService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingProjectBulid);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingProjectBulid biddingProjectBulid) {
		biddingProjectBulid.setId(id);
		biddingProjectBulidService.updateById(biddingProjectBulid);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		biddingProjectBulidService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingProjectBulid biddingProjectBulid) {
	List<BiddingProjectBulid> biddingProjectBulids = biddingProjectBulidService.list(biddingProjectBulid);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingProjectBulids);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingProjectBulid biddingProjectBulid, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingProjectBulid> biddingProjectBulids = biddingProjectBulidService.list(biddingProjectBulid, currentPage, pageSize);
		Integer count = biddingProjectBulidService.selectCount(biddingProjectBulid);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingProjectBulid>(count, biddingProjectBulids));
	}
}