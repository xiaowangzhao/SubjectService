package com.subject.service;

import com.alibaba.fastjson.JSONObject;
import com.subject.BaseTest;
import com.subject.dto.Result;
import com.subject.model.*;
import com.subject.service.impl.SubjectServiceImpl;
import com.subject.util.GetDataUtil;
import jxl.write.WriteException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liangqin
 * @date 2019/3/26 16:19
 */
public class SubjectServiceTest extends BaseTest {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SysarguService sysarguService;


    @Test
    public void testAddSubject() {
        Subject subject = new Subject();
        subject.setAsstid("003");
        subject.setSubname("bbbbb");
        subject.setContent("dsf");
        subject.setIsoutschool(23);
        subject.setRequirement("sdf");
        subject.setRefpapers("sdf");
        subject.setRequirement("sdf");
        subject.setStatus("0");
        subject.setSubkind("bbb");
        subject.setSubdirection("1");
        subject.setSubsource("adf");
        subject.setSubsort("a");
        subject.setSubtype("s");
        subject.setSummary("asfd");
        subject.setTid("003");
        subject.setUsedyear("2016");
        Progress progress = new Progress();
        progress.setSubid(2l);
        progress.setContent("sgsg");
        progress.setInorder(3);
        progress.setStartendtime("sfds");
        Progress progress1 = new Progress();
        progress1.setContent("sgsg");
        progress1.setSubid(2l);
        progress1.setInorder(2);
        progress1.setStartendtime("sfds");
        List<Progress> progressList = new ArrayList<>();
        progressList.add(progress);
        progressList.add(progress1);
        int count = subjectService.insertSubject(subject, progressList, "100");
        System.out.println(count);
    }

    @Test
    public void testUpdateSubject() {
        Subject subject = new Subject();
        subject.setSubid(14L);
        subject.setAsstid("003");
        subject.setSubname("1111");
        subject.setContent("dsf");
        subject.setIsoutschool(23);
        subject.setRequirement("sdf");
        subject.setRefpapers("sdf");
        subject.setRequirement("sdf");
        subject.setStatus("0");
        subject.setSubkind("bbb");
        subject.setSubdirection("1");
        subject.setSubsource("adf");
        subject.setSubsort("a");
        subject.setSubtype("s");
        subject.setSummary("asfd");
        subject.setTid("002");
        subject.setUsedyear("2016");
        Progress progress = new Progress();
        progress.setSubid(2l);
        progress.setContent("sgsg");
        progress.setInorder(0);
        progress.setStartendtime("sfds");
        Progress progress1 = new Progress();
        progress1.setContent("sgsg");
        progress1.setSubid(2l);
        progress1.setInorder(1);
        progress1.setStartendtime("sfds");
        List<Progress> progressList = new ArrayList<>();
        progressList.add(progress);
        progressList.add(progress1);
        int count = subjectService.updateSubject(subject, progressList, "100");
        System.out.println(count);
    }

    @Test
    public void testAssignSubject() throws ParseException {
        String text = subjectService.assignSubject();
        System.out.println(text);
    }

    @Test
    public void testAuditBarchSub() {
        Subspec subspec = new Subspec();
        subspec.setSubid(27l);
        subspec.setSpecid("002");
        subspec.setReleaseflag("1");
        Subspec subspec1 = new Subspec();
        subspec1.setSubid(31l);
        subspec1.setSpecid("002");
        subspec1.setReleaseflag("1");
        List<Subspec> subspecList = new ArrayList<>();
        subspecList.add(subspec);
        subspecList.add(subspec1);

        System.out.println(subjectService.auditBarchSub(subspecList));
    }

    @Test
    public void testComputesimilar() {
        List<SubSim> subSimList = subjectService.computeSimilar("asdf俺是个爱上敢死队风格", 0.9F);
        for(SubSim subSim : subSimList) {
            System.out.println(subSim);
        }
    }

