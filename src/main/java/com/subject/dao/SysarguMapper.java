package com.subject.dao;

import com.subject.model.Sysargu;
import java.util.List;

public interface SysarguMapper {
    int deleteByPrimaryKey(Long arguid);

    int insert(Sysargu record);

    Sysargu selectByPrimaryKey(Long arguid);

    List<Sysargu> selectAll();

    int updateByPrimaryKey(Sysargu record);

    /**
     * 通过参数名查找参数值
     * @param arguname
     * @return
     */
    String selectArguvalueByName(String arguname);
}