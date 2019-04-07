package com.subject.dao;

import com.subject.model.Stusub;
import java.util.List;

public interface StusubMapper {
    int deleteByPrimaryKey(Long stusubid);

    int insert(Stusub record);

    Stusub selectByPrimaryKey(Long stusubid);

    List<Stusub> selectAll();

    int updateByPrimaryKey(Stusub record);
}