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
    SERVER_ERROR(-100, "系统错误！"),
    NO_JURISDICTION(401, "没有权限！"),
    USER_NOT_FIND(-101, "用户不存在"),
    ILLEGAL_PARAM(-102, "非法参数"),
    SUBJECT_BEENSELECT(-103, "同学你手慢了，课题已被选！！！"),
    SUBJECT_SELECT_FAIL(-104, "选题失败！"),
    SUBJECT_DELECT_FAIL(-105, "删除课题失败！"),
    SUBJECT_NOTEXIST(-106, "课题不存在！"),
    OPINOIN_UPDATE_FAIL(-107, "修改意见失败！"),
    SUBJECT_DIS_FAIL(-108, "课题分配失败！"),
    OPRATION_FAIL(403, "操作失败！"),
    SUBJECT_SEARCH_FAIL(-109, "查询失败！"),
    SERVER_FAIL(-1000, "出错啦，请与管理员联系~"),
    OPRATION_SUCCESS(200, "操作成功！"),
    STUDENT_BEENSELECT(-110, "学生已被选中！"),
    SPECID_NOTNULL(-111, "专业编号不能为空!"),
    STUIDTID_NOTNULL(-112, "学号和教师编号均不能为空！"),
    TEACHER_NOTNULL(-113, "教师编号不存在！"),
    STUDENT_NOT_COMSUB(-114, "该学生还没有确定的课题，因此无法更换指导教师！"),
    FILEPATH_NOTSET(-115, "出错了：tempfilepath参数未配置！"),
    FILE_NOTEXIT(-116, "文件不存在！"),
    SUBJECT_EXCIT(-117, "课题已存在！");

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

