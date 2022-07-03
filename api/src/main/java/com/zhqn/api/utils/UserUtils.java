package com.zhqn.api.utils;

import com.zhqn.api.user.UserVO;

public class UserUtils {


    public static String FORWARD_USER_ID = "FORWARD_USER_ID";

    public static String AUTH_COOKIE_NAME = "login_token";

    static ThreadLocal<UserVO> userThreadLocal = new InheritableThreadLocal<>();

    public static void setCurrentUser(UserVO userVO) {
        userThreadLocal.set(userVO);
    }

    public static UserVO getCurrentUser() {
        return userThreadLocal.get();
    }
}
