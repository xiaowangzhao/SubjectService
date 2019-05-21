package com.subject.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.subject.Exception.ExceptionEnum;
import com.subject.Exception.ExceptionHandle;
import com.subject.dto.Result;
import com.subject.model.*;
import com.subject.service.ExportService;
import com.subject.service.StusubService;
import com.subject.service.SubjectService;
import com.subject.service.SyscodeService;
import com.subject.util.JsonUtil;
import com.subject.util.ResultUtil;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
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
@CrossOrigin  //开放跨域权限
@RestController
@RequestMapping(value = "/subjectservice")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SyscodeService syscodeService;

    @Autowired
    private StusubService stusubService;

    @Autowired
    private ExceptionHandle exceptionHandle;

    @Autowired
    private ExportService exportService;


    /**
     * 根据 subid 获得 subject 信息
     * @param subid
     * @return
     */
    @RequestMapping(value = "/subjects",method = {RequestMethod.GET})
    public Result getSubject(@RequestParam(value = "subid") long subid) {
        Result result = ResultUtil.success();
        try{
            result = ResultUtil.success(subjectService.getSubject(subid));
        }catch (Exception e) {
            result = exceptionHandle.exceptionGet(e);
        }
        return result;
    }

    /**
     * 根据教师tid得到课题列表
     *
     * @param tid
     * @return
     */

    @RequestMapping(value = "/getsubbytea/{tid}", method = {RequestMethod.GET})
    public Result getSubjects(@PathVariable("tid") String tid) {
        Result result = ResultUtil.success();
        try{
            result = ResultUtil.success(subjectService.getAllinfo(tid));
        }catch (Exception e) {
            result = exceptionHandle.exceptionGet(e);
        }
        return result;
    }



    /**
     * 删除课题，根据id删除课题
     * @param subid
     * @return
     */
    @RequestMapping(value = "/subjects/{id}", method = {RequestMethod.DELETE})
    public Result delSubject(@PathVariable("id") Long subid) {
        Result result = ResultUtil.success();
        try{
            if(subjectService.delSubject(subid) == 1){
                result = ResultUtil.success("删除成功！");
            }
        }catch (Exception e) {
            result = exceptionHandle.exceptionGet(e);
        }
        return result;
    }

    /**
     * 提交课题（修改课题提交标志为1）
     * @param subid
     * @return
     */
    @RequestMapping(value = "/subjects", method = {RequestMethod.PUT})
    public Result submitSubject(@RequestParam("subid") long subid){
        Result result = ResultUtil.success();
        try{
            if(subjectService.getSubject(subid) != null) {
                if(subjectService.submitSubject(subid) != 0){
                    return ResultUtil.success("提交课题状态成功！");
                }
            }else {
                return ResultUtil.error(ExceptionEnum.SUBJECT_NOTEXIST);
            }
        }catch (Exception e) {
            result = exceptionHandle.exceptionGet(e);
        }
        return result;

    }

    /**
     * 提交课题到临时表
     * @param subid
     * @return
     */
    @RequestMapping(value = "/subjects/temp", method = {RequestMethod.GET})
    public Result addSubjectTemp(@RequestParam(value = "subid", required = true) long subid,
                                 @RequestParam(value = "condition", required = true) String condition) {
        Result result = ResultUtil.success();
        try{
            if(condition.equals("0")) {//状态0为转移课题到暂存库
                if(subjectService.getSubTemp(subid) == null) {
                    int status = subjectService.submitSubjectTemp(subjectService.selectBySubid(subid));
                    int status2 = subjectService.delSubjectBySubid(subid);
                    if(status != 0 && status2 != 0 ) {
                        result = ResultUtil.success("转移课题成功！");
                    }
                }else {
                    result = ResultUtil.error(ExceptionEnum.SUBJECT_EXCIT);
                }

            }else if(condition.equals("1")) {//状态1为复制课题到暂存库
                if(subjectService.getSubTemp(subid) == null) {
                    int status = subjectService.submitSubjectTemp(subjectService.selectBySubid(subid));
                    if(status != 0) {
                        result = ResultUtil.success("复制课题成功！");
                    }
                }else {
                    result = ResultUtil.error(ExceptionEnum.SUBJECT_EXCIT);
                }

            }

        }catch (Exception e) {
            result = exceptionHandle.exceptionGet(e);
        }
        return result;

    }

    /**
     * 添加课题,修改课题
     * @param map
     * @return
     */
    @RequestMapping(value = "/subjects", method = {RequestMethod.POST})
    public Result addSubject(@RequestBody Map<String, Object> map) {
        Result result = ResultUtil.success();
        if(!map.isEmpty()) {
            JsonUtil jsonUtil = new JsonUtil();
            //从map中获取对象
            Subject subject = jsonUtil.mapToObject(map, Subject.class, "subject");
            List<Progress> progressList = jsonUtil.mapToList(map, Progress.class, "progress");
            String specid = jsonUtil.mapToString(map, "specid");
            //判断是否为空
            if(subject != null && progressList.size() != 0 && specid != null) {
                if(subject.getSubid() == null) {
                    try{
                        //保存前校验
                        String viewSubjcet = subjectService.validNewSubject(subject);
                       if(viewSubjcet.equals("1")){
                           subjectService.insertSubject(subject, progressList, specid);
                           result =  ResultUtil.success("添加课题成功！");
                       }else{
                           result =  ResultUtil.error(403,viewSubjcet);
                       }
                    }catch (Exception e) {
                        result =  ResultUtil.error(403, "添加课题失败!");
                    }
                }else {
                    try{
                        subjectService.updateSubject(subject, progressList, specid);
                        result =  ResultUtil.success("修改课题成功！");
                    }catch (Exception e) {
                        result = ResultUtil.error(403, "修改课题失败!");
                    }
                }
            }
        }
        return result;
    }

    /**
     * 通过tid， 获取教师所需要盲审的课题
     * @param tid
     * @return
     */
    @RequestMapping(value = "/auditsubject/teacher/{tid}", method = {RequestMethod.GET})
    public Result getTeaReviewSub(@PathVariable("tid") String tid) {
        Result result = ResultUtil.success();
        Map<String, Object> map = new HashMap<>();
        try{
            map = subjectService.getTeaReviewSub(tid);
            if(map == null || map.size() == 0) {
                result =  ResultUtil.success();
            }
            result = ResultUtil.success(map);
        }catch (Exception e) {
            result =   ResultUtil.error(ExceptionEnum.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 更新教师盲审意见
     * @param map
     * @return
     */
    @RequestMapping(value = "/auditsubject/teacher", method = {RequestMethod.POST})
    public Result updateReviewOpinion(@RequestBody Map<String, Object> map) {
        Result result = ResultUtil.success();
        JsonUtil jsonUtil = new JsonUtil();
        List<ReviewSubject> reviewSubjects = jsonUtil.mapToList(map, ReviewSubject.class, "reviewSubjects");
        try{
            subjectService.updateReviewOpinion(reviewSubjects);
            result = ResultUtil.success("修改意见成功!");
        }catch (Exception e) {
            result = ResultUtil.error(ExceptionEnum.OPINOIN_UPDATE_FAIL);
        }
        return result;
    }

    /**
     * 盲审分配
     * @return
     */
    @RequestMapping(value = "/assignsubject", method = {RequestMethod.GET})
    public Result assignSubject() {
        Result result = ResultUtil.success();
        try {
            result =  ResultUtil.success(subjectService.assignSubject());
        } catch (ParseException e) {
            result = ResultUtil.error(ExceptionEnum.SUBJECT_DIS_FAIL);
        }
        return result;
    }

    /**
     * 审核课题成功时传subid, specid,发布课题时传subid, specid, auditflag='1'
     *  审核课题失败时传subid, specid, auditoption
     * @param map
     * @return
     */
    @RequestMapping(value = "/auditsubject", method = {RequestMethod.POST})
    public Result assignSubject(@RequestBody Map<String, Object> map) {
        Result result = ResultUtil.success();
        if(map.size() != 0) {
            JsonUtil jsonUtil = new JsonUtil();
            List<Subspec> subspecList = jsonUtil.mapToList(map, Subspec.class, "subspecs");
            try {
                if(subjectService.auditBarchSub(subspecList) == 0){
                    result =  ResultUtil.error(ExceptionEnum.OPRATION_FAIL);
                }
                result =  ResultUtil.success("操作成功！");
            }catch (Exception e) {
                result =  ResultUtil.error(ExceptionEnum.OPRATION_FAIL);
            }
        }
        return result;
    }

    /**
     * 恢复课题状态到“审核未通过”
     * @param
     * @return
     */
    @RequestMapping(value = "/auditsubject/{id}", method = {RequestMethod.GET})
    public Result assignSubject(@PathVariable("id") long subid) {
        Result result = ResultUtil.success();
        try {
            subjectService.restoreSubjectStatus(subid);
            result =  ResultUtil.success("恢复成功！");
        }catch (Exception e) {
            result = ResultUtil.error(ExceptionEnum.OPRATION_FAIL);
        }
        return result;
    }

    /**
     * 获取教师需审核的课题列表
     * @param
     * @return
     */
    @RequestMapping(value = "/getallsubnum", method = {RequestMethod.GET})
    public Result getAllSubNum() throws IOException {
        Result result = ResultUtil.success();
        try{
            List<SubNumByTea> subNumByTeaList = subjectService.getSubNum();
            result = ResultUtil.success(subNumByTeaList);
        }catch (Exception e) {
            result = exceptionHandle.exceptionGet(e);
        }
        return result;
    }

    /**
     * 获取参数列表
     * @return
     */
    @RequestMapping(value = "/getallcode", method = {RequestMethod.GET})
    public Result getAllcode() {
        Result result = ResultUtil.success();
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
            result = ResultUtil.success(codelist);
        }catch (Exception e) {
            result = ResultUtil.error(ExceptionEnum.SERVER_ERROR);
        }
       return  result;
    }

    /**
     * 获取教师申请的课题数目
     * @param tid
     * @return
     */
    @RequestMapping(value = "/getsubcount/{id}", method = {RequestMethod.GET})
    public Result getSubjectCount(@PathVariable("id") String tid) {
        Result result = ResultUtil.success();
        try{
            result = ResultUtil.success(subjectService.selectSubjectCount(tid));
        }catch (Exception e) {
            result = exceptionHandle.exceptionGet(e);
        }

        return result;
    }

    /**
     * 学生选题
     * @param
     * @return
     */
    @RequestMapping(value = "/select/stu", method = {RequestMethod.POST})
    public Result StuSelectSub(@RequestBody Map<String, Object> map) {
        Result result = ResultUtil.success();
        try{
            JsonUtil jsonUtil = new JsonUtil();
            List<Stusub> stusubs = jsonUtil.mapToList(map, Stusub.class, "stusubs");
            result = ResultUtil.success(stusubService.stuSelectSub(stusubs));
        }catch (Exception e) {
            result = exceptionHandle.exceptionGet(e);
        }
        return result;
    }

    /**
     * 学生选题列表
     * @param stuid
     * @return
     */
    @RequestMapping(value = "/select/sublist", method = {RequestMethod.GET})
    public Result StuSelectSubList(@RequestParam(value = "stuid", required = true) String stuid) {
        Result result = ResultUtil.success();
        try{
            result = ResultUtil.success(subjectService.getStuSubList(stuid));
        }catch (Exception e) {
            result = exceptionHandle.exceptionGet(e);
        }
        return result;
    }

    /**
     * 教师选学生
     * @param stuid 学号
     * @param subid  课题号
     * @param status 状态码 ：status为1时，选中学生，status为0时，弃选学生
     * @return
     */
    @RequestMapping(value = "/select/tea", method = {RequestMethod.GET})
    public Result TeaSelectStu(@RequestParam(value = "stuid", required =true)String stuid,
                               @RequestParam(value = "subid", required =true)long subid,
                               @RequestParam(value = "status", required =true)int status) {
        Result result = ResultUtil.success();
        try{
            stusubService.teaPickStu(stuid, subid, status);
            result = ResultUtil.success("操作成功！");
        }catch (Exception e) {
            result = ResultUtil.error(ExceptionEnum.OPRATION_FAIL);
        }
        return  result;
    }

    /**
     * 通过specid获得课题列表
     * @param specid
     * @param tname
     * @param tdept
     * @param substatus
     * @return
     */
    @RequestMapping(value = "/getsubsbyspec", method = {RequestMethod.GET})
    public Result getSubsBySpec(@RequestParam(value = "specid", required =true) String specid, @RequestParam(value = "tname", required =false) String tname,
                                @RequestParam(value = "tdept", required =false) String tdept, @RequestParam(value = "substatus", required =false) String substatus) {
        Result result = ResultUtil.success();
        List<Subject> subjects;
        try{
            subjects = subjectService.getSubsBySpec(specid, tdept, tname, substatus);
            result = ResultUtil.success(subjects);
        } catch (Exception e) {
            result = ResultUtil.error(ExceptionEnum.SERVER_ERROR);
        }

        return result;
    }

    /**
     * 课题审核查询
     * 按专业、课题名、学生名 查询论文审核信息
     * @param specid
     * @param subname
     * @return
     */
    @RequestMapping(value = "/getsubsbyspecandname", method = {RequestMethod.GET})
    public Result getSubsBySpecAndName(@RequestParam(value = "specid", required =true) String specid,
                                       @RequestParam(value = "subname", required =false) String subname) {
        Result result = ResultUtil.success();
        List<ReviewSubject> reviewSubjects;
        try{
            reviewSubjects = subjectService.getSubsBySpecAndName(specid, subname);
            result = ResultUtil.success(reviewSubjects);
        }catch (Exception e) {
            result =  ResultUtil.error(ExceptionEnum.SUBJECT_SEARCH_FAIL);
        }

        return result;
    }

    /**
     * 获取课题对应的学生
     * @param subid
     * @return
     */
    @RequestMapping(value = "/getstubysubid", method = {RequestMethod.GET})
    public Result getStuBySubid(@RequestParam(value = "subid", required = true) Long subid) {
        Result result = ResultUtil.success();
        try{
            result = ResultUtil.success(stusubService.getStuBySubid(subid));
        }catch (Exception e) {
            exceptionHandle.exceptionGet(e);
        }
        return result;
    }

    /**
     * 学生落选，重新选择课题时，清空已选的课题
     * @param stuid
     * @return
     */
    @RequestMapping(value = "/againselect", method = {RequestMethod.GET})
    public Result againSelect(@RequestParam(value = "stuid", required = true) String stuid){
        Result result = ResultUtil.success();
        try{
            stusubService.againSelect(stuid);
            result = ResultUtil.success("操作成功！");
        }catch (Exception e) {
            result = exceptionHandle.exceptionGet(e);
        }
        return  result;
    }

    /**
     * 按专业获得学生当前选题状态
     * @param specid
     * @param classname
     * @param stustatus
     * @return
     */
    @RequestMapping(value = "/getstusbyspec", method = {RequestMethod.GET})
    public Result getStusBySpec(@RequestParam(value = "specid", required = true) String specid,
                                @RequestParam(value = "classname", required = false) String classname,
                                @RequestParam(value = "stustatus", required = false) String stustatus) {
        Result result = ResultUtil.success();
        try{
            result = ResultUtil.success(subjectService.getStusBySpec(specid, classname, stustatus));
        }catch (Exception e) {
            result = exceptionHandle.exceptionGet(e);
        }
        return result;
    }

    /**
     * 学生换导师
     * @param stuid
     * @param tid
     * @return
     */
    @RequestMapping(value = "/changetutortorstu", method = {RequestMethod.GET})
    public Result changeTutorForStu(@RequestParam(value = "stuid", required = true) String stuid,
                                    @RequestParam(value = "tid", required = true) String tid) {
        Result result = ResultUtil.success();
        try{
            stusubService.changeTutorForStu(stuid, tid);
            result = ResultUtil.success("操作成功！");
        }catch (Exception e) {
            result = exceptionHandle.exceptionGet(e);
        }
        return result;
    }

    /**
     * 导出课题明细表
     * @param specid
     * @param classname
     * @return
     */
    @RequestMapping(value = "/exportstusublist", method = {RequestMethod.GET})
    public void exportStuSubList2(@RequestParam(value = "specid") String specid,
                                  @RequestParam(value = "classname", required = false) String classname,
                                  HttpServletResponse response) {
        try{
            exportService.exportStuSubList(specid, classname, response);
        } catch (Exception e) {
            System.out.println(e);
        }

    }




    /**
     * 导出任务书（根据教师编号导出该教师的所有任务书-一个excel文件）
     * @param tid
     * @return
     */
    @RequestMapping(value = "/exporttaskbooksbytid", method = {RequestMethod.GET})
    public Result exportTaskBooksByTid(@RequestParam(value = "tid") String tid) {
        Result result = ResultUtil.success();
        try{
            exportService.exportTaskBooksByTid(tid);
            result = ResultUtil.success("导出成功！");
        } catch (WriteException e) {
            e.printStackTrace();
        }catch (Exception e) {
            result = exceptionHandle.exceptionGet(e);
        }
        return result;
    }

}
