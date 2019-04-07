package com.subject.dao;

import com.subject.BaseTest;
import com.subject.model.Reviewsubject;
import com.subject.model.SubReview;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liangqin
 * @date 2019/4/2 15:51
 */
public class ReviewsubjectTest extends BaseTest {

    @Autowired
    private ReviewsubjectMapper reviewsubjectMapper;

    @Test
    public void testDeleteAll() {
        System.out.println(reviewsubjectMapper.deleteAll());
    }

    @Test
    public void testInsert() {
        Reviewsubject reviewsubject = new Reviewsubject();
        reviewsubject.setTid("1110");
        reviewsubject.setSubid(65L);
        System.out.println(reviewsubjectMapper.insert(reviewsubject));
    }

}
