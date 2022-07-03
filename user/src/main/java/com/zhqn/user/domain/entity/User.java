package com.zhqn.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1619826391962172296L;

    /**
     * 自增
     */
    @TableId
    private Long id;

    /**
     * 登录账号
     */
    private String loginNo;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 登录密码
     */
    private String password;


}