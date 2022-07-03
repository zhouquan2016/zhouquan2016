package com.zhqn.api.user;

import com.zhqn.api.RestResult;

import java.util.Objects;

public interface BaseApi {

    String name();

    default void checkResult(Object request, RestResult<?> result) {
        if (Objects.isNull(result)) {
            throw new SecurityException(name() + "返回为空");
        }
        if (Objects.isNull(result.getCode())) {
            throw new SecurityException(name() + "返回code为空");
        }
        throw new SecurityException(name() + ":" + result.getMsg());
    }
}
