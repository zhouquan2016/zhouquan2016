package com.zhqn.user.domain.query;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotBlank;

@Data
public class LoginQuery {

    @NotBlank(message = "登录账号不能为空")
    private String loginNo;

    @NotBlank(message = "登录密码不能为空")
    private String password;

}
