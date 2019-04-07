package com.subject.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.subject.dto.Result;
import com.subject.model.Progress;
import com.subject.model.SubNumByTea;
import com.subject.model.Subject;
import com.subject.model.Subspec;
import com.subject.service.SubjectService;
import com.subject.util.JsonUtil;
import com.subject.util.ResultUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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

    @CrossOrigin
    @RequestMapping(value = "/subjects",method = {RequestMethod.GET})
    public Result getSubjects(@PathVariable("id") long subid) {
        if(subjectService.getSubject(subid) != null) {
            return ResultUtil.success(subjectService.selectAll());
        }else {
            return ResultUtil.error(403,"查询失败!");
        }
    }


    /**
     * 根据 subid 获得 subject 信息
     * @param subid
     * @return
     */
    @RequestMapping(value = "/subjects/{id}",method = {RequestMethod.GET})
    public Result getSubject(@PathVariable("id") long subid) {
        if(subjectService.getSubject(subid) != null) {
            return ResultUtil.success(subjectService.getSubject(subid));
        }else {
            return ResultUtil.error(403,"查询失败!");
        }
    }

    /**
     * 根据教师信息得到课题列表
     *
     * @param tid
     * @return
     */
    @CrossOrigin  //
    @RequestMapping(value = "/getsubbytea/{id}", method = {RequestMethod.GET})
    public Result getSubjects(@PathVariable("id") String tid) {
        if(!subjectService.getSubjects(tid).isEmpty()){
            return ResultUtil.success(subjectService.getSubjects(tid));
        }else {
            return ResultUtil.error(403,"查询失败！");
        }
    }

    /**
     * 删除课题
     * @param subid
     * @return
     */
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
     * 添加课题,修改课题
     * @param map
     * @return
     */
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
                        subjectService.insertSubject(subject, progressList, specid);
                        return ResultUtil.success("添加课题成功！");
                    }catch (Exception e) {
                        return ResultUtil.error(403, "添加课题失败！ " + e);
                    }
                }else {
                    try{
                        subjectService.updateSubject(subject, progressList, specid);
                        return ResultUtil.success("修改课题成功！");
                    }catch (Exception e) {
                        return ResultUtil.error(403, "修改课题失败！ " + e);
                    }
                }
            }
        }
        return ResultUtil.error(403, "课题不能为空！");
    }

    /**
     * 盲审分配
     * @return
     */
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
     * 获取教师列表
     * @param
     * @return
     */
    @RequestMapping(value = "/getAllsubnum", method = {RequestMethod.GET})
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
}
