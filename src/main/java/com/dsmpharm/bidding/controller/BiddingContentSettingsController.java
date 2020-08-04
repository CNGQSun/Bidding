package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingContentSettingsService;
import com.dsmpharm.bidding.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/08/03
 */
@RestController
@RequestMapping("/biddingContentSettings")
public class BiddingContentSettingsController {

	@Resource
	private BiddingContentSettingsService biddingContentSettingsService;

	/**
	 * 新增内容设置
	 * @param map
	 * @return
	 */
	@PostMapping("/sub")
	public Result insert(@RequestParam Map map){
		Result result=biddingContentSettingsService.insertSet(map);
		return result;
	}

	/**
	 * 根据ID查询内容设置详细信息
	 * @param id
	 * @return
	 */
	@GetMapping()
	public Result findById(@RequestParam String id){
		Result result =  biddingContentSettingsService.findById(id);
		return result;
	}

	/**
	 * 根据ID修改内容设置详细信息
	 * @param map
	 * @return
	 */
	@PostMapping("/update")
	public Result update(@RequestParam Map map) {
		Result result=biddingContentSettingsService.updateById(map);
		return result;
	}

	/**
	 * 分页、条件查询所有内容设置
	 * @param map
	 * @return
	 */
	@PostMapping(value = "/list")
	public Result findSearch(@RequestParam Map map) {
		Result result = biddingContentSettingsService.list(map);
		return result;
	}

	/**
	 * 根据ID删除内容设置信息
	 * @param id
	 * @return
	 */
	@PostMapping("/del")
	public Result deleteById(@RequestParam String id){
		Result result = biddingContentSettingsService.deleteById(id);
		return result;
	}

	/**
	 * 根据IDS批量删除内容设置信息
	 * @param ids
	 * @return
	 */
	@PostMapping("/del/batch")
	public Result deleteByIds(@RequestParam String ids){
		Result result = biddingContentSettingsService.deleteByIds(ids);
		return result;
	}
}