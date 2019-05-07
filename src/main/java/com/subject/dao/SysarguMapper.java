package com.subject.dao;

import com.subject.model.Sysargu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysarguMapper {
    int deleteByPrimaryKey(Long arguid);

    int insert(Sysargu record);

    Sysargu selectSysargu(String arguname);

    List<Sysargu> selectAll();

    int updateByPrimaryKey(Sysargu record);

    /**
     * 通过参数名查找参数值
     * @param arguname
     * @return
     */
    String selectArguvalueByName(String arguname);

    //按名字获得系统参数条目
    Sysargu getSysargu(@Param("arguname") String arguname);
}