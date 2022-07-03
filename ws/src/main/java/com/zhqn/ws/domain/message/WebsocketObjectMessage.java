package com.zhqn.ws.domain.message;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.web.socket.WebSocketMessage;

@Data
public class WebsocketObjectMessage implements WebSocketMessage {

    String payloadString;

    public WebsocketObjectMessage(Object payload) {
        payloadString = JSON.toJSONString(payload);
    }
    @Override
    public Object getPayload() {
        return payloadString;
    }

    @Override
    public int getPayloadLength() {
        return payloadString.length();
    }

    @Override
    public boolean isLast() {
        return true;
    }
}