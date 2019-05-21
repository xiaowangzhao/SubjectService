package com.subject.controller;

import com.subject.Exception.ExceptionEnum;
import com.subject.Exception.ExceptionHandle;
import com.subject.dto.Result;
import com.subject.service.SubjectService;
import com.subject.service.SysarguService;
import com.subject.util.ExcelUtil;
import com.subject.util.IpUtil;
import com.subject.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

/**
 * @author liangqin
 * @date 2019/4/23 10:46
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/subjectsysargu")
public class OpenAPI {

    private static  final Logger logger =  LoggerFactory.getLogger(OpenAPI.class);

    @Autowired
    private SysarguService sysarguService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ExceptionHandle exceptionHandle;

    @RequestMapping(value = "/test",method = {RequestMethod.GET})
    public Result test(HttpServletRequest request) {
        Result result = ResultUtil.success();
        String ip = IpUtil.getIpAddr(request);
        try{
            if(ip.equals("0:0:0:0:0:0:0:1")){
                result =  ResultUtil.error(ExceptionEnum.NO_JURISDICTION);
            }else {
                int i = 1/0;
            }
        }catch (Exception e) {
            result = exceptionHandle.exceptionGet(e);
        }
//        if(ip.equals("0:0:0:0:0:0:0:1")){
//            return ResultUtil.error(ExceptionEnum.NO_JURISDICTION);
//        }
//        System.out.println(request);
//        logger.info("访问----/subjectsysargu/test----接口的ip：" + ip);
//        return ResultUtil.success(ip);
        return result;
    }


    @RequestMapping(value = "/ifstartgraduate",method = {RequestMethod.GET})
    public Result ifStartGraduate() throws ParseException {
        return ResultUtil.success(sysarguService.ifStartGraduate());
    }

    @RequestMapping(value = "/getsubbydirection",method = {RequestMethod.GET})
    public Result getSubByDirection(@RequestParam(value = "subdirection", required = true) String subdirection) {
        return ResultUtil.success(subjectService.getSubByDirection(subdirection));
    }

    @RequestMapping(value = "/getallsubject",method = {RequestMethod.GET})
    public Result getAllSubject(){
        return ResultUtil.success(subjectService.selectAll());

    }


}
