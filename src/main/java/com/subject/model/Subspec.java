package com.subject.model;

import java.util.Date;

public class Subspec {
    private Long subspecid;

    private Long subid;

    private String specid;

    private String auditoption;

    private String auditflag;

    private String releaseflag;

    private String operator;

    private Date operatedtime;

    private String remark;

    public Long getSubspecid() {
        return subspecid;
    }

    public void setSubspecid(Long subspecid) {
        this.subspecid = subspecid;
    }

    public Long getSubid() {
        return subid;
    }

    public void setSubid(Long subid) {
        this.subid = subid;
    }

    public String getSpecid() {
        return specid;
    }

    public void setSpecid(String specid) {
        this.specid = specid;
    }

    public String getAuditoption() {
        return auditoption;
    }

    public void setAuditoption(String auditoption) {
        this.auditoption = auditoption;
    }

    public String getAuditflag() {
        return auditflag;
    }

    public void setAuditflag(String auditflag) {
        this.auditflag = auditflag;
    }

    public String getReleaseflag() {
        return releaseflag;
    }

    public void setReleaseflag(String releaseflag) {
        this.releaseflag = releaseflag;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Subspec{" +
                "subspecid=" + subspecid +
                ", subid=" + subid +
                ", specid='" + specid + '\'' +
                ", auditoption='" + auditoption + '\'' +
                ", auditflag='" + auditflag + '\'' +
                ", releaseflag='" + releaseflag + '\'' +
                ", operator='" + operator + '\'' +
                ", operatedtime=" + operatedtime +
                ", remark='" + remark + '\'' +
                '}';
    }
}