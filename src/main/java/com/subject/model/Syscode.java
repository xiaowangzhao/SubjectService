package com.subject.model;

import java.util.Date;

public class Syscode {
    private Long codeid;

    private String codeno;

    private String codename;

    private String codevalue;

    private String codecontent;

    private String operator;

    private Date operatedtime;

    public Long getCodeid() {
        return codeid;
    }

    public void setCodeid(Long codeid) {
        this.codeid = codeid;
    }

    public String getCodeno() {
        return codeno;
    }

    public void setCodeno(String codeno) {
        this.codeno = codeno;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public String getCodevalue() {
        return codevalue;
    }

    public void setCodevalue(String codevalue) {
        this.codevalue = codevalue;
    }

    public String getCodecontent() {
        return codecontent;
    }

    public void setCodecontent(String codecontent) {
        this.codecontent = codecontent;
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
        return "Syscode{" +
                "codeid=" + codeid +
                ", codeno='" + codeno + '\'' +
                ", codename='" + codename + '\'' +
                ", codevalue='" + codevalue + '\'' +
                ", codecontent='" + codecontent + '\'' +
                ", operator='" + operator + '\'' +
                ", operatedtime=" + operatedtime +
                '}';
    }
}