package com.subject.service;

import com.subject.dto.Result;
import com.subject.model.Progress;
import com.subject.model.SubNumByTea;
import com.subject.model.Subject;
import com.subject.model.Subspec;
import org.apache.ibatis.annotations.Param;

import java.text.ParseException;
import java.util.List;

/**
 * @author liangqin
 * @date 2019/3/23 9:45
 */
public interface SubjectService {

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
     * 获取课题列表
     * @return
     */
    List<Subject> selectAll();

    /**
     * 删除课题
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
}
