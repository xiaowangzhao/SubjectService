package com.subject.Exception;

/**
 * @author liangqin
 * @date 2019/5/7 17:36
 */

/**
 * 继承RuntimeException
 * 重新定义一个构造方法来定义自己的错误信息
 */
public class DescribeException extends RuntimeException{

    private Integer code;

    /**
     * 继承exception,加入错误状态值
     * @param exceptionEnum
     */
    public DescribeException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }

    /**
     * 自定义错误信息
     * @param msg
     * @param code
     */
    public DescribeException(String msg, Integer code) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
