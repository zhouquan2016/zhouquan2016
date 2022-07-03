package com.zhqn.ws.domain.dto;

import lombok.Data;

/**
 * 返回给前端的消息实体
 */
@Data
public class WebsocketResponseDTO<T> extends WebsocketDTO <T>{

    /**
     * 消息id
     */
    private String messageId;

    /**
     * 时间戳
     */
    private String timestamp;
}
