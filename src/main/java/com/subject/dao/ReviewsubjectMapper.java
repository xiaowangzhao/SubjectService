package com.subject.dao;

import com.subject.model.ReviewSubject;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Transactional
public interface ReviewsubjectMapper {

    /**
     * 清空盲审表
     */
    int deleteAll();

    int deleteByPrimaryKey(@Param("tid") String tid, @Param("subid") Long subid);

    int insert(ReviewSubject record);

    ReviewSubject selectByPrimaryKey(@Param("tid") String tid, @Param("subid") Long subid);

    List<ReviewSubject> selectByTid(String tid);

    int updateReviewOpinion(@Param("reviewSubjects") List<ReviewSubject> reviewSubjects);
}