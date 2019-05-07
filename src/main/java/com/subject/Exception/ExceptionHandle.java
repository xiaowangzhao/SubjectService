package com.subject.Exception;

import com.subject.dto.Result;
import com.subject.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author liangqin
 * @date 2019/5/7 17:43
 */


/**
 * 使用一个Handle来把Try，Catch中捕获的错误进行判定，
 * 是一个我们已知的错误信息，还是一个未知的错误信息，如果是未知的错误信息，
 * 那我们就用log记录它，便于之后的查找和解决
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    public Result exceptionGet(Exception e) {
        if(e instanceof DescribeException) {
            DescribeException myException = (DescribeException) e;
            return ResultUtil.error(myException.getCode(), myException.getMessage());
        }

        logger.error("【系统异常】{}", e);
        return ResultUtil.error(ExceptionEnum.UNKNOW_ERROR);
    }
}
