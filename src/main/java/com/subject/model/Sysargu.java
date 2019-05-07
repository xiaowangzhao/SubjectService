package com.subject.model;

import java.util.Date;

public class Sysargu {
    private Long arguid;

    private String arguname;

    private String argutype;

    private String arguvalue;

    private String remark;

    private String operator;

    private Date operatedtime;

    public Long getArguid() {
        return arguid;
    }

    public void setArguid(Long arguid) {
        this.arguid = arguid;
    }

    public String getArguname() {
        return arguname;
    }

    public void setArguname(String arguname) {
        this.arguname = arguname;
    }

    public String getArgutype() {
        return argutype;
    }

    public void setArgutype(String argutype) {
        this.argutype = argutype;
    }

    public String getArguvalue() {
        return arguvalue;
    }

    public void setArguvalue(String arguvalue) {
        this.arguvalue = arguvalue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "Sysargu{" +
                "arguid=" + arguid +
                ", arguname='" + arguname + '\'' +
                ", argutype='" + argutype + '\'' +
                ", arguvalue='" + arguvalue + '\'' +
                ", remark='" + remark + '\'' +
                ", operator='" + operator + '\'' +
                ", operatedtime=" + operatedtime +
                '}';
    }
}