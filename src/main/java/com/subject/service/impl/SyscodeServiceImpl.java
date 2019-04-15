package com.subject.service.impl;

import com.subject.dao.SyscodeMapper;
import com.subject.model.Syscode;
import com.subject.service.SyscodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liangqin
 * @date 2019/4/11 10:00
 */
@Service
public class SyscodeServiceImpl implements SyscodeService {

    @Autowired
    private SyscodeMapper syscodeMapper;

    @Override
    public Syscode selectCode(String codeno, String codevalue) {
        return syscodeMapper.selectCode(codeno, codevalue);
    }

    @Override
    public List<Syscode> selectCodeByCodeno(String codeno) {
        return syscodeMapper.selectCodeByCodeno(codeno);
    }
}
