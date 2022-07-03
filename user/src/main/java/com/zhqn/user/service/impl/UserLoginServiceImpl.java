package com.zhqn.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhqn.user.domain.entity.User;
import com.zhqn.user.domain.entity.UserLogin;
import com.zhqn.user.service.UserLoginService;
import com.zhqn.user.mapper.UserLoginMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
* @author zq
* @description 针对表【user_login(用户登录记录表)】的数据库操作Service实现
* @createDate 2022-07-03 21:04:21
*/
@Service
public class UserLoginServiceImpl extends ServiceImpl<UserLoginMapper, UserLogin>
    implements UserLoginService{

    @Override
    public UserLogin getLatestLogin(Long userId) {
        if (Objects.isNull(userId)) {
            return null;
        }
        return getOne(new QueryWrapper<UserLogin>()
                .lambda().eq(UserLogin::getUserId, userId)
                .orderByDesc(UserLogin::getLoginId)
                .last("limit 1")
        );
    }
}




