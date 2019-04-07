package com.subject.model;

import java.util.Date;

public class Progress {
    private Long proid;

    private String checkopinion;

    private Long subid;

    private int inorder;

    private String content;

    private String startendtime;

    private String operator;

    private Date operatedtime;

    private String remarks;

    public Long getProid() {
        return proid;
    }

    public void setProid(Long proid) {
        this.proid = proid;
    }

    public Long getSubid() {
        return subid;
    }

    public void setSubid(Long subid) {
        this.subid = subid;
    }

    public int getInorder() {
        return inorder;
    }

    public void setInorder(int inorder) {
        this.inorder = inorder;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartendtime() {
        return startendtime;
    }

    public void setStartendtime(String startendtime) {
        this.startendtime = startendtime;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCheckopinion() {
        return checkopinion;
    }

    public void setCheckopinion(String checkopinion) {
        this.checkopinion = checkopinion;
    }

    @Override
    public String toString() {
        return "Progress{" +
                "proid=" + proid +
                ", checkopinion='" + checkopinion + '\'' +
                ", subid=" + subid +
                ", inorder=" + inorder +
                ", content='" + content + '\'' +
                ", startendtime='" + startendtime + '\'' +
                ", operator='" + operator + '\'' +
                ", operatedtime=" + operatedtime +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}