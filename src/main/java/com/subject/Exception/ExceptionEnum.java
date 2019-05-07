package com.subject.Exception;

/**
 * @author liangqin
 * @date 2019/5/7 16:52
 */


/**
 * 错误枚举类，记录一些已知的错误信息
 */
public enum ExceptionEnum {
    UNKNOW_ERROR(-1,"未知错误"),
    NO_JURISDICTION(401, "没有权限！"),
    USER_NOT_FIND(-101, "用户不存在"),
    ILLEGAL_PARAM(-102, "非法参数");

    private Integer code;  //状态码

    private String msg;    //错误信息

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

