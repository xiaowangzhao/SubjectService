package com.subject.service.impl;

import com.subject.dao.SysarguMapper;
import com.subject.service.SysarguService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liangqin
 * @date 2019/4/1 19:57
 */
@Service
public class SysarguServiceImpl implements SysarguService {

    @Autowired
    private SysarguMapper sysarguMapper;

    /**
     * 检查毕业设计是否已经开始
     * @return 返回false，还没有开始
     */
    @Override
    public boolean ifStartGraduate() throws ParseException {
        String startdate = sysarguMapper.selectArguvalueByName("startdate");
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date gradstartdate=format.parse(startdate);
        long day=currentDate.getTime()-gradstartdate.getTime();
        if(day<0){
            return false;
        }
        return true;
    }
}
