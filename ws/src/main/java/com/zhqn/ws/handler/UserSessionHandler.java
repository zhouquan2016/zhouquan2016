package com.zhqn.ws.handler;

import com.alibaba.fastjson.JSON;
import com.zhqn.api.user.UserVO;
import com.zhqn.ws.domain.constraints.BaseConstraints;
import com.zhqn.ws.domain.dto.WebsocketResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.Map;
import java.util.Objects;
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

    @Value("${spring.application.name}")
    String serviceName;

    Map<Long, WebSocketSession> userMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        UserVO userVO = (UserVO) session.getAttributes().get(BaseConstraints.WEBSOCKET_ATTR_USER);
        userMap.put(userVO.getId(), session);
        WebsocketResponseDTO<String> dto = new WebsocketResponseDTO<>();
        dto.setServiceName(serviceName);
        dto.setEventName("loginSuccess");
        dto.setData("欢迎你");
        session.sendMessage(new TextMessage(JSON.toJSONString(dto).replaceAll("\"", "\\\"")));
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
        UserVO userVO = (UserVO) session.getAttributes().get(BaseConstraints.WEBSOCKET_ATTR_USER);
        if (Objects.nonNull(userVO)) {
            userMap.remove(userVO.getId());
            log.info("用户{},下线成功", userVO.getId());
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
