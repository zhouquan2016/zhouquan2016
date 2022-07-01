package com.zhqn.api.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Copyright (C), 2015-2022,
 * FileName: UserApi
 * Date:     2022/7/1 19:03
 * Description:
 * @author zhouquan3
 */
@FeignClient("user")
public interface UserApi {

    String AUTH_HEAD_NAME = "Auth—Token-String";

    String USER_ID_NAME = "userId";

    /**
     * 验证token，通过则返回用户的基本信息
     * @param token 登录验证信息
     * @return 用户基本信息
     */
    @PostMapping("/api/user/validateToken")
    UserVO validateToken(String token);
}
