package com.subject.controller;

import com.subject.Exception.DescribeException;
import com.subject.Exception.ExceptionEnum;
import com.subject.Exception.ExceptionHandle;
import com.subject.service.ExportService;
import com.subject.service.SysarguService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author liangqin
 * @date 2019/5/15 10:26
 */

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private SysarguService sysarguService;

    @Autowired
    private ExportService exportService;

    /**
     * 下载学生课题明细表
     * @param response
     */
    @RequestMapping(value = "/stusublistdownload", method = {RequestMethod.GET}, produces = "application/msexcel")
    public void stuSubListDownload(HttpServletResponse response) {
        String fileName = "output.xls";
        try{
            exportService.download(response, fileName);
        }catch (Exception e) {
            throw e;
        }
    }

    /**
     * 下载任务书
     * @param response
     */
    @RequestMapping(value = "/taskbooksbytiddownload", method = {RequestMethod.GET}, produces = "application/msexcel")
    public void taskBooksByTidDownload(HttpServletResponse response, @RequestParam(value = "tid") String tid) {
        String fileName = tid + "taskbookoutput.xls";
        try{
            exportService.download(response, fileName);
        }catch (Exception e) {
            throw e;
        }
    }
}
