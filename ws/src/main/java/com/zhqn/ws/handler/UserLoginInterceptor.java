package com.zhqn.ws.handler;

import com.zhqn.api.RestResult;
import com.zhqn.api.user.UserApi;
import com.zhqn.api.user.UserVO;
import com.zhqn.ws.domain.constraints.BaseConstraints;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Copyright (C), 2015-2022,
 * FileName: UserLoginHandler
 * Date:     2022/7/1 18:12
 * Description:
 * @author zhouquan3
 */
@Component
@Slf4j
public class UserLoginInterceptor implements HandshakeInterceptor {

    @Resource
    UserApi userApi;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.info("--------------request url:{}", request.getURI());
        String token = getToken(request);
        UserVO userVO = validateToken(token);
        if (userVO == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.close();
            return false;
        }
        attributes.put(BaseConstraints.WEBSOCKET_ATTR_USER, userVO);
        return true;
    }

    private UserVO validateToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        UserVO userVO = null;
        try {
            RestResult<UserVO> result = userApi.validateToken(token);
            userApi.checkResult(token, result);
            userVO = result.getData();
        }catch (Exception e) {
            log.info("validateToken exception ", e);
        }
        return userVO;
    }

    private String getToken(ServerHttpRequest request) {
        StringTokenizer stringTokenizer = new StringTokenizer(request.getURI().getRawQuery(), "=");
        while (stringTokenizer.hasMoreTokens()) {
            if (ObjectUtils.equals(stringTokenizer.nextToken().trim(), BaseConstraints.WEBSOCKET_TOKEN_NAME)) {
                if (stringTokenizer.hasMoreTokens()) {
                    return stringTokenizer.nextToken().trim();
                }
            }
        }
        return null;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
