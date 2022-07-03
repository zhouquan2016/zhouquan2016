package com.zhqn.user.service;

import com.alibaba.fastjson.JSON;
import com.zhqn.api.RestResult;
import com.zhqn.api.exception.ServiceException;
import com.zhqn.api.utils.DateUtils;
import com.zhqn.user.domain.entity.UserLogin;
import com.zhqn.user.props.UserProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.Key;
import java.util.Date;
import java.util.Objects;

@Component
@Slf4j
public class JwtService {

    @Resource
    UserProperties userProperties;

    private String issuer = "zhqn.com";

    private Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(userProperties.getLoginTokenSecret()));
    }

    public String createToken(UserLogin userLogin) {
        DefaultClaims claims = new DefaultClaims();
        claims.setExpiration(DateUtils.toDate(userLogin.getLoginTime()))
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .putAll(JSON.parseObject(JSON.toJSONString(userLogin)));
        return Jwts.builder().signWith(getKey())
                .setClaims(claims)
                .compact()
                ;
    }


    private void toLogin(String error) {
        throw new ServiceException(RestResult.LOGIN_CODE, error);
    }
    public UserLogin validateToken(String token) {
        Jwt<Header, Claims> jwt = null;
        try {
            jwt = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJwt(token);
        } catch (ExpiredJwtException e) {
            toLogin("密码过期");
        } catch (Exception e) {
            log.debug("jwt解析失败", e);
            toLogin("密码不对");
        }
        if (Objects.isNull(jwt)) {
            toLogin("鉴权失败");
        }
        UserLogin userLogin = null;
        try {
            userLogin = JSON.parseObject(JSON.toJSONString(jwt.getBody()), UserLogin.class);
        } catch (Exception e) {
            log.debug("claim===>UserLogin失败", e);
        }
        return userLogin;
    }
}
