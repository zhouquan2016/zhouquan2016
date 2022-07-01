package com.zhqn.ws.handler;

import com.zhqn.api.user.UserApi;
import com.zhqn.api.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright (C), 2015-2022,
 * FileName: DefaultHandler
 * Author:   zhouquan3
 * Date:     2022/7/1 18:06
 * Description:
 * @author zhouquan3
 */
@Component
@Slf4j
public class UserSessionHandler implements WebSocketHandler {

    Map<Long, WebSocketSession> userMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        UserVO userVO = (UserVO) session.getAttributes().get(UserApi.USER_ID_NAME);
        userMap.put(userVO.getId(), session);
        session.sendMessage(new TextMessage("欢迎你"));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("receive message:{}", message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        session.sendMessage(new TextMessage("消息处理失败!"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        userMap.remove(session.getAttributes().get(UserApi.USER_ID_NAME));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