    @Test
    public void testValidNewSubject() {
        Subject subject = new Subject();
        subject.setSubid(14L);
        subject.setAsstid("003");
        subject.setSubname("11");
        subject.setContent("dsf");
        subject.setIsoutschool(23);
        subject.setRequirement("sdf");
        subject.setRefpapers("sdf");
        subject.setRequirement("sdf");
        subject.setStatus("0");
        subject.setSubkind("bbb");
        subject.setSubdirection("1");
        subject.setSubsource("adf");
        subject.setSubsort("a");
        subject.setSubtype("s");
        subject.setSummary("asfd");
        subject.setTid("002");
        subject.setUsedyear("2016");
        Progress progress = new Progress();
        progress.setSubid(2l);
        progress.setContent("sgsg");
        progress.setInorder(0);
        progress.setStartendtime("sfds");
        Progress progress1 = new Progress();
        progress1.setContent("sgsg");
        progress1.setSubid(2l);
        progress1.setInorder(1);
        progress1.setStartendtime("sfds");
        List<Progress> progressList = new ArrayList<>();
        progressList.add(progress);
        progressList.add(progress1);
        String count = subjectService.validNewSubject(subject);
        System.out.println(count);
    }

    @Test
    public void testGetSubsBySpec() throws Exception {
        List<Subject> subjects = subjectService.getSubsBySpec("003", null, null, null);
        for(Subject subject : subjects) {
            System.out.println(subject);
        }
    }

    @Test
    public void testGetStusBySpec() throws Exception {
        List<Student> test =  subjectService.getStusBySpec("003", "软测142", "2");
        for(Student student : test) {
            System.out.println(student);
        }
    }

    @Test
    public void tests() throws IOException {
        //String specname = subjectService.getSpecInformation(62L);
        //System.out.println(specname);
    }

    @Test
    public void getAllinfo() throws IOException {
        List<Subject> subjects = subjectService.getAllinfo("002");
        for(Subject subject : subjects) {
            System.out.println(subject);
        }
    }

    @Test
    public void getStuSubList() throws IOException {
        List subjects = subjectService.getStuSubList("2016111");
        System.out.println(subjects);
    }

    @Test
    public void test() {
        RestTemplate rest = new RestTemplate();
        String a = rest.getForObject("http://localhost:8089/teacher/gettnamebytno?tno=110004", String.class);


        String filePath = sysarguService.selectSysargu("tempfilepath").getArguvalue();
        System.out.println(filePath);
    }

    @Test
    public void testInsertSubject() {
        Subject subject = new Subject();
        subject.setSubname("sdfsdfdsfsd");
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
        progress.setStartendtime("1-2");
        Progress progress1 = new Progress();
        progress1.setInorder(4);
        progress1.setContent("sfd");
        progress1.setStartendtime("3-4");
        Progress progress2 = new Progress();
        progress2.setInorder(6);
        progress2.setContent("sfd");
        progress2.setStartendtime("5-6");
        List<Progress> processList = new ArrayList<>();
        processList.add(progress);
        processList.add(progress1);
        processList.add(progress2);
        int count = subjectService.insertSubject(subject, processList, "003");
        System.out.println(count);
    }


    @Test
    public void testExportStuSubList() throws IOException, WriteException {


//        String turl1 = "http://localhost:8089/teacher/getteabytno?tno=110003";
//        String turl2 = "http://localhost:8089/teacher/getteabytno?tno=110004";
//        JSONObject teacherdata = GetDataUtil.getJSONObject(turl1);
//        JSONObject teacherObject = teacherdata.getJSONObject("data");
//        String tid = teacherObject.getString("tno");
//        String tname = teacherObject.getString("tname");
//        String tposTdegree = teacherObject.getString("tpost") + "-" + teacherObject.getString("tdegree");
//        System.out.println(tname);
//        System.out.println(tid);
//        System.out.println(tposTdegree);
    }

    @Test
    public void test1111() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8089/teacher/gettnamebytno?tno=110003";
        JSONObject jsonData = restTemplate.getForObject(url, JSONObject.class);
        String data = GetDataUtil.getSpecNameBySpceId("001");
        //String josn = restTemplate.getForObject(url, String.class);

        System.out.println(data);
    }

    @Test
    public void getSubNum() {
        List<SubNumByTea> subNumByTeaList = subjectService.getSubNum();
        for(SubNumByTea subNumByTea : subNumByTeaList) {
            System.out.println(subNumByTea);
        }

    }
    @Test
    public void getStuBySubid() {
        List<SubNumByTea> subNumByTeaList = subjectService.getSubNum();
        for(SubNumByTea subNumByTea : subNumByTeaList) {
            System.out.println(subNumByTea);
        }

    }
}
