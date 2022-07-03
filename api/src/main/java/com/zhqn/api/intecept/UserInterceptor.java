package com.zhqn.api.intecept;

import com.zhqn.api.RestResult;
import com.zhqn.api.user.UserApi;
import com.zhqn.api.user.UserVO;
import com.zhqn.api.utils.UserUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UserInterceptor implements HandlerInterceptor {

    @Resource
    UserApi userApi;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getServletPath().startsWith("/op/")) {
            setUser(request.getHeader(UserUtils.FORWARD_USER_ID));
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void setUser(String userId) {
        RestResult<UserVO> result = userApi.getById(Long.valueOf(userId));
        userApi.checkResult(userId, result);
        UserUtils.setCurrentUser(result.getData());
    }
}
