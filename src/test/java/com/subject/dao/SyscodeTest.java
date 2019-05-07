package com.subject.dao;

import com.subject.BaseTest;
import com.subject.model.Syscode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author liangqin
 * @date 2019/4/11 9:51
 */
public class SyscodeTest  extends BaseTest {

    @Autowired
    private SyscodeMapper syscodeMapper;

    @Test
    public void testSelectCode() {
        Syscode syscode = syscodeMapper.selectSyscodeById(1);
        System.out.println(syscode);
    }

    @Test
    public void testSelectCodeByCodeno() {
        Syscode syscode = syscodeMapper.selectCodeByContent("ktzht", "未提交");
        System.out.println(syscode);


    }
}
