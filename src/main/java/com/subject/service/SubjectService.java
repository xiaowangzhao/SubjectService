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
    List<Subject> getSubjects(String tid);



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
     * 按专业查看所有课题情况,其中参数specid不能为null或”“
     * @param specid
     * @return
     */
    List<Subject> selectSubsBySpec(String specid) throws IOException;

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
}
