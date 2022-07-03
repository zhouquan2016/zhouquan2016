package com.zhqn.user.service;

import com.zhqn.user.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zq
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2022-07-03 21:40:06
*/
public interface UserService extends IService<User> {

    User getByLoginNo(String loginNo);

    User findById(Long userId);
}
