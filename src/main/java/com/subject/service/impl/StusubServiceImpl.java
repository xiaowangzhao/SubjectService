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
            e.printStackTrace();
            return "选题失败";
        }
        return "选题成功";
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
        try{
            if(status == 1) {
                int test1 = stusubMapper.succPickStu(stuid, subid);
                int test3 = stusubMapper.deleteStusub(stuid);
                int test2 = stusubMapper.defeatStu(subid);
            }else if(status == 0) {
                stusubMapper.failPickStu(stuid, subid);
            }
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * 检查学生选题状态
     * @param stuid
     * @return
     */
    @Override
    public String getStuStatus(String stuid) {
        String status = null;
        List<Stusub> stusubs = stusubMapper.selectStuStatus(stuid);
        int size = stusubs.size();
        if(stusubs == null || stusubs.size() == 0) {
            status = "未选";
            return status;
        }
        int unpasssubcount=0;//落选课题数
        boolean assignflag=false;//需指派标志
        for(Stusub stusub : stusubs) {
            String pickflag = stusub.getPickflag();
            long subid = stusub.getSubid();
            if(pickflag==null||pickflag.equals("")) {
                if(subid!=0){
                    status="已初选";
                    break;
                }else{
                    assignflag=true;
                    break;
                }
            }else if(pickflag.equals("1")){
                status="已选/"+String.valueOf(subid);
                break;
            }else if(pickflag.equals("0")){
                unpasssubcount++;
            }
            if(assignflag) {
                if(size == 1) {
                    status = "需指派";
                }else if((size-1) == unpasssubcount){
                    status = "落选-需指派";
                }
            }else{
                if(size == unpasssubcount)	status="落选-需重选";
            }
        }
        return status;
    }
}
