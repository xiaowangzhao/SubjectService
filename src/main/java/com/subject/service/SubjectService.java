package com.subject.service;

import com.subject.dto.Result;
import com.subject.model.*;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author liangqin
 * @date 2019/3/23 9:45
 */
public interface SubjectService {

    /**
     * 根据 subid 获得 subject
     * @param subid
     * @return
     */
    Subject selectBySubid(Long subid);

    /**
     * 根据 subid 获得 subject ,progresslist，speid
     *
     * @param subid
     * @return
     */
    Map<String, Object> getSubject(Long subid);

    /**
     * 根据教师信息得到课题列表
     *
     * @param tid
     * @return
     */
    Map<String, Object> getSubjects(String tid);

    /**
     * 获取教师对应每个课题的状态及可进行的操作情况
     * @param tid
     * @return
     */
    List<Subject> getAllinfo(String tid);



    /**
     * 获取课题列表
     * @return
     */
    List<Subject> selectAll();

    /**
     * 删除课题
     * @param subid
     * @return
     */
    int delSubjectBySubid(Long subid);

    /**
     * 删除课题,进程，所属专业
     * @param subid
     * @return
     */
    int delSubject(Long subid);

    /**
     * 提交课题（修改课题的状态为1）
     *
     * @param subid
     * @return
     */
    int submitSubject(long subid);

    /**
     * 提交课题到临时表
     *
     * @param subject
     * @return
     */
    int submitSubjectTemp(Subject subject);

    /**
     * 修改课题
     *
     * @param subject
     * @return
     */
    int updateSubject(Subject subject, List<Progress> processList, String specid);

    /**
     * 添加课题
     * @param subject
     * @param processList
     * @param specid
     * @return
     */
    int insertSubject(Subject subject, List<Progress> processList, String specid);

    /**
     *为评审教师分配课题（题目审核时用）
     * @return
     */
    String assignSubject() throws ParseException;

    /**
     * 批量审核课题(审核通过)
     * @param subspecs
     * @return
     */
    int auditBarchSub(List<Subspec> subspecs);

    /**
     * 通过tid， 获取教师所需要盲审的课题
     * @param tid
     * @return
     */
    Map<String, Object> getTeaReviewSub(String tid);

    /**
     * 更新教师盲审意见
     * @param reviewSubjects
     * @return
     */
    int updateReviewOpinion(List<ReviewSubject> reviewSubjects);

    /**
     * 恢复课题状态到“审核未通过”
     * @param subid
     * @return
     */
    int restoreSubjectStatus(long subid);

    /**
     * 查询教师课题数目
     * @param tid, tname
     * @return
     */
    SubNumByTea getsubnum(String tid, String tname);



    /**
     * 判断课题相似性
     * @param subname
     * @param threshold
     * @return
     */
    List<SubSim> computesimilar(String subname, float threshold);

    /**
     *  保存前校验
     * @param subject
     */
    String validNewSubject(Subject subject);

    /**
     * 判断课题的个数
     */
    int selectSubjectCount(String tid);

    /**
     *按specid查看所有课题情况
     * @param specid
     * @param substatus
     * @return
     */
    List<Subject> getSubsBySpec(String specid,  String tdept,String tname, String substatus) throws Exception;

    /**
     * 与教师往年课题（近三年）进行相似度比较
     *  返回相似度大于等于阈值的课题名
     * @param tutorid
     * @param subname
     * @param threshold
     * @return
     */
    List<SubSim> computesimilarWithOldSubs(String tutorid, String subname, float threshold);

    /**
     * 课题审核查询
     *按专业、课题名、学生名 查询论文审核信息
     * @param specid
     * @param subname
     * @return
     */
    List<ReviewSubject> getSubsBySpecAndName( String specid, String subname) throws IOException;

    /**
     * 按专业获得学生当前选题状态
     * @param specid
     * @param classname
     * @param stustatus
     * @return
     */
    List getStusBySpec(String specid,String classname,String stustatus) throws IOException, Exception;


    /**
     * 查询申报了课题方向为value的课题
     * @param subdirection
     * @return
     */
    List<Subject> getSubByDirection(String subdirection);

    Map<String, Object> getSpecInformation(Long subid) throws IOException;
}
