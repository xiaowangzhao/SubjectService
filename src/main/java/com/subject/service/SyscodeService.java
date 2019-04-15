package com.subject.service;

import com.subject.model.Syscode;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liangqin
 * @date 2019/4/11 9:59
 */
public interface SyscodeService {

    /**
     * 得到code
     * @param codeno
     * @param codevalue
     * @return
     */
    Syscode selectCode( String codeno,  String codevalue);

    /**
     * 通过codeno获得课题列表
     * @param codeno
     * @return
     */
    List<Syscode> selectCodeByCodeno(String codeno);
}
