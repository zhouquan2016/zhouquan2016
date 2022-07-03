package com.zhqn.api.exception;

import com.zhqn.api.RestResult;
import lombok.Data;

@Data
public class ServiceException extends RuntimeException {

    int code;
    public ServiceException(String msg) {
        super(msg);
        code = RestResult.ERROR_SERVICE_CODE;
    }

    public ServiceException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
