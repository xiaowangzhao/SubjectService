package com.subject.service.impl;

import com.subject.dao.ProgressMapper;
import com.subject.dao.ReviewsubjectMapper;
import com.subject.dao.SubjectMapper;
import com.subject.dao.SubspecMapper;
import com.subject.model.*;
import com.subject.service.SubjectService;
import com.subject.service.SysarguService;
import com.subject.util.TeaSubComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;

/**
 * @author liangqin
 * @date 2019/3/23 9:45
 */
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private ProgressMapper progressMapper;

    @Autowired
    private SubspecMapper subspecMapper;

    @Autowired
    private SysarguService sysarguService;

    @Autowired
    private ReviewsubjectMapper reviewsubjectMapper;

    /**
     * 根据 subid 获得 subject 信息
     *
     * @param subid
     * @return
     */
    @Override
    public Subject getSubject(Long subid) {
        return subjectMapper.getSubject(subid);
    }

    /**
     * 根据教师信息得到课题列表
     *
     * @param tid
     * @return
     */
    @Override
    public List<Subject> getSubjects(String tid) {
        return subjectMapper.getSubjects(tid);
    }

    /**
     * 查询所有课题
     * @return
     */
    @Override
    public List<Subject> selectAll() {
        return subjectMapper.selectAll();
    }

    /**
     * 删除课题
     * @param subid
     * @return
     */
    @Override
    @Transactional
    public int delSubject(Long subid) {
        try{
            subjectMapper.delSubject(subid);
            progressMapper.deleteProgressBySubid(subid);
            subspecMapper.deleteBySubid(subid);
        }catch (Exception e) {
            System.out.println(e);
            return 0;
        }
        return 1;
    }

    /**
     * 提交课题（修改课题提交标志为1）
     *
     * @param subid
     * @return
     */
    @Override
    public int submitSubject(long subid) {
        return subjectMapper.submitSubject(subid);
    }

    /**
     * 修改课题
     *
     * @param subject
     * @return
     */
    @Override
    @Transactional
    public int updateSubject(Subject subject, List<Progress> processList, String specid) {
        long subid = subject.getSubid();
        try {
            subjectMapper.updateSubject(subject);
            progressMapper.deleteProgressBySubid(subid);
            for(Progress progress : processList) {
                progress.setSubid(subject.getSubid());
            }
            subspecMapper.deleteBySubid(subid);
            addProgressSubspec(subject, processList, specid);
        }catch (Exception e) {
            System.out.println(e);
            return 0;
        }
        return 1;
    }


    public void addProgressSubspec(Subject subject, List<Progress> processList, String specid) {
        progressMapper.addProgress(processList);
        Subspec subspec = new Subspec();
        subspec.setSubid(subject.getSubid());
        subspec.setSpecid(specid);
        subspecMapper.insertSubspec(subspec);
    }

    /**
     * 增加课题，成功返回1，失败返回0
     * @param subject
     * @param processList
     * @param specid
     * @return
     */
    @Override
    @Transactional
    public int insertSubject(Subject subject, List<Progress> processList, String specid) {
        try{
            if(subject != null && !processList.isEmpty() && specid != null){
                subjectMapper.insertSubject(subject);
                for(Progress progress : processList) {
                    progress.setSubid(subject.getSubid());
                }
               addProgressSubspec(subject, processList, specid);
            }
        }catch (Exception e) {
            System.out.println(e);
            return 0;
        }
        return 1;
    }

    /**
     * 从教师里获取subid
     */
    public String getSubidFromTea(TeaSubSum temp) {
        String allotSubid = temp.getSubjects().get(0); //获取第一个subid
        temp.getSubjects().remove(0);  //获取后删除
        temp.setSubsum( temp.getSubsum() - 1); //-1
        return allotSubid;
    }

    /**
     *为评审教师分配课题（题目审核时用）
     * @return
     */
    @Override
    @Transactional
    public String assignSubject() throws ParseException {
        if(sysarguService.ifStartGraduate()) {  //检查毕业设计是否已经开始，若已开始则返回
            return "毕业设计已经开始，不允许再审核课题!";
        }
        String assignresult="";
        ArrayList<SubReview> subReviews = new ArrayList<>();
        Map<String,String> teaSumForReview=new HashMap<>();//参与盲审的教师数量
        try{
            for(int i = 1; i <= 3; i++) {
                List<Subject> subjectList = subjectMapper.selectSubByDirection(String.valueOf(i));
                ArrayList<TeaSubSum> teaSubSums = new ArrayList<>(); //统计每个教师申报课题数目（符合课题方向i），记录每个老师盲审题目
                List<TeaSubSum> teaSubSumTemp = new ArrayList<>(); //存储课题号，分配时用
                String lastTid =null;
                TeaSubSum teacher = null;
                TeaSubSum temp = null;  //生成中间结果的，需要重新new,防止一个对象引用
                for(Subject subject : subjectList) { //循环将申报了课题方向i的教师放入teaSubSums中
                    String tid = subject.getTid();
                    String subid = String.valueOf(subject.getSubid());
                    if(tid ==null||subid==null) return "教师编号或者课程编号为空！！！";
                    if(tid.equals(lastTid)) {
                        teacher.setSubsum(teacher.getSubsum() + 1);
                        temp.setSubsum(temp.getSubsum() + 1);
                        temp.getSubjects().add(subid);
                    }else {
                        lastTid = tid;
                        teacher = new TeaSubSum();
                        teacher.setTid(tid);
                        temp = new TeaSubSum();
                        temp.setTid(tid);
                        teaSubSums.add(teacher);
                        teaSubSumTemp.add(temp);

                        teacher.setSubsum(1);
                        temp.setSubsum(1);
                        temp.getSubjects().add(subid);
                    }
                }
                //开始分配课题
                int teasum=teaSubSums.size();
                if(teasum == 1) return "只有一个教师申请了课题方向代码为"+String.valueOf(i)+"的课题，无法进行分配！";
                TeaSubComparator comparator = new TeaSubComparator();
                Collections.sort(teaSubSums, comparator);//先分审题目多的老师
                for(int j = 0; j < teasum; j++) {  //对每个老师进行循环分配，取第一个教师
                    TeaSubSum teaSubSum = teaSubSums.get(j);
                    String tid = teaSubSum.getTid();  //教师号
                    int subsum =  teaSubSum.getSubsum(); //课题数

                    Collections.sort(teaSubSumTemp, comparator);  //将temp过程中按仍尚未分配的课题数从小到大排序

                    boolean add = true;
                    while (add && subsum > 0) {
                        add = false;
                        for(int k = teaSubSumTemp.size() -1; k >=0; k--) { //取最后一个教师
                            temp = teaSubSumTemp.get(k);
                            if(temp.getTid().equals(tid)) continue;
                            String allotSubid = getSubidFromTea(temp);
                            SubReview review = new SubReview();
                            review.setTid(tid);
                            review.setSubid(allotSubid);
                            subReviews.add(review);
                            if(temp.getSubsum() == 0) {
                                teaSubSumTemp.remove(k);
                            }
                            add = true;
                            subsum --;
                            if(subsum <=0) break;
                        }
                    }
                }
                //若还有剩余课题，则该剩余课题一定只属于一个教师，其余老师平均分配
                if(teaSubSumTemp.size() > 0) {
                    temp = teaSubSumTemp.get(0);
                    int overplus = temp.getSubsum();
                    int teacurr = -1;
                    while (overplus > 0) {
                        if(teacurr >= teaSubSums.size() - 1) {
                            teacurr = -1;
                        }
                        teacurr++;
                        if(teaSubSums.get(teacurr).getTid().equals(temp.getTid())) continue;
                        String distsubid = getSubidFromTea(temp);
                        SubReview subReview = new SubReview();
                        subReview.setTid(teaSubSums.get(teacurr).getTid());
                        subReview.setSubid(distsubid);
                        subReviews.add(subReview);
                        overplus --;
                    }
                }
            }
            //将分配情况写入数据库
            try{
                reviewsubjectMapper.deleteAll();
                int subSums = subReviews.size();
                Reviewsubject reviewsubject;
                for(int l = 0; l < subSums; l++) {
                    SubReview reviewTemp = subReviews.get(l);
                    String tid = reviewTemp.getTid();
                    teaSumForReview.put(tid, tid);
                    Long subid = Long.parseLong(reviewTemp.getSubid());
                    reviewsubject = new Reviewsubject();
                    reviewsubject.setTid(tid);
                    reviewsubject.setSubid(subid);
                    reviewsubjectMapper.insert(reviewsubject);
                }
            }catch (Exception e) {
                System.out.println(e + "insert");
            }
        }catch (Exception e) {
            System.out.println(e + " assignSubject");
        }
        assignresult = "已分配的课题总数为 "+String.valueOf(subReviews.size()+" ,参与盲审的教师数为 " + teaSumForReview.size());
        return assignresult;
    }

    /**
     * 审核课题成功时传subid, specid,发布课题时传subid, specid, auditflag='1'
     * 审核课题失败时传subid, specid, auditoption
     * @param subspecs
     * @return
     */
    @Override
    @Transactional
    public int auditBarchSub(List<Subspec> subspecs) {
        if(subspecs.size() != 0) {
            Subspec subspec = subspecs.get(0);
            try {
                if(subspec.getAuditflag() == null && subspec.getReleaseflag() == null && subspec.getAuditoption() == null) { //批量审核课题,审核通过
                    subspecMapper.submitAuditBatchPass(subspecs);
                    return 1;
                }
                else if(subspec.getAuditflag() == null && subspec.getReleaseflag() == null && subspec.getAuditoption() != null) { //批量审核课题,审核未通过
                    subspecMapper.submitAuditBatchUnPass(subspecs);
                    return 1;
                }
                else if (subspec.getAuditflag().equals("1") && subspec.getReleaseflag() == null) { //批量发布课题
                    subspecMapper.releasesubject(subspecs);
                    return 1;
                }

            }catch (Exception e) {
                System.out.println(e);
            }
        }
        return 0;
    }

    /**
     * 恢复课题状态到“审核未通过”
     * @param subid
     * @return
     */
    @Override
    @Transactional
    public int restoreSubjectStatus(long subid) {
        try {
            return subspecMapper.restoreSubjectStatus(subid);
        }catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    /**
     * 查询教师课题数目
     * @paramtid
     * @return
     */
    @Override
    public SubNumByTea getsubnum(String tid, String tname) {
        SubNumByTea subNumByTea = new SubNumByTea();
        subNumByTea.setTid(tid);
        subNumByTea.setTname(tname);
        subNumByTea.setAuditSubSum(subjectMapper.selectAuditSubSum(tid));
        subNumByTea.setSubmitSubSum(subjectMapper.selectSubmitSubSum(tid));
        subNumByTea.setNotAuditSubSum(subjectMapper.selectNotAuditSubSum(tid));
        return subNumByTea;
    }

}
