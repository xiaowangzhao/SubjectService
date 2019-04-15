package com.subject.dao;

import com.subject.model.Stusub;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Transactional
public interface StusubMapper {

    int deleteStusub(String stuid);

    int insertStuSub(Stusub stusub);

    /**
     * 学生选题
     * @param stusubs
     * @return
     */
    int insertBatchStuSub(@Param("stusubs") List<Stusub> stusubs);

    /**
     * 教师选择学生，将pickflag设为1
     * @param
     * @return
     */
    int succPickStu(@Param("stuid") String stuid, @Param("subid") long subid);

    /**
     * 教师弃选学生，将pickflag设为0
     * @param
     * @return
     */
    int failPickStu(@Param("stuid") String stuid, @Param("subid") long subid);

    /**
     *
     * @param subid
     * @return
     */
    Stusub selectStusub(long subid);

    List<Stusub> selectAll();

    int updateByPrimaryKey(Stusub record);
}