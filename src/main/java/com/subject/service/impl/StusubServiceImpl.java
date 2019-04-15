package com.subject.service.impl;

import com.subject.dao.StusubMapper;
import com.subject.model.Stusub;
import com.subject.service.StusubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liangqin
 * @date 2019/4/14 21:09
 */
@Service
public class StusubServiceImpl implements StusubService {

    @Autowired
    private StusubMapper stusubMapper;

    /**
     * 学生选题
     * @param stusubs
     * @return
     */
    @Override
    @Transactional
    public String insertBatchStuSub(List<Stusub> stusubs) {
        Long subid = stusubs.get(0).getSubid();
        if(stusubs==null||stusubs.size()==0) return "课题不能为空";
        try{
            if(subid != null) {
                if(stusubMapper.selectStusub(subid) != null ) return "学生" + subid + "的选题已存在，不允许再次选择！";
            }
            stusubMapper.insertBatchStuSub(stusubs);
        }catch (Exception e) {
            return "选题失败";
        }
        return "选题成功";
    }

    /**
     * 教师选择学生
     * @param stuid
     * @param subid
     * @param status status为1时，选中学生，status为0时，弃选学生
     */
    @Override
    @Transactional
    public void teaPickStu(String stuid, long subid, int status) {
        try{
            if(status == 1) {
                stusubMapper.succPickStu(stuid, subid);
                stusubMapper.deleteStusub(stuid);
            }else if(status == 0) {
                stusubMapper.failPickStu(stuid, subid);
            }
        }catch (Exception e) {
            return;
        }
    }
}
