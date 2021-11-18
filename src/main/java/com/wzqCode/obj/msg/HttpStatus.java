package com.wzqCode.obj.msg;

import lombok.Getter;

@Getter
public enum HttpStatus {
    SUCCESS(200, "成功"),
    ERROR(201, "失败"),

    TOKEN_ERROR(202, "token错误"),

    REGISTER_ACCOUNT_FORMAT_ERROR(1001, "账号格式错误：字母、数字、特殊符号组成，账号字数限制为5-20个字符，特殊符号仅限 @$^!~,.*"),
    REGISTER_PASSWORD_FORMAT_ERROR(1002, "密码格式错误：字母、数字、特殊符号组成，密码字数限制为8-16个字符，特殊符号仅限 @$^!~,.*"),
    REGISTER_ACCOUNT_REPEAT_ERROR(1003, "已存在该用户名"),
    LOGIN_ACCOUNT_NOT_EXIST_ERROR(1004, "用户名不存在"),
    LOGIN_PASSWORD_ERROR(1005, "密码错误"),

    PLAYER_CREATE_ERROR(1101, "角色信息创建错误"),

    HERO_MODULE_NOT_FOUNT_ERROR(1201, "英雄模板未找到错误"),
    HERO_CREATE_ERROR(1202, "英雄创建错误"),
    HERO_NOT_FOUNT_ERROR(1203, "英雄未找到错误"),
    HERO_MAX_LV_ERROR(1204, "英雄已达最大等级"),
    HERO_MAX_STAR_ERROR(1205, "英雄已达最大星级"),
    HERO_STAR_UP_MATERIAL_ERROR(1206, "英雄升星所需英雄材料不符合"),
    HERO_DELETE_ERROR(1207, "英雄数据删除错误"),

    UNKNOWN_ERROR(9999, "未知错误");

    private Integer code;
    private String msg;

    HttpStatus(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
