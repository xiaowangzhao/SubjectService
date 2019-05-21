package com.subject.dao;

import com.subject.model.Stusub;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Transactional
public interface StusubMapper {

    int deleteStusub(String stuid);

    int insertStuSub(Stusub stusub);

    /**
     * 学生选题
     * @param stusubs
     * @return
     */
    int insertBatchStuSub(@Param("stusubs") List<Stusub> stusubs);

    /**
     * 教师选择学生，将pickflag设为1
     * @param
     * @return
     */
    int succPickStu(@Param("stuid") String stuid, @Param("subid") long subid);

    /**
     * 学生落选，将pickflag设为0
     * @param subid
     * @return
     */
    int defeatStu(long subid);
    /**
     * 教师弃选学生，将pickflag设为0
     * @param
     * @return
     */
    int failPickStu(@Param("stuid") String stuid, @Param("subid") long subid);

    /**
     *查看学生是否重复选题
     * @param stuid
     * @return
     */
    Stusub selectStusub(long stuid);

    /**
     * 查询pickflag是否为1
     * @param subid
     * @return
     */
    Stusub selectPickflag(long subid);

    /**
     * 检查学生选题状态
     * @param stuid
     * @return
     */
    List<Stusub> selectStuStatus(String stuid);

    /**
     * 获取课题对应的学生
     * @param subid
     * @return
     */
    List<Stusub> selectStuBySubid(Long subid);

    /**
     * 判断课题是否已选
     * @return
     */
    Stusub  whetherSelectSub(Long subid);

    /**
     * 查询学生已选课题
     * @param stuid
     * @return
     */
    Long selectSub(String stuid);

    /**
     * 学生落选，重新选择课题时，清空已选的课题
     * @param stuid
     * @return
     */
    int againSelect(String stuid);

    List<Stusub> selectAll();

    int updateByPrimaryKey(Stusub record);


}