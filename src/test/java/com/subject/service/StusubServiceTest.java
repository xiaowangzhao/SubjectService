package com.subject.service;

import com.subject.BaseTest;
import com.subject.model.Stusub;
import com.subject.model.Subspec;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangqin
 * @date 2019/4/15 10:17
 */
public class StusubServiceTest  extends BaseTest {
    @Autowired
    StusubService stusubService;

    @Test
    public void testInsertBatchStuSub() {
        Stusub stusub = new Stusub();
        stusub.setStuid("111");
        Stusub stusub2 = new Stusub();
        stusub2.setStuid("111");
        List<Stusub> stusubList = new ArrayList<>();
        stusubList.add(stusub);
        stusubList.add(stusub2);
        String result = stusubService.insertBatchStuSub(stusubList);
        System.out.println(result);
    }

    @Test
    public void testTeaPickStu() {
        stusubService.teaPickStu("2024", 17L, 1);
    }

    @Test
    public void testGetStuStatus() {
        String status = stusubService.getStuStatus("20214");
        System.out.println(status);
    }
}
