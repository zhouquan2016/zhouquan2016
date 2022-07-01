package com.zhqn.ws.handler;

import com.zhqn.api.user.UserApi;
import com.zhqn.api.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Copyright (C), 2015-2022,
 * FileName: UserLoginHandler
 * Date:     2022/7/1 18:12
 * Description:
 * @author zhouquan3
 */
@Component
@Slf4j
public class UserLoginHandler implements HandshakeHandler {

    @Resource
    UserApi userApi;

    @Override
    public boolean doHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws HandshakeFailureException {
        List<String> headValueList = request.getHeaders().get(UserApi.AUTH_HEAD_NAME);
        if (CollectionUtils.isEmpty(headValueList)) {
            log.debug("{}为空", UserApi.AUTH_HEAD_NAME);
            return false;
        }
        String token = headValueList.get(0);
        if (StringUtils.isEmpty(token)) {
            log.debug("{}的token为空", UserApi.AUTH_HEAD_NAME);
            return false;
        }
        UserVO userVO = null;
        try {
            userApi.validateToken(token);
        }catch (Exception e) {
            log.debug("验证用户token:{},失败", token, e);
        }
        if (Objects.isNull(userVO)) {
            log.debug("token:{}对应的用户未找到", token);
            return false;
        }
        attributes.put(UserApi.USER_ID_NAME, userVO);
        return true;
    }
}
