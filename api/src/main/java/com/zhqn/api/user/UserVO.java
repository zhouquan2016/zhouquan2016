package com.zhqn.api.user;

import lombok.Data;

/**
 * FileName: UserVO
 * Date:     2022/7/1 19:04
 * Description: 用户基本信息
 * @author zhouquan3
 */
@Data
public class UserVO {

    private Long id;

    private String loginNo;

    private String nickName;

}
