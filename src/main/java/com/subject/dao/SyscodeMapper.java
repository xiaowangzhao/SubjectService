package com.subject.dao;

import com.subject.model.Syscode;
import java.util.List;

public interface SyscodeMapper {
    int deleteByPrimaryKey(Long codeid);

    int insert(Syscode record);

    Syscode selectByPrimaryKey(Long codeid);

    List<Syscode> selectAll();

    int updateByPrimaryKey(Syscode record);
}