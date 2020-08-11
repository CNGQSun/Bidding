package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.BiddingProjectService;
import com.dsmpharm.bidding.utils.JwtUtil;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "项目管理相关接口")
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
	@ApiOperation(value="点击新增，调用此接口，加载内容设置" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
	})
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
	@ApiOperation(value="提交立项内容" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "addContent", value = "单独的内容设置，格式{{name,contentTypeId,isNull,value},{}}", required = true, paramType = "query", dataType = "body"),
			@ApiImplicitParam(name = "projectPhaseId", value = "阶段ID，七个阶段对应七个ID：1,2,3,4,5,6,7", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "docPublicTime", value = "文件发布时间", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "typeId", value = "项目类型ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "name", value = "项目名称", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "source", value = "项目来源", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "provinceId", value = "省份ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "cityId", value = "城市ID", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "productId", value = "产品ID，多个ID拼接：1,2,3", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "fileFormal", value = "正式稿文件(文件或字符串)", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "fileAsk", value = "征求稿文件(文件或字符串)", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "docInterTime", value = "文件解读时间", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "submitTime", value = "递交/填报资料时间", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "publicTime", value = "公示期", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "appealTime", value = "申诉期", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "noticeTime", value = "公告期", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "proLabel", value = "项目标签", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "suggestion", value = "意见", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "goStatus", value = "阶段状态", required = true, paramType = "query", dataType = "String"),
	})
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
	* 条件查询，无分页
	*/
	@PostMapping(value = "/list")
	public Result findSearch(@RequestParam Map map) {
		Result result = biddingProjectService.list(map);
		return result;
		//new Result<>(true, StatusCode.OK, "查询成功", new PageResult<BiddingProject>(count, biddingProjects));
	}

	///**
	//* 查询全部
	//*/
	//@GetMapping
	//public Result findAll(){
	//	List<BiddingProject> list = biddingProjectService.selectAll();
	//	return new Result<>(true, StatusCode.OK, "查询成功", list);
	//}
	//
	//
	//
	///**
	//* 更新
	//*/
	//@PutMapping(value = "/{id}")
	//public Result update(@PathVariable String id, @RequestBody BiddingProject biddingProject) {
	//	biddingProject.setId(id);
	//	biddingProjectService.updateById(biddingProject);
	//	return new Result<>(true, StatusCode.OK, "修改成功");
	//}
	//
	//
	///**
	//* 删除
	//*/
	//@DeleteMapping(value = "/{id}")
	//public Result deleteById(@PathVariable String id){
	//	biddingProjectService.delete(id);
	//	return new Result<>(true, StatusCode.OK, "删除成功");
	//}
	//
	///**
	//* 条件查询，无分页
	//*/
	//@PostMapping(value = "/list")
	//public Result findSearch(@RequestBody BiddingProject biddingProject) {
	//List<BiddingProject> biddingProjects = biddingProjectService.list(biddingProject);
	//	return new Result<>(true, StatusCode.OK, "查询成功", biddingProjects);
	//}
	//

}