package com.zhqn.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestResult<T> {

    /**
     * 成功标识
     */
    public final static Integer SUCCESS_CODE = 1000;

    /**
     * 需要重新登录
     */
    public final static Integer LOGIN_CODE = 1001;
    /**
     * 业务失败标识
     */
    public final static Integer ERROR_SERVICE_CODE = 2000;

    /**
     * 系统异常标识
     */
    public final static Integer ERROR_SYSTEM_CODE = 2000;

    private Integer code;

    private String msg;

    private T data;

    /**
     * 处理成功
     * @param data 业务数据
     * @return 统一返回格式
     * @param <T> 泛型
     */
    public static <T> RestResult<T> getSuccessResult(T data) {
        return new RestResult<>(SUCCESS_CODE, null, data);
    }

    public static RestResult<Void> getSuccessResult() {
        return new RestResult<>(SUCCESS_CODE, null, null);
    }

    /**
     * 业务异常
     * @param msg 异常描述
     * @return 统一返回格式
     */
    public static RestResult<Void> getServiceError(String msg) {
        return new RestResult<>(ERROR_SERVICE_CODE, msg, null);
    }

    /**
     * 系统异常
     * @return 统一返回格式
     */
    public static RestResult<Void> getSystemError() {
        return new RestResult<>(ERROR_SYSTEM_CODE, "系统异常", null);
    }
}
