package com.subject.service;

import com.subject.model.Stusub;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author liangqin
 * @date 2019/4/14 21:07
 */
public interface StusubService {

    /**
     * 学生选题
     * @param stusubs
     * @return
     */
    Map<String, Object> stuSelectSub(List<Stusub> stusubs) throws Exception;

    /**
     * 教师选择学生
     * @param stuid
     * @param subid
     * @param status status为1时，选中学生，status为0时，弃选学生
     */
    void teaPickStu(String stuid, long subid, int status);

    /**
     * 检查学生选题状态
     * @param stuid
     * @return
     */
    String getStuStatus(String stuid);

    /**
     * 获取课题对应的学生
     * @param subid
     * @return
     */
    List getStuBySubid(Long subid) throws IOException;

    /**
     * 获取学生选择的课题号
     * @param stuid
     * @return
     */
    List<Stusub> getStuSubList(String stuid);

    /**
     * 判断课题是否已选
     * @return
     */
    Stusub  whetherSelectSub(Long subid);

    /**
     * 学生落选，重新选择课题时，清空已选的课题
     * @param stuid
     * @return
     */
    int againSelect(String stuid);

    /**
     * 学生换导师
     * @param stuid
     * @param tid
     */
    void changeTutorForStu(String stuid, String tid);

}
