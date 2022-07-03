package com.zhqn.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户登录记录表
 * @TableName user_login
 */
@TableName(value ="user_login")
@Data
public class UserLogin implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = -4710337791195490790L;

    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 登录时的密码
     */
    private String password;
    /**
     * 登录id
     */
    private String loginId;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 登录过期时间
     */
    private LocalDateTime loginExpireTime;

    /**
     * 登录终端名称
     */
    private String terminateName;

    /**
     * 用户id
     */
    private String userHost;

    /**
     * 是否强制下线
     */
    private Boolean forceLogout;




}