package com.subject.service;

import com.subject.BaseTest;
import com.subject.dto.Result;
import com.subject.model.Progress;
import com.subject.model.SubSim;
import com.subject.model.Subject;
import com.subject.model.Subspec;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        List<SubSim> subSimList = subjectService.computesimilar("1111", 0.9F);
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
        List<Subject> subjects = subjectService.getSubsBySpec("002", null, null, null);
        for(Subject subject : subjects) {
            System.out.println(subject);
        }
    }

    @Test
    public void testGetStusBySpec() throws Exception {
        List test =  subjectService.getStusBySpec("003", null, null);
        System.out.println(test);
    }

    @Test
    public void tests() throws IOException {
        Map<String, Object> map = subjectService.getSpecInformation(54L);
        System.out.println(map);

    }

    @Test
    public void getAllinfo() throws IOException {
        List<Subject> subjects = subjectService.getAllinfo("001");
        for(Subject subject : subjects) {
            System.out.println(subject);
        }
    }
}
