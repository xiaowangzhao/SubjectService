package com.subject.dao;

import com.subject.model.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface SubjectMapper {

    /**
     * 根据 subid 获得 subject 信息
     *
     * @param subid
     * @return
     */
    Subject getSubject(Long subid);

    /**
     * 根据教师信息得到课题列表
     *
     * @param tid
     * @return
     */
    List<Subject> getSubjects(String tid);

    /**
     * 删除课题
     * @param subid
     * @return
     */
    int delSubject(Long subid);

    /**
     * 提交课题（修改课题提交标志为1）
     *
     * @param subid
     * @return
     */
    int submitSubject(long subid);

    /**
     * 修改课题
     *
     * @param subject
     * @return
     */
    @Transactional
    int updateSubject(@Param("subject") Subject subject);

    /**
     * 增加课题
     * @param subject
     * @return
     */
    @Transactional
    int insertSubject(Subject subject);

    /**
     * 查询全部课题
     * @return
     */
    List<Subject> selectAll();

    /**
     * 查询申报了课题方向为value的课题
     * @param subdirection
     * @return
     */
    List<Subject> selectSubByDirection(@Param("subdirection") String subdirection);

    /**
     * 查询教师提交课题数
     * @param tid
     * @return
     */
    int selectSubmitSubSum(String tid);

    /**
     * 查询教师需要审核课题数
     * @param tid
     * @return
     */
    int selectAuditSubSum(String tid);

    /**
     * 查询教师需要审核课题数
     * @param tid
     * @return
     */
    int selectNotAuditSubSum(String tid);



}