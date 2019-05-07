package com.subject.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.subject.dao.*;
import com.subject.model.*;
import com.subject.service.StusubService;
import com.subject.service.SubjectService;
import com.subject.service.SysarguService;
import com.subject.util.JsonUtil;
import com.subject.util.SimiComparator;
import com.subject.util.Similarity;
import com.subject.util.TeaSubComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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

    @Autowired
    private StusubMapper stusubMapper;

    @Autowired
    private SyscodeMapper syscodeMapper;

    @Autowired
    private StusubService stusubService;

    /**
     * 根据 subid 获得 subject 信息
     * @param subid
     * @return
     */
    @Override
    public Subject selectBySubid(Long subid) {
        return subjectMapper.getSubject(subid);
    }

    /**
     *
     *根据 subid 获得 subject ,progresslist，speid
     * @param subid
     * @return
     */
    @Override
    public Map<String, Object> getSubject(Long subid) {
        Map<String, Object> map = new HashMap<>();
        try{
            Subject subject = subjectMapper.getSubject(subid);
            List<Progress> progressList = progressMapper.selectProgressList(subid);
            String specid = subspecMapper.selectSpecid(subid);
            map.put("subject", subject);
            map.put("progressList", progressList);
            map.put("specid", specid);
        }catch (Exception e) {
            System.out.println(e);
        }
        return map;
    }

    /**
     * 根据教师信息得到课题列表
     *
     * @param tid
     * @return
     */
    @Override
    public Map<String, Object> getSubjects(String tid) {
        Map<String, Object> map = new HashMap<>();
        List<Subspec> subspecs = new ArrayList<>();
        Subspec subspec = null;
        try{
            List<Subject> subjects = subjectMapper.getSubjects(tid);
            for(Subject subject : subjects) {
                subspec = subspecMapper.selectBySubid(subject.getSubid());
                subspecs.add(subspec);
            }
            map.put("subjects", subjects);
            map.put("subspecs", subspecs);
        }catch (Exception e) {
            map.put("errorMsg", "获取课题信息错误！");
            return map;
        }
        return map;
    }

    /**
     * 通过tsubid获取spec列表里的专业信息
     * @param subid
     * @return
     * @throws IOException
     */
    @Override
    public Map<String, Object> getSpecInformation(Long subid) throws IOException {
        Map<String, Object> map = new HashMap<>();
        JSONArray jsonArray = JsonUtil.getSpec();
        List<Subspec> subspecs = subspecMapper.selectSpecidsBySubid(subid);
        String specName = "";
        String specid = "";
        for(Subspec subspec : subspecs) {
            specid = subspec.getSpecid();
            for(Object obj : jsonArray) {
                JSONObject jsonOb = (JSONObject) obj;
                String specidTemp = jsonOb.getString("specid");
                if(specidTemp.equals(specid)){
                    String specname = jsonOb.getString("specname");
                    specName += specname + " ";
                    map.put("specname", specname);
                    map.put("specName", specName);

                }
            }
        }

        return  map;
    }


    /**
     * 获取教师对应每个课题的状态及可进行的操作情况
     * @param tid
     * @return
     */
    @Override
    public List<Subject> getAllinfo(String tid) {

        List<Subject> subjects = new ArrayList<>();
        try{
            List<Subject> subjectList = subjectMapper.getSubjects(tid);
            for(Subject subject : subjectList) {
                Subject tempSub = subject;
                Long subid = subject.getSubid();
                String submitflag = subject.getStatus();

                //获取课题审核意见
                String reviewOpinion = reviewsubjectMapper.selectReviewBySubid(subid);

                //计算相似的历史课题数
                tempSub.setSimSubsInHis(this.computesimilarWithOldSubs(subject.getTid(), subject.getSubname(), 0.9F));

                tempSub.setReviewopinion(reviewOpinion);

                //获得课题对应专业
                Map<String, Object> specInformationMap = this.getSpecInformation(subid);
                String specName = (String) specInformationMap.get("specName");

                if(submitflag == null || submitflag.equals("")) {
                    tempSub.setCondition("未提交");
                    tempSub.getOperations().add("修改");
                    tempSub.getOperations().add("删除");
                    tempSub.getOperations().add("提交");
                    tempSub.getOperations().add("转移到暂存库");
                }else if(submitflag.equals("1")) {  //已提交
                    String condition = "";
                    //判断是否被选中
                    Stusub stusub = stusubMapper.selectPickflag(subid);
                    if(stusub != null) {
                        condition="已选,"+stusub.getStuid();
                        tempSub.setCondition(condition);


                        String modifyflag = sysarguService.selectSysargu("modifytaskbookflag").getArguvalue();
                        //需增加判断，若允许修改，则显示‘修改任务书’按钮，否则不显示
                        if(modifyflag.equals("1")) {
                            tempSub.getOperations().add("修改任务书");
                            tempSub.getOperations().add("复制到暂存库");
                        }
                        tempSub.getOperations().add("查看设计情况");
                    }else {//课题没有被选中，查看是否已有学生初选
                        int stucount = subjectMapper.selectNotSelectCountSubject(subid);
                        if(stucount != 0) {
                            condition = "初选学生数："+stucount;
                            tempSub.setCondition(condition);
                            tempSub.getOperations().add("选择学生");
                        }else {//课题没有人选，判断是否已审核、已发布

                            String status="";
                            boolean showoptionflag=false;//是否显示“查看审核意见”操作
                            List<Subspec> subspecs = subspecMapper.selectSpecidsBySubid(subid);
                            for(Subspec subspec : subspecs) {

                                String specname = (String) specInformationMap.get("specname");
                                String auditflag = subspec.getAuditflag();
                                String releaseflag = subspec.getReleaseflag();
                                String reviewstr = "<hr>[盲审意见："+reviewOpinion+"]";


                                if(auditflag==null||auditflag.equals("")) {//没有审核
                                    if(reviewOpinion.equals("")) reviewstr="<hr>[等待盲审]";
                                    if(status.equals("")) {
                                        status="("+specname+")审核中…"+reviewstr;
                                    }else{
                                        status=status+"<br>"+"("+specname+")审核中…"+reviewstr;
                                    }
                                }else if(auditflag.equals("0")) {//审核没通过
                                    if(status.equals("")) {
                                        status="("+specname+")审核没通过"+reviewstr;
                                    }else{
                                        status=status+"<br>"+"("+specname+")审核没通过"+reviewstr;
                                    }
                                    showoptionflag=true;//操作中增加查看审核意见
                                }else {
                                    if(releaseflag==null||releaseflag.equals("")){//还未发布
                                        if(status.equals("")) {
                                            status="("+specname+")审核通过";
                                        }else{
                                            status=status+"<br>"+"("+specname+")审核通过";
                                        }
                                    }else {//课题已发布，但是还没有学生选
                                        String endpickingflag = sysarguService.selectSysargu("endpickingflag").getArguvalue();
                                        if(endpickingflag.equals("1")){
                                            status="选题已结束";
                                        }else{
                                            if(status.equals("")) {
                                                status="("+specname+")学生选题中…";
                                            }else{
                                                status=status+"<br>"+"("+specname+")学生选题中…";
                                            }
                                        }
                                    }
                                }
                            }



                            tempSub.setCondition(status);
                            if(showoptionflag) tempSub.getOperations().add("查看审核意见");
                        }
                    }

                }else {//所有专业审核未通过时，submitflag="0",auditflag="0"
                    tempSub.setCondition("审核没通过"+"<hr>[盲审意见："+reviewOpinion+"]");
                    tempSub.getOperations().add("查看审核意见");
                    tempSub.getOperations().add("修改");
                    tempSub.getOperations().add("删除");
                    tempSub.getOperations().add("提交");
                    tempSub.getOperations().add("转移到暂存库");
                }
                subjects.add(tempSub);
            }
        }catch (Exception e) {
            System.out.println("chucuola~");
        }
        return subjects;
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
    public int delSubjectBySubid(Long subid) {
        return subjectMapper.delSubject(subid);
    }

    /**
     * 删除课题,进程，所属专业
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
     * 提交课题到临时表
     *
     * @param subject
     * @return
     */
    @Override
    public int submitSubjectTemp(Subject subject) {
        return subjectMapper.insertSubjectTmep(subject);
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


    /**
     *
     * @param subject
     * @param processList
     * @param specid
     */
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
            String subdirection = "";
            for(int i = 1; i <= 3; i++) {
                if(i == 1){
                    subdirection = "软件";
                }else if(i == 2) {
                    subdirection = "网络";
                }else {
                    subdirection = "硬件";
                }
                List<Subject> subjectList = subjectMapper.selectSubByDirection(subdirection);
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
                if(teasum == 1) return "只有一个教师申请了课题方向为"+subdirection+"的课题，无法进行分配！";
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
                ReviewSubject reviewsubject;
                for(int l = 0; l < subSums; l++) {
                    SubReview reviewTemp = subReviews.get(l);
                    String tid = reviewTemp.getTid();
                    teaSumForReview.put(tid, tid);
                    Long subid = Long.parseLong(reviewTemp.getSubid());
                    reviewsubject = new ReviewSubject();
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
     * 审核课题成功时传subid, specid, auditflag='1' 发布课题时传subid, specid, releaseflag='1'
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
                if(subspec.getAuditflag() != null && subspec.getReleaseflag() == null && subspec.getAuditoption() == null) { //批量审核课题,审核通过
                    return subspecMapper.submitAuditBatchPass(subspecs);
                }
                else if(subspec.getAuditflag() == null && subspec.getReleaseflag() == null && subspec.getAuditoption() != null) { //批量审核课题,审核未通过
                    return subspecMapper.submitAuditBatchUnPass(subspecs);
                }
                else if (subspec.getReleaseflag() != null && subspec.getAuditflag() == null && subspec.getAuditoption() == null) { //批量发布课题
                    return subspecMapper.releasesubject(subspecs);
                }

            }catch (Exception e) {
                System.out.println(e);
            }
        }
        return 0;
    }

    /**
     * 通过tid， 获取教师所需要盲审的课题
     * @param tid
     * @return
     */
    @Override
    public Map<String, Object> getTeaReviewSub(String tid) {
        Map<String, Object> map = new HashMap<>();
        List<ReviewSubject> reviewSubjects;
        List<Subject> subjects = new ArrayList<>();
        List<Subspec> subspecs = new ArrayList<>();
        List<Progress> progresses = new ArrayList<>();
        List progressMap = new ArrayList<>();
        Subspec subspec = null;
        Subject subject = null;
        try{
            reviewSubjects = reviewsubjectMapper.selectByTid(tid);
            for(ReviewSubject reviewSubject : reviewSubjects) {
                long subid = reviewSubject.getSubid();
                subspec = subspecMapper.selectBySubid(subid);
                subject = subjectMapper.getSubject(subid);
                progresses = progressMapper.selectProgressList(subid);
                subspecs.add(subspec);
                subjects.add(subject);
                progressMap.add(progresses);
            }
            map.put("reviewSubjects", reviewSubjects);
            map.put("subjects", subjects);
            map.put("subspecs", subspecs);
            map.put("progressMap", progressMap);
        }catch (Exception e) {
            System.out.println(e);
        }
        return map;
    }

    /**
     * 更新教师盲审意见
     * @param reviewSubjects
     * @return
     */
    @Override
    public int updateReviewOpinion(List<ReviewSubject> reviewSubjects) {
        try{
            reviewsubjectMapper.updateReviewOpinion(reviewSubjects);
        }catch (Exception e) {
            return 0;
        }
        return 1;
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



    /**
     *  保存前校验
     * @param subject
     */
    @Override
    @Transactional
    public String validNewSubject(Subject subject) {
        String tid = subject.getTid();
        String subname = subject.getSubname();
        //判断课题数是否超过10个，若超过，则不允许新增
        int subjectCount = subjectMapper.selectSubjectCount(tid);
        if(subjectCount >= 10) {
            return "您当前库中的课题数已经10个，不允许再次增加！";
        }

        //判断课题是否重复
        if(subjectMapper.selectSubjectRepeat(tid, subname).size() != 0) {
            return "您的课题：'"+subname+"'在当前库中已存在，不允许重复！";
        }

        //题目相似度若超过0.9，则认定为重复
        List<SubSim> subSims = computesimilar(subject.getSubname(), 0.9f);
        if(subSims.size() != 0){
            String errstr = "";
            for(Iterator<SubSim> it = subSims.iterator(); it.hasNext();){
                SubSim subSim = it.next();
                errstr = errstr + "/" + subSim.getTid() + ":" + subSim.getSubname() + "/";
            }
            if(!errstr.equals("")){
                errstr = "[提交失败]系统中已存在"+subSims.size()+"个相同的课题:"+"["+errstr+"]请修改课题名后再提交！";
               return errstr;
            }
        }
        return "1";
    }

    /**
     * 查询课题数量
     * @param tid
     * @return
     */
    @Override
    public int selectSubjectCount(String tid) {
        return subjectMapper.selectSubjectCount(tid);
    }



    /**
     *按专业查看所有课题情况
     * @param specid
     * @param substatus
     * @return
     */
    @Override
    public List<Subject> getSubsBySpec(String specid, String tdept,String tname, String substatus) throws Exception{
        Map<String, Object> map = new HashMap<>();
        List<Subject> subjectList = new ArrayList<>();
        JSONArray jsonArray = JsonUtil.getTeacher();
        if(tdept==null)tdept="";
        if(tname==null)tname="";
        if(substatus==null)substatus="";
        try{
            if(specid == null || specid.equals("")) {
               throw new Exception("按专业查询课题时，专业编号不能为空");
            }
            List<Subject> subjects = subjectMapper.selectSubsBySpec(specid);
            if(subjects.size() != 0){
                Subject temp;
                for(Subject subject : subjects) {
                    temp = subject;
                    long subid = subject.getSubid();
                    String reviewopinion = subjectMapper.selectReviewBySubid(subid);
                    temp.setReviewopinion(reviewopinion);
                    //查询课题历史，确定重复的课题
                    temp.setSimSubsInHis(this.computesimilarWithOldSubs(temp.getTid(), temp.getSubname(), 0.9F));
                    String tid = subject.getTid();
                    String asstid = subject.getAsstid();
                    temp.setTnames(this.getTname(tid, asstid));
                    String tdeptTpost = this.getTeacherInformation(tid);
                    String[] str = tdeptTpost.split(" ");
                    if(str.length != 0) {
                        temp.setTdept(str[0]);
                        temp.setTpost(str[1]);
                    }

                    Subspec subspec = subspecMapper.selectSpec(subid, specid);
                    String auditflag = subspec.getAuditflag();
                    String releaseflag = subspec.getReleaseflag();
                    String auditoption = subspec.getAuditoption();

                    String status = temp.getStatus();
                    if(status == null || status.equals("")){
                        temp.setCondition("未提交");
                    }else if(status.equals("1")){  //课题已提交
                        //判断是否被选中
                        Stusub stusub = stusubMapper.selectPickflag(subid);
                        if(stusub != null) {
                            temp.setCondition("已选/" + stusub.getStuid());
                        }else{ //课题没有被选中，查看是否已有学生初选
                            int stucount = subjectMapper.selectNotSelectCountSubject(subid);
                            if(stucount != 0) {
                                temp.setCondition("已初选/" + stucount);
                            }else {//课题没有人选，判断是否已审核、已发布
                                String reviewstr="[盲审意见："+reviewopinion+"]";
                                if(auditflag==null||auditflag.equals("")){//没有审核
                                    if(reviewopinion == null) {
                                        reviewstr="[等待盲审]";
                                    }
                                    temp.setCondition("已提交-等待审核"+reviewstr);
                                }else if(auditflag.equals("0")){//审核没通过
                                    temp.setCondition("审核没通过/"+auditoption+"[盲审意见："+reviewopinion+"]");
                                }else{
                                    if(releaseflag==null||releaseflag.equals("")){//还未发布
                                        temp.setCondition("审核通过-等待发布");
                                    }else{//课题已发布，但是还没有学生选
                                        temp.setCondition("已发布-等待选题");
                                    }
                                }
                            }
                        }
                    }else {//所有专业审核未通过时，submitflag="0",auditflag="0"
                        temp.setCondition("审核没通过/"+auditoption+"[盲审意见："+reviewopinion+"]");
                    }
                    subjectList.add(temp);
                }
            }


        }catch (Exception e) {
            System.out.println(e + " getSubsBySpec");
        }
        //根据参数substatus值从ret中筛选满足条件的
        List<Subject> result = new ArrayList<Subject>();
        if(substatus.equals("")){
            result = subjectList;
        }else {
            String substatuscodename = syscodeMapper.selectCodeByContent("ktzht", substatus).getCodecontent();
            for(Subject subject : subjectList) {
                String str = subject.getCondition().split("/")[0];
                if(str.length() >= 8 && str.substring(0, 8).equals("已提交-等待审核")) str = str.substring(0, 8);
                if(str.equals(substatuscodename)){
                    result.add(subject);
                }
            }
        }

        return result;
    }

    /**
     * 与教师往年课题（近三年）进行相似度比较
     *  返回相似度大于等于阈值的课题名
     * @param tutorid
     * @param subname
     * @param threshold
     * @return
     */
    @Override
    public List<SubSim> computesimilarWithOldSubs(String tutorid, String subname, float threshold) {
        List<SubSim> subSimList = new ArrayList<>();
        if(subname==null) subname="";
        if(tutorid==null) tutorid="";
        //获得当前毕业设计年份
        try {
            String currentyear = sysarguService.selectSysargu("startdate").getArguvalue().substring(0,3);
            String longyear=String.valueOf(Integer.valueOf(currentyear)-3);//参与比较的最久年份
            List<SubSim> subSims = subjectMapper.selectPastSubject(tutorid, longyear);
            if(subSims.size() != 0){
                for(SubSim subSim : subSims){
                    String subname0 = subSim.getSubname();
                    Float simd = Similarity.calculatesimilar(subname0, subname);
                    if(simd<threshold) continue;
                    SubSim sim = new SubSim();
                    sim.setSubname(subname0);
                    sim.setSimilard(simd);
                    sim.setUsedyear(subSim.getUsedyear());
                    subSimList.add(sim);
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return subSimList;
    }

    /**
     * 通过tid获取教师列表里的信息
     * @param tid
     * @return
     * @throws IOException
     */
    public String getTeacherInformation(String tid) throws IOException {
        JSONArray jsonArray = JsonUtil.getTeacher();
        String tdept = "";
        String tpost = "";
        for(Object obj : jsonArray) {
            JSONObject jsonOb = (JSONObject) obj;
            String tidtemp = jsonOb.getString("tid");
            String tdepttemp = jsonOb.getString("tdept");
            String tposttemp = jsonOb.getString("tpost");
            if(tid.equals(tidtemp)){
                tdept = tdepttemp;
                tpost = tposttemp;
            }
        }
        return  tdept + " " + tpost;
    }

    /**
     * 在教师数据里获取教师名
     * @param tid
     * @param asstid
     * @return
     */
    public String getTname(String tid, String asstid) throws IOException {
        JSONArray jsonArray = JsonUtil.getTeacher();
        String teaname = "";
        for(Object obj : jsonArray) {
            JSONObject jsonOb = (JSONObject) obj;
            String tname = jsonOb.getString("tname");
            String tidtemp = jsonOb.getString("tid");
            if(tid.equals(tidtemp)){
                teaname += tname;
            }
        }
        teaname += "/";
        for(Object obj : jsonArray) {
            JSONObject jsonOb = (JSONObject) obj;
            String tname = jsonOb.getString("tname");
            String asstidtemp = jsonOb.getString("tid");
            if(asstid.equals(asstidtemp)){
                teaname += tname;
            }
        }

        return teaname;
    }

    /**
     * 课题审核查询
     *按专业、课题名、学生名 查询论文审核信息
     * @param specid
     * @param subname
     * @return
     */
    @Override
    public List<ReviewSubject> getSubsBySpecAndName(String specid, String subname) throws IOException {
        List<ReviewSubject> reviewSubjects = subjectMapper.selectSubsBySpecAndName(specid, subname);
        JSONArray jsonArray = JsonUtil.getTeacher();

        for(ReviewSubject reviewSubject : reviewSubjects) {
            String tid2 = reviewSubject.getTid();
            String asstid = reviewSubject.getAsstid();
            String reviewid = reviewSubject.getReviewerid();
            String teaname = this.getTname(tid2, asstid);
            String subnametemp = subjectMapper.getSubject(reviewSubject.getSubid()).getSubname();
            reviewSubject.setSubname(subnametemp);
            for(Object obj : jsonArray) {
                JSONObject jsonOb = (JSONObject) obj;
                String tname = jsonOb.getString("tname");
                String tid = jsonOb.getString("tid");
                if(tid.equals(reviewid)){
                    reviewSubject.setReviewername(tname);
                }
            }
            reviewSubject.setTnames(teaname);
        }

        return reviewSubjects;
    }

    /**
     * 按专业获得学生当前选题状态
     * @param specid
     * @param classname
     * @param stustatus
     * @return
     */
    @Override
    public List getStusBySpec(String specid, String classname, String stustatus) throws IOException, Exception {
        List studentlist = new ArrayList();
        JSONArray jsonArray = JsonUtil.getStudents();
        if(classname==null) classname="";
        if(stustatus==null) stustatus="";
        if(specid==null||specid.equals("")) {
            throw new Exception("专业编号不能为空!");
        }
        for(Object obj : jsonArray) {
            Map<String, Object> map = new HashMap<>();
            JSONObject jsonOb = (JSONObject) obj;
            String stuid = jsonOb.getString("stuid");
            String sname = jsonOb.getString("sname");
            String clazzname = jsonOb.getString("classname");
            String email = jsonOb.getString("email");
            String telphone = jsonOb.getString("email");
            String ssex = jsonOb.getString("ssex");
            map.put("stuid", stuid);
            map.put("sname", sname);
            map.put("clazzname", clazzname);
            map.put("email", email);
            map.put("telphone", telphone);
            map.put("ssex", ssex);
            String status = stusubService.getStuStatus(stuid);
            map.put("status", status);
            if(status.split("/")[0].equals("已选")){
                Subject subject = subjectMapper.getSubject(Long.valueOf(status.split("/")[1]));
                map.put("subject",subject);
            }
            studentlist.add(map);
        }
        return studentlist;
    }

    /**
     * 查询申报了课题方向为value的课题
     * @param subdirection
     * @return
     */
    @Override
    public List<Subject> getSubByDirection(String subdirection) {
        return subjectMapper.selectSubByDirection(subdirection);
    }

    /**
     * 题目相似度比较
     * @param subname 为被比较的课题名
     * @param threshold 相似度的阈值
     * @return  List<SubSimBean> 为与subname比较的题目及相似度，其中相似度大于threshold
     */
    @Override
    public List<SubSim> computesimilar(String subname, float threshold) {
        List<SubSim> ret = new ArrayList<>();
        if(subname == null){
            subname = "";
        }
        List<Subject> subjectList = subjectMapper.selectSubjectSim();
        if(subjectList.size() != 0){
            for(Subject subject : subjectList){
                Float simd = Similarity.calculatesimilar(subject.getSubname(),subname);
                if(simd < threshold) continue;
                String tid = subject.getTid();
                SubSim temp = new SubSim();
                temp.setSubname(subject.getSubname());
                temp.setSimilard(simd);
                temp.setTid(tid);
                ret.add(temp);
            }
        }
        SimiComparator comparator = new SimiComparator();
        Collections.sort(ret,comparator);
        return ret;
    }


}
