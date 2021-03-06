package com.subject.dao;

import com.subject.model.Subject;
import com.subject.model.Subspec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
@Repository
public interface SubspecMapper {

    /**
     * 删除
     * @param subid
     * @return
     */
    int deleteBySubid(Long subid);

    /**
     * 增加课题专业
     * @param subspec
     * @return
     */
    @Transactional
    int insertSubspec(Subspec subspec);

    /**
     * 查找spec
     * @param subid
     * @param specid
     * @return
     */
    Subspec selectSpec(@Param("subid") long subid, @Param("specid") String specid);

    /**
     * 查询课题对应的专业号列表
     * @param subid
     * @return
     */
    List<Subspec> selectSpecidsBySubid(Long subid);

    Subspec selectBySubid(Long subid);

    String selectSpecid(Long subid);

    List<Subspec> selectAll();

    /**
     * 通过subid获取审核意见
     * @param subid
     * @return
     */
    String selectAuditoption(Long subid);

    /**
     * 更新
     * @param subspec
     * @return
     */
    int updateSubspec(@Param("subspec") Subspec subspec);

    /**
     * 批量审核课题(审核通过)
     * @param subspecs
     * @return
     */
    int submitAuditBatchPass(@Param("list") List<Subspec> subspecs);

    /**
     *批量审核课题(审核未通过)
     * @param subspecs
     * @return
     */
    int submitAuditBatchUnPass(@Param("list") List<Subspec> subspecs);

    /**
     * 批量发布课题
     *
     * @param subspecs
     * @param
     * @return
     */
    int releasesubject(@Param("list") List<Subspec> subspecs);

    /**
     * 恢复课题状态到“审核未通过”
     *
     * @param subid
     */
    int restoreSubjectStatus(Long subid);

}