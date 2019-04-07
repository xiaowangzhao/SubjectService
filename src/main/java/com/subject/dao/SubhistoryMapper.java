package com.subject.dao;

import com.subject.model.Subhistory;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SubhistoryMapper {
    int deleteByPrimaryKey(@Param("subid") Long subid, @Param("usedyear") String usedyear);

    int insert(Subhistory record);

    Subhistory selectByPrimaryKey(@Param("subid") Long subid, @Param("usedyear") String usedyear);

    List<Subhistory> selectAll();

    int updateByPrimaryKey(Subhistory record);
}