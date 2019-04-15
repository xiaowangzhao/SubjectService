package com.subject.model;

import java.util.Date;

public class Stusub {
    private Long stusubid;

    private String stuid;

    private Long subid;

    private String pickorder;

    private String pickflag;

    private String operator;

    private Date operatedtime;

    public Long getStusubid() {
        return stusubid;
    }

    public void setStusubid(Long stusubid) {
        this.stusubid = stusubid;
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public Long getSubid() {
        return subid;
    }

    public void setSubid(Long subid) {
        this.subid = subid;
    }

    public String getPickorder() {
        return pickorder;
    }

    public void setPickorder(String pickorder) {
        this.pickorder = pickorder;
    }

    public String getPickflag() {
        return pickflag;
    }

    public void setPickflag(String pickflag) {
        this.pickflag = pickflag;
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

    @Override
    public String toString() {
        return "Stusub{" +
                "stusubid=" + stusubid +
                ", stuid='" + stuid + '\'' +
                ", subid=" + subid +
                ", pickorder=" + pickorder +
                ", pickflag='" + pickflag + '\'' +
                ", operator='" + operator + '\'' +
                ", operatedtime=" + operatedtime +
                '}';
    }
}