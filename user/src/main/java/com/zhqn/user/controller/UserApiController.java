package com.zhqn.user.controller;

import com.zhqn.api.user.UserApi;
import com.zhqn.api.user.UserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Copyright (C), 2015-2022,
 * FileName: UserApiController
 * Date:     2022/7/1 19:10
 * Description: 用户api
 * @author zhouquan3
 */
@RestController
public class UserApiController implements UserApi {

    @Override
    @PostMapping("/api/user/validateToken")
    public UserVO validateToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        UserVO userVO = new UserVO();
        userVO.setId(1L);
        userVO.setUserName("张三");
        return userVO;
    }
}
