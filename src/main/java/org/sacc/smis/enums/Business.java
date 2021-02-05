package org.sacc.smis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by 林夕
 * Date 2021/1/21 20:55
 */

@Getter
@AllArgsConstructor
public enum Business {
    STUDENT_ID_IS_EXIT(1000,"该学号已存在")
    ,
    EMAIL_IS_EXIT(1001,"该邮箱已存在")
    ,
    OLD_PASSWORD_EQUAL_NEW_PASSWORD(1002,"新密码与旧密码相同")
    ,
    OLD_PASSWORD_ERROR(1003,"旧密码错误")
    ,
    USER_IS_NOT_EXIST(1004,"该用户不存在")
    ,
    THE_NUMBER_OF_EMAILS_SENT_TODAY_HAS_REACHED_THE_LIMIT(1005,"今日发送邮件次数已达上限")
    ;

    private final int code;
    private final String message;

}
