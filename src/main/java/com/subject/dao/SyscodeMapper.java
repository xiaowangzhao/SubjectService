package com.subject.dao;

import com.subject.model.Syscode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SyscodeMapper {

    /**
     * 根据 codeid 获得 Syscode 信息
     * @param codeid
     * @return
     */
    Syscode selectSyscodeById(Integer codeid);

    /**
     * 得到code
     * @param codeno
     * @param codevalue
     * @return
     */
    Syscode selectCode(@Param("codeno") String codeno, @Param("codevalue") String codevalue);

    /**
     * 得到code
     * @param codeno
     * @param codecontent
     * @return
     */
    Syscode selectCodeByContent(@Param("codeno") String codeno, @Param("codecontent") String codecontent);
    /**
     * 通过codeno获得课题列表
     * @param codeno
     * @return
     */
    List<Syscode> selectCodeByCodeno(String codeno);

    int deleteByPrimaryKey(Long codeid);

    int insert(Syscode record);

    Syscode selectByPrimaryKey(Long codeid);

    List<Syscode> selectAll();

    int updateByPrimaryKey(Syscode record);
}