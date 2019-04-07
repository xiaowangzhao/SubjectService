package com.subject.dao;

import ch.qos.logback.core.util.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.subject.BaseTest;
import com.subject.model.Progress;
import com.subject.model.Subject;
import com.subject.util.JsonUtil;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liangqin
 * @date 2019/3/25 11:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SubjectTest  {

    @Autowired
    private SubjectMapper subjectMapper;

    @Test
    public void testInsertSubject() {
        Subject subject = new Subject();
        subject.setAsstid("003");
        subject.setContent("dsf");
        subject.setIsoutschool(23);
        subject.setRequirement("sdf");
        subject.setRefpapers("sdf");
        subject.setRequirement("sdf");
        subject.setStatus("0");
        subject.setSubkind("bbb");
        subject.setSubdirection("saf");
        subject.setSubsource("adf");
        subject.setSubsort("a");
        subject.setSubtype("s");
        subject.setSummary("asfd");
        subject.setTid("002");
        subject.setUsedyear("2016");
        Progress progress = new Progress();
        progress.setInorder(2);
        progress.setContent("sfd");
        int count = subjectMapper.insertSubject(subject);
        System.out.println(subject.getSubid());
    }

    @Test
    public void testEditSubject() {
        Subject subject = new Subject();
        subject.setSubname("ssfsf");
        subject.setAsstid("003");
        subject.setContent("dsf");
        subject.setIsoutschool(23);
        subject.setRequirement("sdf");
        subject.setRefpapers("sdf");
        subject.setRequirement("sdf");
        subject.setStatus("0");
        subject.setSubkind("bbb");
        subject.setSubdirection("saf");
        subject.setSubsource("adf");
        subject.setSubsort("a");
        subject.setSubtype("s");
        subject.setSummary("asfd");
        subject.setTid("002");
        subject.setUsedyear("2016");
        int count = subjectMapper.insertSubject(subject);
        System.out.println(count);
    }


    @Test
    public void testSelectSubByDirection(){
       List<Subject> subjects = subjectMapper.selectSubByDirection("1");
       for(Subject subject : subjects) {
           System.out.println(subject + "111");
       }
        System.out.println("111");
    }

    @Test
    public void testSelectSubmitSubSum(){
        int count = subjectMapper.selectSubmitSubSum("002");
        System.out.println(count);
    }

    @Test
    public void testSelectauditSubSum(){
        int count = subjectMapper.selectAuditSubSum("003");
        System.out.println(count);
    }

    @Test
    public void testSelectNotAuditSubSum(){
        int count = subjectMapper.selectNotAuditSubSum("003");
        System.out.println(count);
    }

    @Test
    public void test() throws IOException{
        File file = ResourceUtils.getFile("classpath:json/teacher.json");
        String content = FileUtils.readFileToString(file);
        JSONObject jsonObject = JSON.parseObject(content);
        JSONArray jsonArray = jsonObject.getJSONArray("teacher");
        System.out.println(jsonArray);
        for(Object obj : jsonArray) {
            JSONObject jsonOb = (JSONObject) obj;
            String tname = jsonOb.getString("tname");
            System.out.println(tname);
        }
    }
}
