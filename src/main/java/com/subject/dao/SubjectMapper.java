package com.subject.dao;

import com.subject.model.ReviewSubject;
import com.subject.model.SubSim;
import com.subject.model.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface SubjectMapper {

    /**
     * 增加课题
     * @param subject
     * @return
     */
    int insertSubjectTmep(Subject subject);

    /**
     * 根据 subid 获得 subject 信息
     *
     * @param subid
     * @return
     */
    Subject getSubject(Long subid);

    /**
     * 根据教师信息得到课题列表
     *      *
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

    /**
     * 按专业查看所有课题情况,其中参数specid不能为null或”“
     * @param specid
     * @return
     */
    List<Subject> selectSubsBySpec(@Param("specid") String specid);


    /**
     * 判断课题是否重复
     * @param tid
     * @param subname
     * @return
     */
    List<Subject> selectSubjectRepeat(@Param("tid") String tid, @Param("subname") String subname);

    /**
     * 判断课题相似度
     * @return
     */
    List<Subject> selectSubjectSim();

    /**
     * 课题审核查询
     *按专业、课题名、学生名 查询论文审核信息
     * @param specid
     * @param subname
     * @return
     */
    List<ReviewSubject> selectSubsBySpecAndName(@Param("specid") String specid, @Param("subname") String subname);

    /**
     * 判断课题的个数
     */
    int selectSubjectCount(String tid);

    /**
     * 查找每个课题的审核意见（盲审）
     * @param subid
     * @return
     */
    String selectReviewBySubid(long subid);

    /**
     *查询教师往年课题
     * @param tid
     * @param longyear
     * @return
     */
    List<SubSim> selectPastSubject(@Param("tid") String tid, @Param("longyear") String longyear);

    /**
     * 课题没有被选中，查看是否已有学生初选
     * @param sbuid
     * @return
     */
    int selectNotSelectCountSubject(long sbuid);

    /**
     * 课题审核全没通过，将status置为1
     * @param subid
     * @return
     */
    int updateStatus(long subid);

    /**
     * 学生换导师
     * @param subid
     * @return
     */
    int updateSubTeacher(@Param("subid") long subid, @Param("tid") String tid);
}