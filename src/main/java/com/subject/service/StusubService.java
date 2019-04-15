package com.subject.service;

import com.subject.model.Stusub;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    String insertBatchStuSub(List<Stusub> stusubs);

    /**
     * 教师选择学生
     * @param stuid
     * @param subid
     * @param status status为1时，选中学生，status为0时，弃选学生
     */
    void teaPickStu(String stuid, long subid, int status);
}
