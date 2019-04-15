package com.subject.dao;

import com.subject.BaseTest;
import com.subject.model.ReviewSubject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangqin
 * @date 2019/4/2 15:51
 */
public class ReviewSubjectTest extends BaseTest {

    @Autowired
    private ReviewsubjectMapper reviewsubjectMapper;

    @Test
    public void testDeleteAll() {
        System.out.println(reviewsubjectMapper.deleteAll());
    }

    @Test
    public void testInsert() {
        ReviewSubject reviewsubject = new ReviewSubject();
        reviewsubject.setTid("1110");
        reviewsubject.setSubid(65L);
        System.out.println(reviewsubjectMapper.insert(reviewsubject));
    }

    @Test
    public void testEelectByTid() {
        List<ReviewSubject> reviewSubjectList = reviewsubjectMapper.selectByTid("002");
       for( ReviewSubject reviewsubject : reviewSubjectList) {
           System.out.println(reviewsubject);
       }

    }

    @Test
    public void testUpdateReviewOpinion() {
        ReviewSubject reviewSubject = new ReviewSubject();
        reviewSubject.setTid("002");
        reviewSubject.setSubid(16L);
        reviewSubject.setReviewopinion("通过");
        ReviewSubject reviewSubject2 = new ReviewSubject();
        reviewSubject2.setTid("002");
        reviewSubject2.setSubid(17L);
        reviewSubject2.setReviewopinion("通过");
        List<ReviewSubject> reviewSubjectList = new ArrayList<>();
        reviewSubjectList.add(reviewSubject);
        reviewSubjectList.add(reviewSubject2);
        int count = reviewsubjectMapper.updateReviewOpinion(reviewSubjectList);
        System.out.println(count);
    }

}
