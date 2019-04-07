package com.subject.dto;

/**
 * @author liangqin
 * @date 2019/3/22 13:30
 *
 * 封装JSON对象，所有返回结果都用它
 * @author lq
 *
 * @param <T>
 */
public class Result<T> {


    private T data; //成功时返回的数据

    private String errorMsg;//错误信息

    private int status;  // 状态值：200 极为成功，其他数值代表失败


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
