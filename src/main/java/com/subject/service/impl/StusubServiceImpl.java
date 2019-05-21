package com.subject.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.subject.Exception.DescribeException;
import com.subject.Exception.ExceptionEnum;
import com.subject.Exception.ExceptionHandle;
import com.subject.dao.StusubMapper;
import com.subject.model.Stusub;
import com.subject.service.StusubService;
import com.subject.service.SubjectService;
import com.subject.util.GetDataUtil;
import com.subject.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liangqin
 * @date 2019/4/14 21:09
 */
@Service
public class StusubServiceImpl implements StusubService {

    @Autowired
    private StusubMapper stusubMapper;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ExceptionHandle exceptionHandle;



    /**
     * 学生选题
     * @param stusubs
     * @return
     */
    @Override
    @Transactional
    public Map<String, Object> stuSelectSub(List<Stusub> stusubs) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String stuid = stusubs.get(0).getStuid();
        long subid;

        for(Stusub stusub : stusubs) {
            subid = stusub.getSubid();
            if(this.whetherSelectSub(subid) != null) {
                throw new DescribeException(ExceptionEnum.SUBJECT_BEENSELECT);
            }
        }
        int count = stusubMapper.insertBatchStuSub(stusubs);
        if(count != 0) {
            String status = "1";  // 选题状态，1为选题成功，0为选题失败
            List stuSubList = subjectService.getStuSubList(stuid);
            map.put("status", status);
            map.put("stuSubList", stuSubList);
        }else {
            throw new DescribeException(ExceptionEnum.SUBJECT_BEENSELECT);
        }
        return map;
    }

    /**
     * 教师选择学生
     * @param stuid
     * @param subid
     * @param status status为1时，选中学生，status为0时，弃选学生
     *  教师选择学生，将pickflag设为1，没选中的都至为0，   教师弃选学生，将pickflag设为0
     */
    @Override
    @Transactional
    public void teaPickStu(String stuid, long subid, int status) {
        if(status == 1) {
            String condition = this.getStuStatus(stuid);
            if(condition.equals("2")){ //判断学生是否被其他教师选中
                throw new DescribeException(ExceptionEnum.STUDENT_BEENSELECT);
            }
            stusubMapper.succPickStu(stuid, subid);
            stusubMapper.deleteStusub(stuid);
            stusubMapper.defeatStu(subid);
        }else if(status == 0) {
            stusubMapper.failPickStu(stuid, subid);
        }
    }

    /**
     * 检查学生选题状态
     * @param stuid
     * status ----> 未选：0， 已初选：1， 已选：2，需指派：3，落选-需指派：4，落选-需重选：5
     * @return
     */
    @Override
    public String getStuStatus(String stuid) {
        String status = null;
        List<Stusub> stusubs = stusubMapper.selectStuStatus(stuid);
        int size = stusubs.size();
        if(stusubs == null || stusubs.size() == 0) {
            status = "0";//未选
            return status;
        }
        int unpasssubcount=0;//落选课题数
        boolean assignflag=false;//需指派标志
        for(Stusub stusub : stusubs) {
            String pickflag = stusub.getPickflag();
            long subid = stusub.getSubid();
            if(pickflag==null||pickflag.equals("")) {
                if(subid!=0){
                    status="1";//已初选
                    break;
                }else{
                    assignflag=true;
                    break;
                }
            }else if(pickflag.equals("1")){
                status="2";//已选
                break;
            }else if(pickflag.equals("0")){
                unpasssubcount++;
            }
            if(assignflag) {
                if(size == 1) {
                    status = "3";//需指派
                }else if((size-1) == unpasssubcount){
                    status = "4";//落选-需指派
                }
            }else{
                if(size == unpasssubcount)	status="5";//落选-需重选
            }
        }
        return status;
    }

    /**
     * 通过subid获取学生列表里的学生信息
     * @return
     * @throws IOException
     */
    public List getStudentInformation(Long subid) throws IOException {

        JSONArray jsonArray = GetDataUtil.getStudent();
        List<Stusub> stusubs = stusubMapper.selectStuBySubid(subid);
        List students = new ArrayList();
        String classname = "";
        String sname = "";
        for(Stusub stusub : stusubs) {
            String stuid = stusub.getStuid();
            for(Object obj : jsonArray) {
                JSONObject jsonOb = (JSONObject) obj;
                String sidTemp = jsonOb.getString("stuid");
                if(stuid.equals(sidTemp)){
                    classname = jsonOb.getString("classname");
                    sname = jsonOb.getString("sname");
                    Map<String, Object> map = new HashMap<>();
                    map.put("stuid", stuid);
                    map.put("classname", classname);
                    map.put("sname", sname);
                    students.add(map);
                }
            }
        }
        return  students;
    }

    /**
     * 获取课题对应的学生
     * @param subid
     * @return
     */
    @Override
    public List getStuBySubid(Long subid) throws IOException {
        return this.getStudentInformation(subid);
    }

    /**
     * 获取学生选择的课题号
     * @param stuid
     * @return
     */
    @Override
    public List<Stusub> getStuSubList(String stuid) {

        return stusubMapper.selectStuStatus(stuid);
    }
    /**
     * 判断课题是否已选
     * @return
     */
    @Override
    public Stusub whetherSelectSub(Long subid) {
        return stusubMapper.whetherSelectSub(subid);
    }

    /**
     * 学生落选，重新选择课题时，清空已选的课题
     * @param stuid
     * @return
     */
    @Override
    public int againSelect(String stuid) {
        int count = stusubMapper.againSelect(stuid);
        if(count == 0) {
            throw new DescribeException(ExceptionEnum.SERVER_FAIL);
        }
        return count;
    }

    /**
     * 学生换导师
     * @param stuid
     * @param tid
     */
    @Override
    public void changeTutorForStu(String stuid, String tid) {
        if(stuid.equals("") || stuid == null || tid.equals("") || tid == null) {
            throw new DescribeException(ExceptionEnum.STUIDTID_NOTNULL);
        }
        //确定导师编号存在
        String turl = "http://localhost:8089/teacher/getteabytno?tno=" + tid;
        String tJson = GetDataUtil.getData(turl);
        String teacher = JsonUtil.JsonToObj(tJson);
        if(teacher == null) throw new DescribeException(ExceptionEnum.TEACHER_NOTNULL);

        //确定学生已经选题
        String subid = String.valueOf(stusubMapper.selectSub(stuid));
        if(subid == null || subid.equals("")) throw new DescribeException(ExceptionEnum.STUDENT_NOT_COMSUB);

        try{
            subjectService.updateSubTeacher(Long.valueOf(subid), tid);
        }catch (Exception e) {
            throw new DescribeException(ExceptionEnum.SERVER_ERROR);
        }
    }
}
