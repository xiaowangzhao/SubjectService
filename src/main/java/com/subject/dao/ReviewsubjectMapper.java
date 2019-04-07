package com.subject.dao;

import com.subject.model.Reviewsubject;
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

    int insert(Reviewsubject record);

    Reviewsubject selectByPrimaryKey(@Param("tid") String tid, @Param("subid") Long subid);

    List<Reviewsubject> selectAll();

    int updateByPrimaryKey(Reviewsubject record);
}