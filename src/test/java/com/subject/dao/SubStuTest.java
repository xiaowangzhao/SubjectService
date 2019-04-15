package com.subject.dao;

import com.subject.BaseTest;
import com.subject.model.Stusub;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangqin
 * @date 2019/4/11 10:47
 */
public class SubStuTest  extends BaseTest {

    @Autowired
    StusubMapper stusubMapper;

    @Test
    public void testSelectStusub() {
        Stusub stusub = stusubMapper.selectStusub(15L);
        System.out.println(stusub);
    }

    @Test
    public void testInsertStuSub() {
        Stusub stusub = new Stusub();
        stusub.setStuid("20165555");
        stusub.setSubid(15L);
        stusub.setPickorder("1");
        stusub.setPickflag("1");
        int count = stusubMapper.insertStuSub(stusub);
        System.out.println(count);
    }

    @Test
    public void testInsertBatchStuSub() {
        Stusub stusub = new Stusub();
        stusub.setStuid("111");
        Stusub stusub2 = new Stusub();
        stusub2.setStuid("111");
        List<Stusub> stusubList = new ArrayList<>();
        stusubList.add(stusub);
        stusubList.add(stusub2);
        int count = stusubMapper.insertBatchStuSub(stusubList);
        System.out.println(count);
    }

    @Test
    public void testUpdatePickflag() {
        int stusub = stusubMapper.succPickStu("111",28L);
        System.out.println(stusub);
    }

    @Test
    public void testFailPickStu() {
        int stusub = stusubMapper.failPickStu("111",28L);
        System.out.println(stusub);
    }

    @Test
    public void testDeleteStusub() {
        int stusub = stusubMapper.deleteStusub("111");
        System.out.println(stusub);
    }
}
