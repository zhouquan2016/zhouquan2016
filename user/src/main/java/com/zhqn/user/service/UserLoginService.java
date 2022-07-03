package com.zhqn.user.service;

import com.zhqn.user.domain.entity.UserLogin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zq
* @description 针对表【user_login(用户登录记录表)】的数据库操作Service
* @createDate 2022-07-03 21:04:21
*/
public interface UserLoginService extends IService<UserLogin> {

    UserLogin getLatestLogin(Long userId);
}
