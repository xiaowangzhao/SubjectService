package com.subject.util;

import com.subject.dto.Result;

/**
 * @author liangqin
 * @date 2019/3/23 11:05
 */
public class ResultUtil {

    //正确时的返回值
    public static Result success(Object data){
        Result result = new Result();
        result.setStatus(200);
        result.setData(data);
        return result;
    }

    public static Result success(){
        return null;
    }

    //错误时的返回值
    public static Result error(int code, String msg) {
        Result result = new Result();
        result.setStatus(code);
        result.setErrorMsg(msg);
        return result;
    }
}
