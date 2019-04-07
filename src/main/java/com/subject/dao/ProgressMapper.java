package com.subject.dao;

import com.subject.model.Progress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface ProgressMapper {

    /**
     * 删除进度
     * @param subid
     * @return
     */
    int deleteProgressBySubid(Long subid);

    /**
     * 增加进度
     * @param progress
     * @return
     */
    int insertProgress(Progress progress);

    /**
     * 批量增加进度
     * @param progress
     * @return
     */
    @Transactional
    int addProgress(@Param("progress") List<Progress> progress);

    /**
     *
     * @param subid
     * @param inorder
     * @return
     */
    long selectProgress(long subid, int inorder);

    Progress selectByPrimaryKey(Long proid);

    List<Progress> selectAll();

    /**
     *修改进度
     * @param progress
     * @return
     */
    int updateProgress(@Param("progress") Progress progress);
}