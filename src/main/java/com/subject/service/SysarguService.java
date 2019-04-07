package com.subject.service;

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
}
