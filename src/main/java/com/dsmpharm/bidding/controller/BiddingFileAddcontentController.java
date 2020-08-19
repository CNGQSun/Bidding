package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingFileAddcontentService;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/15
 */
@Api(tags = "新增内容设置为文件类型")
@CrossOrigin
@RestController
@RequestMapping("/biddingFileAddcontent")
public class BiddingFileAddcontentController {

	@Resource
	private BiddingFileAddcontentService biddingFileAddcontentService;

	/**
	 * 项目编辑中接收新增的内容设置的文件类型
	 * @param files
	 * @return
	 */
	@ApiOperation(value = "项目编辑中接收新增的内容设置的文件类型")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "files", value = "单个或多个文件均使用此参数", required = true, paramType = "query", dataType = "body"),
	})
	@PostMapping
	public Result insert(@RequestParam List<MultipartFile> files){
		Result result=biddingFileAddcontentService.insert(files);
		return result;
	}
}