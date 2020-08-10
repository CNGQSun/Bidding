package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.pojo.BiddingProject;
import com.dsmpharm.bidding.service.BiddingProjectService;
import com.dsmpharm.bidding.utils.JwtUtil;
import com.dsmpharm.bidding.utils.PageResult;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/08/08
 */
@CrossOrigin
@RestController
@RequestMapping("/biddingProject")
public class BiddingProjectController {

	@Resource
	private BiddingProjectService biddingProjectService;

	@Resource
	private JwtUtil jwtUtil;

	/**
	 * 点击新增 加载内容设置
	 * @param projectPhaseId
	 * @return
	 */
	@GetMapping(value = "/content")
	public Result findById(@RequestParam String projectPhaseId){
		Result result =  biddingProjectService.findContent(projectPhaseId);
		return result;
	}

	/**
	 * 立项
	 * @param request
	 * @param map
	 * @param addContent
	 * @return
	 */
	@PostMapping("/build")
	public Result insert(HttpServletRequest request, @RequestParam Map map,@RequestParam(value = "addContent") List<List<String>> addContent){
		MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
		Map<String, MultipartFile> fileMap = params.getFileMap();
		String authorization = request.getHeader("Authorization");
		String userId = jwtUtil.parseJWT(authorization).getId();
		Result result=biddingProjectService.insert(map,userId,fileMap,addContent);
		return result;
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