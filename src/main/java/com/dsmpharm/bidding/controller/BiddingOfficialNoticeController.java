package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingOfficialNotice;
import com.dsmpharm.bidding.service.BiddingOfficialNoticeService;
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
@RequestMapping("/biddingOfficialNotice")
public class BiddingOfficialNoticeController {
	private static Logger log = LoggerFactory.getLogger(BiddingOfficialNoticeController.class);

	@Resource
	private BiddingOfficialNoticeService biddingOfficialNoticeService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody BiddingOfficialNotice biddingOfficialNotice){
		biddingOfficialNoticeService.insert(biddingOfficialNotice);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingOfficialNotice> list = biddingOfficialNoticeService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		BiddingOfficialNotice biddingOfficialNotice =  biddingOfficialNoticeService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingOfficialNotice);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingOfficialNotice biddingOfficialNotice) {
		biddingOfficialNotice.setId(id);
		biddingOfficialNoticeService.updateById(biddingOfficialNotice);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		biddingOfficialNoticeService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingOfficialNotice biddingOfficialNotice) {
	List<BiddingOfficialNotice> biddingOfficialNotices = biddingOfficialNoticeService.list(biddingOfficialNotice);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingOfficialNotices);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingOfficialNotice biddingOfficialNotice, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingOfficialNotice> biddingOfficialNotices = biddingOfficialNoticeService.list(biddingOfficialNotice, currentPage, pageSize);
		Integer count = biddingOfficialNoticeService.selectCount(biddingOfficialNotice);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingOfficialNotice>(count, biddingOfficialNotices));
	}
}