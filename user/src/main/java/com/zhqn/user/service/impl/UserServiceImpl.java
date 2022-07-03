package com.zhqn.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhqn.user.domain.entity.User;
import com.zhqn.user.service.UserService;
import com.zhqn.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author zq
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2022-07-03 21:40:06
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public User getByLoginNo(String loginNo) {
        return getOne(new QueryWrapper<User>().lambda().eq(User::getLoginNo, loginNo));
    }

    @Override
    public User findById(Long userId) {
        return getById(userId);
    }
}




