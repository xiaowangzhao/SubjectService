package com.subject.dao;

import ch.qos.logback.core.util.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.subject.BaseTest;
import com.subject.model.*;
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
import org.springframework.web.client.RestTemplate;

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
    public void testInsertSubjectTmep() {
        Subject subject = new Subject();
        subject.setSubid(12L);
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
        int count = subjectMapper.insertSubjectTmep(subject);
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


    @Test
    public void testGetSubsBySpec(){
        List<Subject> subjectList = subjectMapper.selectSubsBySpec("002");
        for(Subject subject : subjectList) {
            System.out.println(subject);
        }
    }

    @Test
    public void testGetSubsBySpecAndName(){
        List<ReviewSubject> reviewSubjectList = subjectMapper.selectSubsBySpecAndName("100", "aaaaa");
        for(ReviewSubject subject : reviewSubjectList) {
            System.out.println(subject);
        }
    }

    @Test
    public void testGetSubjectRepeat(){
        List<Subject> subjectList = subjectMapper.selectSubjectRepeat("003", "1111");
        for(Subject subject : subjectList) {
            System.out.println(subject);
        }
    }

    @Test
    public void testSelectSubjectSim(){
        List<Subject> subjectList = subjectMapper.selectSubjectSim();
        for(Subject subject : subjectList) {
            System.out.println(subject);
        }
    }

    @Test
    public void testSelectSubjectCount(){
        int count  = subjectMapper.selectSubjectCount("002");
            System.out.println(count);
    }

    @Test
    public void testgetReviewBySubid(){
        String count  = subjectMapper.selectReviewBySubid(16L);
        System.out.println(count);
    }

    @Test
    public void testSelectPastSubject(){
        List<SubSim> subSims  = subjectMapper.selectPastSubject("0002", "2015");
        for(SubSim subSim : subSims) {
            System.out.println(subSim);
        }
    }

    @Test
    public void testSelectNotSelectCountSubject(){
        int count  = subjectMapper.selectNotSelectCountSubject(15L);
        System.out.println(count);
    }

    @Test
    public void test111() {
        RestTemplate rest = new RestTemplate();
        String students = rest.getForObject("http://localhost:8084/students", String.class);
        System.out.println(students);
    }
}
