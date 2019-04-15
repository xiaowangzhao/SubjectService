package com.subject.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.subject.dto.Result;
import com.subject.model.*;
import com.subject.service.StusubService;
import com.subject.service.SubjectService;
import com.subject.service.SyscodeService;
import com.subject.util.JsonUtil;
import com.subject.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.jws.Oneway;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liangqin
 * @date 2019/3/23 9:47
 */
@RestController
@RequestMapping(value = "/subjectservice")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SyscodeService syscodeService;

    @Autowired
    private StusubService stusubService;

    @CrossOrigin
    @RequestMapping(value = "/subjects",method = {RequestMethod.GET})
    public Result getSubjects() {
        return ResultUtil.success(subjectService.selectAll());
    }


    /**
     * 根据 subid 获得 subject 信息
     * @param subid
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/subjects/{id}",method = {RequestMethod.GET})
    public Result getSubject(@PathVariable("id") long subid) {
        return ResultUtil.success(subjectService.getSubject(subid));
    }

    /**
     * 根据教师信息得到课题列表
     *
     * @param tid
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/getsubbytea/{tid}", method = {RequestMethod.GET})
    public Result getSubjects(@PathVariable("tid") String tid) {
        return ResultUtil.success(subjectService.getSubjects(tid));
    }



    /**
     * 删除课题，根据id删除课题
     * @param subid
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/subjects/{id}", method = {RequestMethod.DELETE})
    public Result delSubject(@PathVariable("id") Long subid) {
        if(subjectService.delSubject(subid) == 1){
            return ResultUtil.success("删除成功！");
        }else {
            return ResultUtil.error(403,"删除失败");
        }
    }

    /**
     * 提交课题（修改课题提交标志为1）
     * @param subid
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/subjects/{id}", method = {RequestMethod.PUT})
    public Result submitSubject(@PathVariable("id") long subid){
        if(subjectService.getSubject(subid) != null) {
            if(subjectService.submitSubject(subid) != 1){
                return ResultUtil.error(403,"提交课题状态失败！");
            }else {
                return ResultUtil.success("提交课题状态成功！");
            }
        }else {
            return ResultUtil.error(403,"提交课题状态失败！");
        }
    }

    /**
     * 提交课题到临时表
     * @param subid
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/subjects/temp/{subid}", method = {RequestMethod.GET})
    public Result addSubjectTemp(@PathVariable("subid") long subid) {
        try{
            subjectService.submitSubjectTemp(subjectService.selectBySubid(subid));
            subjectService.delSubjectBySubid(subid);
        }catch (Exception e) {
            return ResultUtil.error(403, "转移课题失败！");
        }
        return ResultUtil.success("转移课题成功！");

    }

    /**
     * 添加课题,修改课题
     * @param map
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/subjects", method = {RequestMethod.POST})
    public Result addSubject(@RequestBody Map<String, Object> map) {
        if(!map.isEmpty()) {
            JsonUtil jsonUtil = new JsonUtil();
            Subject subject = jsonUtil.mapToObject(map, Subject.class, "subject");
            List<Progress> progressList = jsonUtil.mapToList(map, Progress.class, "progress");
            String specid = jsonUtil.mapToString(map, "specid");
            if(subject != null && progressList.size() != 0 && specid != null) {
                if(subject.getSubid() == null) {
                    try{
                        String viewSubjcet = subjectService.validNewSubject(subject);
                       if(viewSubjcet.equals("1")){
                           subjectService.insertSubject(subject, progressList, specid);
                           return ResultUtil.success("添加课题成功！");
                       }else{
                           return ResultUtil.error(403,viewSubjcet);
                       }
                    }catch (Exception e) {
                        return ResultUtil.error(403, "添加课题失败！");
                    }
                }else {
                    try{
                        subjectService.updateSubject(subject, progressList, specid);
                        return ResultUtil.success("修改课题成功！");
                    }catch (Exception e) {
                        return ResultUtil.error(403, "修改课题失败！ ");
                    }
                }
            }
        }
        return ResultUtil.error(403, "课题不能为空！");
    }

    /**
     * 通过tid， 获取教师所需要盲审的课题
     * @param tid
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/auditsubject/teacher/{tid}", method = {RequestMethod.GET})
    public Result getTeaReviewSub(@PathVariable("tid") String tid) {
        Map<String, Object> map = new HashMap<>();
        try{
            map = subjectService.getTeaReviewSub(tid);
            if(map == null || map.size() == 0) {
                return ResultUtil.success();
            }

        }catch (Exception e) {
            return  ResultUtil.error(403, "系统错误！");
        }
        return ResultUtil.success(map);
    }

    /**
     * 更新教师盲审意见
     * @param map
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/auditsubject/teacher", method = {RequestMethod.POST})
    public Result updateReviewOpinion(@RequestBody Map<String, Object> map) {
        JsonUtil jsonUtil = new JsonUtil();
        List<ReviewSubject> reviewSubjects = jsonUtil.mapToList(map, ReviewSubject.class, "reviewSubjects");
        try{
            subjectService.updateReviewOpinion(reviewSubjects);
        }catch (Exception e) {
            return ResultUtil.error(403, "修改意见失败！");
        }
        return ResultUtil.success("修改意见成功!");
    }

    /**
     * 盲审分配
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/assignsubject", method = {RequestMethod.GET})
    public Result assignSubject() {
        try {
            return ResultUtil.success(subjectService.assignSubject());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResultUtil.error(403, "课题分配失败！");
    }

    /**
     * 审核课题成功时传subid, specid,发布课题时传subid, specid, auditflag='1'
     *  审核课题失败时传subid, specid, auditoption
     * @param map
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/auditsubject", method = {RequestMethod.POST})
    public Result assignSubject(@RequestBody Map<String, Object> map) {
        if(map.size() != 0) {
            JsonUtil jsonUtil = new JsonUtil();
            List<Subspec> subspecList = jsonUtil.mapToList(map, Subspec.class, "subspecs");
            try {
                subjectService.auditBarchSub(subspecList);
                return ResultUtil.success("操作成功！");
            }catch (Exception e) {
                return ResultUtil.error(403, "操作失败！");
            }
        }
        return ResultUtil.error(403, "审核课题不能为空！");
    }

    /**
     * 恢复课题状态到“审核未通过”
     * @param
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/auditsubject/{id}", method = {RequestMethod.GET})
    public Result assignSubject(@PathVariable("id") long subid) {
        try {
            subjectService.restoreSubjectStatus(subid);
            return ResultUtil.success("恢复成功！");
        }catch (Exception e) {
            System.out.println(e);
        }
        return ResultUtil.error(403, "操作失败！");
    }

    /**
     * 获取教师需审核的课题列表
     * @param
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/getallsubnum", method = {RequestMethod.GET})
    public Result getAllsubnum() throws IOException {
        JSONArray jsonArray = JsonUtil.getTeacher();
        SubNumByTea subNumByTea;
        List<SubNumByTea> subNumByTeaList = new ArrayList<>();
        try{
            for(Object obj : jsonArray) {
                JSONObject jsonOb = (JSONObject) obj;
                String tname = jsonOb.getString("tname");
                String tid = jsonOb.getString("tid");
                subNumByTea = subjectService.getsubnum(tid, tname);
                subNumByTeaList.add(subNumByTea);
            }
        }catch (Exception e) {
            System.out.println(e);
        }
        return ResultUtil.success(subNumByTeaList);
    }

    /**
     * 获取参数列表
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/getallcode", method = {RequestMethod.GET})
    public Result getAllcode() {
        List codelist = new ArrayList();
        try{
            List<Syscode> ktlb = syscodeService.selectCodeByCodeno("ktlb");
            List<Syscode> ktxz = syscodeService.selectCodeByCodeno("ktxz");
            List<Syscode> ktly = syscodeService.selectCodeByCodeno("ktly");
            List<Syscode> ktlx = syscodeService.selectCodeByCodeno("ktlx");
            List<Syscode> ktfx = syscodeService.selectCodeByCodeno("ktfx");
            codelist.add(ktlb);
            codelist.add(ktxz);
            codelist.add(ktly);
            codelist.add(ktlx);
            codelist.add(ktfx);
        }catch (Exception e) {
            return ResultUtil.error(403, "系统错误！");
        }
       return  ResultUtil.success(codelist);
    }

    /**
     * 获取教师申请的课题数目
     * @param tid
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/getsubcount/{id}", method = {RequestMethod.GET})
    public Result getSubjectCount(@PathVariable("id") String tid) {
        int count = subjectService.selectSubjectCount(tid);
        return ResultUtil.success(count);
    }

    /**
     * 学生选题
     * @param
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/selectedtopic", method = {RequestMethod.POST})
    public Result getSubjectCount(@RequestBody Map<String, Object> map) {
        String result = null;
        try{
            JsonUtil jsonUtil = new JsonUtil();
            List<Stusub> stusubs = jsonUtil.mapToList(map, Stusub.class, "stusubs");
            result = stusubService.insertBatchStuSub(stusubs);
            if(result.equals("选题成功")) {
                return ResultUtil.success(result);
            }
        }catch (Exception e) {
            return ResultUtil.error(403, "选题错误！");
        }
        return ResultUtil.error(403, result);
    }
}
