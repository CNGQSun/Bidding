package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingProject;
import com.dsmpharm.bidding.service.BiddingProjectService;
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
@RestController
@RequestMapping("/biddingProject")
public class BiddingProjectController {

	@Resource
	private BiddingProjectService biddingProjectService;

	/**
	* 添加
	*/
	@PostMapping
	public Result insert(@RequestBody BiddingProject biddingProject){
		biddingProjectService.insert(biddingProject);
		return new Result<>(true, StatusCode.OK, "保存成功");
	}

	/**
	* 查询全部
	*/
	@GetMapping
	public Result findAll(){
		List<BiddingProject> list = biddingProjectService.selectAll();
		return new Result<>(true, StatusCode.OK, "查询成功", list);
	}

	/**
	* 根据ID查询
	*/
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		BiddingProject biddingProject =  biddingProjectService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingProject);
	}

	/**
	* 更新
	*/
	@PutMapping(value = "/{id}")
	public Result update(@PathVariable String id, @RequestBody BiddingProject biddingProject) {
		biddingProject.setId(id);
		biddingProjectService.updateById(biddingProject);
		return new Result<>(true, StatusCode.OK, "修改成功");
	}


	/**
	* 删除
	*/
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable String id){
		biddingProjectService.delete(id);
		return new Result<>(true, StatusCode.OK, "删除成功");
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestBody BiddingProject biddingProject) {
	List<BiddingProject> biddingProjects = biddingProjectService.list(biddingProject);
		return new Result<>(true, StatusCode.OK, "查询成功", biddingProjects);
	}

	/**
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list/{currentPage}/{pageSize}")
	public Result findSearch(@RequestBody BiddingProject biddingProject, @PathVariable int currentPage, @PathVariable int pageSize) {
		List<BiddingProject> biddingProjects = biddingProjectService.list(biddingProject, currentPage, pageSize);
		Integer count = biddingProjectService.selectCount(biddingProject);
		return new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingProject>(count, biddingProjects));
	}
}