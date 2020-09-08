package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingContentTypeService;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/** 
 * <br/>
 * Created by Grant on 2020/08/03
 */
@CrossOrigin
@Api(tags = "内容设置新增中的类型接口")
@RestController
@RequestMapping("/biddingContentType")
public class BiddingContentTypeController {
	private static Logger log = LoggerFactory.getLogger(BiddingContentTypeController.class);

	@Resource
	private BiddingContentTypeService biddingContentTypeService;


	/**
	 * 查询全部内容类型
	 * @return
	 */
	@ApiOperation(value="新增中‘类型’下拉框的接口" )
	@GetMapping
	public Result findAll(){
		Result result=biddingContentTypeService.selectAllNoDel();
		return result;
	}
}