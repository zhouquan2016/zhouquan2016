package com.zhqn.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.zhqn.api.RestResult;
import com.zhqn.api.exception.ServiceException;
import com.zhqn.api.user.UserApi;
import com.zhqn.api.user.UserVO;
import com.zhqn.api.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.protocol.HTTP;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

@Slf4j
public class AuthFilter implements GlobalFilter, GatewayFilter, Ordered {

    public AuthFilter() {
        System.out.println("----------------------AuthFilter------------------------");
    }
    @Resource
    UserApi userApi;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("----------------filterx----------------------");
        ServerHttpRequest request = exchange.getRequest();
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        if (Objects.isNull(route)
                || ObjectUtils.notEqual("lb", route.getUri().getScheme())
                || !request.getURI().getRawPath().startsWith("/op/")
        ) {
            return chain.filter(exchange);
        }
        HttpCookie cookie = request.getCookies().getFirst(UserUtils.AUTH_COOKIE_NAME);
        RestResult<?> result;
        if (Objects.isNull(cookie) || StringUtils.isBlank(cookie.getValue())) {
            result = new RestResult<>(RestResult.LOGIN_CODE, "未登录", null);
        }else {
            try {
                result = userApi.validateToken(cookie.getValue());
            }catch (Exception e) {
                log.warn("校验token失败", e);
                result = RestResult.getSystemError();
            }
        }
        if (Objects.isNull(result)) {
            result = RestResult.getSystemError();
        }
        if (RestResult.SUCCESS_CODE.equals(result.getCode())) {
            UserVO userVO = (UserVO) result.getData();
            request.getHeaders().set(UserUtils.FORWARD_USER_ID, userVO.getId().toString());
            return chain.filter(exchange);
        }
        String resultString = JSON.toJSONString(result);
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(Mono.just(response.bufferFactory().wrap(resultString.getBytes(StandardCharsets.UTF_8))));
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
