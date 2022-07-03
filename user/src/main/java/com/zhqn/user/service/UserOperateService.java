package com.zhqn.user.service;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.zhqn.api.RestResult;
import com.zhqn.api.exception.ServiceException;
import com.zhqn.api.user.UserVO;
import com.zhqn.api.utils.UserUtils;
import com.zhqn.user.domain.entity.User;
import com.zhqn.user.domain.entity.UserLogin;
import com.zhqn.user.domain.query.LoginQuery;
import com.zhqn.user.props.UserProperties;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class UserOperateService {

    @Resource
    UserService userService;

    @Resource
    UserLoginService userLoginService;

    @Resource
    UserProperties userProperties;


    @Resource
    JwtService jwtService;

    public void login(LoginQuery loginQuery, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getByLoginNo(loginQuery.getLoginNo());
        if (Objects.isNull(user)) {
            throw new ServiceException("用户不存在");
        }
        if (ObjectUtils.notEqual(user.getPassword(), loginQuery.getPassword())) {
            throw new ServiceException("密码不对");
        }
        String terminateName = request.getHeader("User-Agent");
        if (StringUtils.isBlank(terminateName)) {
            throw new ServiceException("设备不能为空");
        }
        UserLogin userLogin = new UserLogin();
        userLogin.setLoginId(IdWorker.get32UUID());
        userLogin.setUserId(user.getId());
        userLogin.setUserHost(request.getRemoteHost());
        userLogin.setForceLogout(Boolean.FALSE);
        userLogin.setTerminateName(request.getHeader("User-Agent"));
        userLogin.setLoginTime(LocalDateTime.now());
        userLogin.setLoginExpireTime(userLogin.getLoginTime().plusHours(userProperties.getLoginTokenExpireHours()));
        userLoginService.save(userLogin);

        String token = jwtService.createToken(userLogin);
        Cookie cookie = new Cookie(UserUtils.AUTH_COOKIE_NAME, token);
        cookie.setMaxAge((int) Duration.ofHours(userProperties.getLoginTokenExpireHours()).getSeconds());
        response.addCookie(cookie);
    }

    private void toLogin(String error) {
        throw new ServiceException(RestResult.LOGIN_CODE, error);
    }

    public UserVO validateToken(String token) {
        UserLogin userLogin = jwtService.validateToken(token);

        if (Objects.isNull(userLogin)) {
            toLogin("鉴权失败");
        }
        if (Objects.isNull(userLogin.getUserId())
                || StringUtils.isBlank(userLogin.getLoginId())
                || StringUtils.isBlank(userLogin.getPassword())
                || StringUtils.isBlank(userLogin.getUserHost())
        ) {
            toLogin("用户登录信息缺失");
        }
        UserLogin login = userLoginService.getById(userLogin.getId());
        if (Objects.isNull(login)) {
            toLogin("用户未登录");
        }
        if (ObjectUtils.notEqual(login.getLoginId(), userLogin.getLoginId())
                || ObjectUtils.notEqual(login.getUserId(), userLogin.getUserId())
        ) {
            toLogin("登录非法");
        }
        if (ObjectUtils.notEqual(login.getPassword(), userLogin.getPassword())) {
            toLogin("密码不对");
        }
        if (ObjectUtils.notEqual(login.getUserHost(), userLogin.getUserHost())
                || ObjectUtils.notEqual(login.getTerminateName(), userLogin.getTerminateName())
        ) {
            toLogin("环境已发生变化");
        }
        if (Boolean.TRUE.equals(login.getForceLogout())) {
            toLogin("管理员已强制下线");
        }
        User user = userService.findById(login.getUserId());
        if (Objects.isNull(user)) {
            toLogin("用户不存在");
        }
        if (ObjectUtils.notEqual(user.getPassword(), userLogin.getPassword())) {
            toLogin("密码已发生改变");
        }
        return convert(user);
    }

    public UserVO findById(Long userId) {
        return convert(userService.findById(userId));
    }

    private UserVO convert(User user) {
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setLoginNo(user.getLoginNo());
        userVO.setNickName(user.getNickName());
        return userVO;
    }
}
