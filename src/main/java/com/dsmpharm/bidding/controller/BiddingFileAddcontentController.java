package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingFileAddcontentService;
import com.dsmpharm.bidding.utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/15
 */
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
	@PostMapping
	public Result insert(@RequestParam List<MultipartFile> files){
		Result result=biddingFileAddcontentService.insert(files);
		return result;
	}
}