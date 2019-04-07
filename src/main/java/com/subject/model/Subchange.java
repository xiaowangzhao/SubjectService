package com.subject.model;

import java.util.Date;

public class Subchange {
    private String stuid;

    private Long oldsubid;

    private Long newsubid;

    private String submitflag;

    private String teaconfirm;

    private String auditflag;

    private String operator;

    private Date operatedtime;

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public Long getOldsubid() {
        return oldsubid;
    }

    public void setOldsubid(Long oldsubid) {
        this.oldsubid = oldsubid;
    }

    public Long getNewsubid() {
        return newsubid;
    }

    public void setNewsubid(Long newsubid) {
        this.newsubid = newsubid;
    }

    public String getSubmitflag() {
        return submitflag;
    }

    public void setSubmitflag(String submitflag) {
        this.submitflag = submitflag;
    }

    public String getTeaconfirm() {
        return teaconfirm;
    }

    public void setTeaconfirm(String teaconfirm) {
        this.teaconfirm = teaconfirm;
    }

    public String getAuditflag() {
        return auditflag;
    }

    public void setAuditflag(String auditflag) {
        this.auditflag = auditflag;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperatedtime() {
        return operatedtime;
    }

    public void setOperatedtime(Date operatedtime) {
        this.operatedtime = operatedtime;
    }
}