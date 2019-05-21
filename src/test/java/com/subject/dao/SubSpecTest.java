package com.subject.dao;

import com.subject.model.Subspec;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangqin
 * @date 2019/3/25 20:39
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubSpecTest {

    @Autowired
    private SubspecMapper subspecMapper;

    @Test
    public void testInsertSubspec() {
        Subspec subspec = new Subspec();
        subspec.setSpecid("100");
        subspec.setSubid(null);
        int count = subspecMapper.insertSubspec(subspec);
        System.out.println(count);
    }

    @Test
    public void testDeleteById() {
        int count = subspecMapper.deleteBySubid(222l);
        System.out.println(count);
    }

    @Test
    public void testSelectAuditoption() {
        System.out.println(subspecMapper.selectAuditoption(74L));
    }

    @Test
    public void testSelect() {
        System.out.println(subspecMapper.selectSpecidsBySubid(54L));
    }

    @Test
    public void testUpdateSubspec() {
        Subspec subspec = new Subspec();
        subspec.setSubspecid(2l);
        subspec.setSpecid("101");
        subspec.setSubid(2223l);
        subspec.setAuditflag("0");
        subspec.setAuditoption("sdfasdf");
        subspec.setReleaseflag("a");
        subspec.setRemark("asdf");
        int count = subspecMapper.updateSubspec(subspec);
        System.out.println(count);
    }

    @Test
    public void testSubmitAuditBatchPass() {
        Subspec subspec = new Subspec();
        subspec.setSubid(27l);
        subspec.setSpecid("002");
        subspec.setAuditflag("1");
        Subspec subspec1 = new Subspec();
        subspec1.setSubid(31l);
        subspec1.setSpecid("002");
        subspec1.setAuditflag("1");
        List<Subspec> subspecList = new ArrayList<>();
        subspecList.add(subspec);
        subspecList.add(subspec1);
        int count = subspecMapper.submitAuditBatchPass(subspecList);
        System.out.println(count);
    }

    @Test
    public void testSubmitAuditBatchUnPass() {
        Subspec subspec = new Subspec();
        subspec.setSubid(18l);
        subspec.setSpecid("003");
        subspec.setAuditoption("11");
        Subspec subspec1 = new Subspec();
        subspec1.setSubid(19l);
        subspec1.setSpecid("003");
        subspec1.setAuditoption("22");
        List<Subspec> subspecList = new ArrayList<>();
        subspecList.add(subspec);
        subspecList.add(subspec1);
        int count = subspecMapper.submitAuditBatchUnPass(subspecList);
        System.out.println(count);
    }

    @Test
    public void testReleasesubject() {
        Subspec subspec = new Subspec();
        subspec.setSubid(12l);
        subspec.setSpecid("103");
        Subspec subspec1 = new Subspec();
        subspec1.setSubid(13l);
        subspec1.setSpecid("003");
        List<Subspec> subspecList = new ArrayList<>();
        subspecList.add(subspec);
        subspecList.add(subspec1);
        int count = subspecMapper.releasesubject(subspecList);
        System.out.println(count);
    }

    @Test
    public void testRestoreSubjectStatus() {
        long count = subspecMapper.restoreSubjectStatus(15L);
        System.out.println(count);
    }

    @Test
    public void testSelectBySubid() {
        Subspec count = subspecMapper.selectBySubid(12L);
        System.out.println(count);
    }

    @Test
    public void testSelectSpec() {
        Subspec count = subspecMapper.selectSpec(16L, "003");
        System.out.println(count);
    }

}
