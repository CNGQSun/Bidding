package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.AppFlowApprovalService;
import com.dsmpharm.bidding.utils.JwtUtil;
import com.dsmpharm.bidding.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** 
 * <br/>
 * Created by Grant on 2020/08/10
 */
@CrossOrigin
@RestController
@RequestMapping("/appFlowApproval")
public class AppFlowApprovalController {

	@Resource
	private AppFlowApprovalService appFlowApprovalService;
	@Resource
	private JwtUtil jwtUtil;



	/**
	* 审批
	*/
	@PutMapping("/app")
	public Result update(HttpServletRequest request, @RequestParam Map map) {
		String authorization = request.getHeader("Authorization");
		String userId = jwtUtil.parseJWT(authorization).getId();
		Result result=appFlowApprovalService.approvalById(map,userId);
		return result;
	}



}