package com.subject.dao;

import com.alibaba.fastjson.JSONObject;
import com.subject.BaseTest;
import com.subject.model.Student;
import com.subject.model.Stusub;
import com.subject.util.GetDataUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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
    public void testSuccPickStu() {
        int stusub = stusubMapper.succPickStu("2020",28L);
        System.out.println(stusub);
    }

    @Test
    public void testFailPickStu() {
        int stusub = stusubMapper.failPickStu("2014",28L);
        System.out.println(stusub);
    }

    @Test
    public void testDeleteStusub() {
        int stusub = stusubMapper.deleteStusub("111");
        System.out.println(stusub);
    }

    @Test
    public void testDefeatStu() {
        int stusub = stusubMapper.deleteStusub("111");
    }

    @Test
    public void testSelectStuStatus() {
        List<Stusub> stusubs = stusubMapper.selectStuStatus("255");
        for(Stusub stusub : stusubs) {
            System.out.println(stusub);
        }

    }


    @Test
    public void testSelectStuBySubid() {
        System.out.println(stusubMapper.selectStuBySubid(83L));
    }
    @Test
    public void testSelectSub() {
        System.out.println(stusubMapper.selectSub("20161110002"));
    }


    @Test
    public void testWhetherSelectSub() {
        System.out.println(stusubMapper.whetherSelectSub(89L));
    }

    @Test
    public void againSelect() {
        System.out.println(stusubMapper.againSelect("20161110001"));


    }

    @Test
    public void test() {
        String url = "http://localhost:8089/student/getstubystuid?stuid=20141113035";
        GetDataUtil getDataUtil = new GetDataUtil();
        //Student student = (Student) getDataUtil.getObject(url, Student.class);
        JSONObject jsonObject = GetDataUtil.getJSONObject(url);
        String tname = GetDataUtil.getTeacherNameByTid("110004");
        System.out.println(tname);
    }
}
