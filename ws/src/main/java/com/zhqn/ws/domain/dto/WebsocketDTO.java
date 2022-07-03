package com.zhqn.ws.domain.dto;

import lombok.Data;

@Data
public abstract class WebsocketDTO <T>{

    /**
     * 微服务命
     */
    private String serviceName;

    /**
     * 业务处理名称
     */
    private String eventName;

    T data;

}
