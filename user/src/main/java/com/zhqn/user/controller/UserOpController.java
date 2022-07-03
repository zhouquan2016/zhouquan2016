package com.zhqn.user.controller;

import com.zhqn.api.RestResult;
import com.zhqn.api.user.UserVO;
import com.zhqn.api.utils.UserUtils;
import com.zhqn.user.domain.query.LoginQuery;
import com.zhqn.user.service.UserOperateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/op/user/")
public class UserOpController {

    @Resource
    UserOperateService userOperateService;

    @PostMapping("/login")
    public RestResult<Void> login(
            @RequestBody
            LoginQuery query,
            HttpServletRequest request, HttpServletResponse response
    ) {
        userOperateService.login(query, request, response);
        return RestResult.getSuccessResult();
    }

    public RestResult<UserVO> details() {
        return RestResult.getSuccessResult(UserUtils.getCurrentUser());
    }

    @GetMapping("/test")
    public String test() {
        return "xxxx";
    }
}
