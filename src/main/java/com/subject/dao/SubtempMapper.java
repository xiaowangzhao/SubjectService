package com.subject.dao;

import com.subject.model.Subtemp;
import java.util.List;

public interface SubtempMapper {
    int deleteByPrimaryKey(Long subid);

    int insert(Subtemp record);

    Subtemp selectSubjectTemp(Long subid);

    List<Subtemp> selectAll();

    int updateByPrimaryKey(Subtemp record);
}