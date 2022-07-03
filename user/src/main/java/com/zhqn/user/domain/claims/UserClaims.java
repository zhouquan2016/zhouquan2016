package com.zhqn.user.domain.claims;

import com.zhqn.api.utils.DateUtils;
import com.zhqn.user.domain.entity.UserLogin;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


public class UserClaims implements Claims {

    UserLogin userLogin;

    private String issuer;
    private String subject;
    private String audience;

    private Date notBefore;

    private Date issuedAt;

    private String id;

    public UserClaims() {
        this(new UserLogin());
    }

    public UserClaims(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public String getIssuer() {
        return this.issuer;
    }

    @Override
    public Claims setIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    @Override
    public String getSubject() {
        return this.subject;
    }

    @Override
    public Claims setSubject(String sub) {
        this.subject = sub;
        return this;
    }

    @Override
    public String getAudience() {
        return this.audience;
    }

    @Override
    public Claims setAudience(String aud) {
        this.audience = aud;
        return this;
    }

    @Override
    public Date getExpiration() {
        return DateUtils.toDate(userLogin.getLoginExpireTime());
    }

    @Override
    public Claims setExpiration(Date date) {
        if (Objects.nonNull(date)) {
            userLogin.setLoginExpireTime(DateUtils.toLocalDateTime(date));
        }
        return this;
    }

    @Override
    public Date getNotBefore() {
        return this.notBefore;
    }

    @Override
    public Claims setNotBefore(Date nbf) {
        this.notBefore = nbf;
        return this;
    }

    @Override
    public Date getIssuedAt() {
        return this.issuedAt;
    }

    @Override
    public Claims setIssuedAt(Date iat) {
        this.issuedAt = iat;
        return this;
    }

    @Override
    public String getId() {
        return userLogin.getLoginId();
    }

    @Override
    public Claims setId(String jti) {
        this.userLogin.setLoginId(jti);
        return this;
    }

    @Override
    public <T> T get(String claimName, Class<T> requiredType) {
        throw new RuntimeException("not support");
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object o) {
        return false;
    }

    @Override
    public boolean containsValue(Object o) {
        return false;
    }

    @Override
    public Object get(Object o) {
        return null;
    }

    @Nullable
    @Override
    public Object put(String s, Object o) {
        return null;
    }

    @Override
    public Object remove(Object o) {
        return null;
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ?> map) {

    }

    @Override
    public void clear() {

    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return null;
    }

    @NotNull
    @Override
    public Collection<Object> values() {
        return null;
    }

    @NotNull
    @Override
    public Set<Entry<String, Object>> entrySet() {
        return null;
    }
}
