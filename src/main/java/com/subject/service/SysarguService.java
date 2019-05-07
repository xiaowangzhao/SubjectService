package com.subject.service;

import com.subject.model.Sysargu;

import java.text.ParseException;

/**
 * @author liangqin
 * @date 2019/4/1 19:57
 */
public interface SysarguService {

    /**
     * 检查毕业设计是否已经开始
     * @return
     */
    boolean ifStartGraduate() throws ParseException;

    /**
     * 通过参数名查找参数值
     * @param arguname
     * @return
     */
    Sysargu selectSysargu(String arguname);
}
