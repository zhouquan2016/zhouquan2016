package com.zhqn.api.config;

import com.zhqn.api.RestResult;
import com.zhqn.api.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public RestResult<Void> serviceException(ServiceException e) {
        log.warn("业务处理失败", e);
        return new RestResult<>(e.getCode(), e.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public RestResult<Void> exception(Exception e) {
        log.warn("发生未知错误", e);
        return RestResult.getSystemError();
    }
}
