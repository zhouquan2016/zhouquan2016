package com.zhqn.user.controller;

import com.zhqn.api.RestResult;
import com.zhqn.api.user.UserApi;
import com.zhqn.api.user.UserVO;
import com.zhqn.user.service.JwtService;
import com.zhqn.user.service.UserOperateService;
import com.zhqn.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Copyright (C), 2015-2022,
 * FileName: UserApiController
 * Date:     2022/7/1 19:10
 * Description: 用户api
 * @author zhouquan3
 */
@RestController
@RequestMapping("/api/user/")
public class UserApiController implements UserApi {

    @Resource
    UserOperateService userOperateService;


    @Override
    @PostMapping("/validateToken")
    public RestResult<UserVO> validateToken(
            @RequestBody
            String token) {
        return RestResult.getSuccessResult(userOperateService.validateToken(token));
    }

    @Override
    public RestResult<UserVO> getById(Long userId) {
        return RestResult.getSuccessResult(userOperateService.findById(userId));
    }
}
