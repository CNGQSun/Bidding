package com.dsmpharm.bidding.controller;

import com.dsmpharm.bidding.service.AppFlowApprovalService;
import com.dsmpharm.bidding.utils.JwtUtil;
import com.dsmpharm.bidding.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "项目审批相关接口")
public class AppFlowApprovalController {

    @Resource
    private AppFlowApprovalService appFlowApprovalService;
    @Resource
    private JwtUtil jwtUtil;

    /**
     * 项目审批
     *
     * @param request
     * @param map
     * @return
     */
    @ApiOperation(value = "审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "approveId", value = "审批ID，list返回数据中已经提供", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "approveResult", value = "审批结果，通过为1，驳回为2", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping("/app")
    public Result update(HttpServletRequest request, @RequestParam Map map) {
        String authorization = request.getHeader("Authorization");
        String userId = jwtUtil.parseJWT(authorization).getId();
        Result result = appFlowApprovalService.approvalById(map);
        return result;
    }
}