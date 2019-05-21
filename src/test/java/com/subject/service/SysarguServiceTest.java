package com.subject.service;

import com.subject.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

/**
 * @author liangqin
 * @date 2019/4/1 20:03
 */
public class SysarguServiceTest extends BaseTest {

    @Autowired
    private SysarguService sysarguService;

    @Test
    public void testIfStartGraduate() throws ParseException {
        System.out.println(sysarguService.ifStartGraduate());
    }

    @Test
    public void selectSysargu() throws ParseException {
        System.out.println(sysarguService.selectSysargu("tempfilepath").getArguvalue());
    }
}
