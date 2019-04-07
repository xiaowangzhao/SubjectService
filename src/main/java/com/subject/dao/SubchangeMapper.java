package com.subject.dao;

import com.subject.model.Subchange;
import java.util.List;

public interface SubchangeMapper {
    int deleteByPrimaryKey(String stuid);

    int insert(Subchange record);

    Subchange selectByPrimaryKey(String stuid);

    List<Subchange> selectAll();

    int updateByPrimaryKey(Subchange record);
}