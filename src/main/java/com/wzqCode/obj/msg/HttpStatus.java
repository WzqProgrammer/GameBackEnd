package com.wzqCode.obj.msg;

import lombok.Getter;

@Getter
public enum HttpStatus {
    SUCCESS(200, "成功"),
    ERROR(201, "失败"),

    REGISTER_ACCOUNT_FORMAT_ERROR(1001, "账号格式错误：字母、数字、特殊符号组成，账号字数限制为5-20个字符，特殊符号仅限 @$^!~,.*"),
    REGISTER_PASSWORD_FORMAT_ERROR(1002, "密码格式错误：字母、数字、特殊符号组成，密码字数限制为8-16个字符，特殊符号仅限 @$^!~,.*"),
    REGISTER_ACCOUNT_REPEAT_ERROR(1003, "已存在该用户名"),
    LOGIN_ACCOUNT_NOT_EXIST_ERROR(1004, "用户名不存在"),
    LOGIN_PASSWORD_ERROR(1005, "密码错误"),

    UNKNOWN_ERROR(9999, "未知错误");

    private Integer code;
    private String msg;

    HttpStatus(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
