package com.subject.dao;

import com.subject.model.Progress;
import com.subject.util.ListToString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangqin
 * @date 2019/3/25 19:36
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProgressTest {

    @Autowired
    private ProgressMapper progressMapper;

    @Test
    public void testInsertProgress() {
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
        int count  = progressMapper.addProgress(progressList);
        System.out.println(count);
    }

    @Test
    public void testUpdateProgress() {
        Progress progress = new Progress();
        progress.setProid(1l);
        progress.setInorder(0);
        int count  = progressMapper.updateProgress(progress);
        System.out.println(count);
    }

    @Test
    public void testDeleteProgressBySubid() {
        int count  = progressMapper.deleteProgressBySubid(11L);
        System.out.println(count);
    }

    @Test
    public void testSelectProgressBySubid() {
        long proid  = progressMapper.selectProgress(12,3);
        System.out.println(proid);
    }
}
