package com.subject.model;

/**
 * @author liangqin
 * @date 2019/4/5 11:12
 */
public class SubNumByTea {

    String tid;//教师编号
    String tname;//教师名
    int submitSubSum;//提交的课题数目
    int auditSubSum;//需审核的课题数目
    int notAuditSubSum;//未被审核的课题数目

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public int getSubmitSubSum() {
        return submitSubSum;
    }

    public void setSubmitSubSum(int submitSubSum) {
        this.submitSubSum = submitSubSum;
    }

    public int getAuditSubSum() {
        return auditSubSum;
    }

    public void setAuditSubSum(int auditSubSum) {
        this.auditSubSum = auditSubSum;
    }

    public int getNotAuditSubSum() {
        return notAuditSubSum;
    }

    public void setNotAuditSubSum(int notAuditSubSum) {
        this.notAuditSubSum = notAuditSubSum;
    }
}
