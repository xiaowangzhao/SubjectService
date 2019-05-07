package com.subject.dao;

import com.subject.BaseTest;
import com.subject.model.Sysargu;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liangqin
 * @date 2019/4/1 19:53
 */
public class SysarguTest extends BaseTest {

    @Autowired
    private SysarguMapper sysarguMapper;

    @Test
    public void testSelectArguvalueByName() {
        String value = sysarguMapper.selectArguvalueByName("startdate");
        System.out.println(value);
    }

    @Test
    public void testSelectSysargu() {
        Sysargu value = sysarguMapper.selectSysargu("startdate");
        System.out.println(value);
    }
}
