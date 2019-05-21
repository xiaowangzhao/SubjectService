package com.subject.model;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private Long subid;

    private String subname;

    private String usedyear;

    private String subkind;

    private String subsource;

    private String subtype;

    private int isoutschool;

    private String tid;

    private String asstid;

    private String status;

    private String subdirection;

    private String subsort;

    private String summary;

    private String content;

    private String requirement;

    private String refpapers;

    private String subprog;

    private String specid;

    private String condition;//在整个生命周期中，课题包括未提交、审核中、审核通过、选题中、n学生初选、已选共6种状态

    private String reviewopinion;//审核意见（课题盲审）

    private List<SubSim> simSubsInHis;//历史中相似的课题

    private String operation;//在不同的状态下可进行的操作集合 状态1未提交：修改、删除、提交、转移到暂存库；状态2已选：2.1（不允许修改任务书）：查看设计情况； 2.2（允许修改任务书）：修改任务书、复制到暂存库、查看设计情况；状态3已有学生初选：选择学生；状态4是否显示“查看审核意见”操作：查看审核意见；状态5审核未通：查看审核意见、修改、删除、提交、转移到暂存库；

    private String tnames;

    private String tdept;

    private String tpost;

    private String specname;

    private String auditoption;

    public String getAuditoption() {
        return auditoption;
    }

    public void setAuditoption(String auditoption) {
        this.auditoption = auditoption;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSpecname() {
        return specname;
    }

    public void setSpecname(String specname) {
        this.specname = specname;
    }

    public String getTnames() {
        return tnames;
    }

    public void setTnames(String tnames) {
        this.tnames = tnames;
    }

    public String getTdept() {
        return tdept;
    }

    public void setTdept(String tdept) {
        this.tdept = tdept;
    }

    public String getTpost() {
        return tpost;
    }

    public void setTpost(String tpost) {
        this.tpost = tpost;
    }

    public String getSpecid() {
        return specid;
    }

    public void setSpecid(String specid) {
        this.specid = specid;
    }

    public List<SubSim> getSimSubsInHis() {
        return simSubsInHis;
    }

    public void setSimSubsInHis(List<SubSim> simSubsInHis) {
        this.simSubsInHis = simSubsInHis;
    }

    public String getReviewopinion() {
        return reviewopinion;
    }

    public void setReviewopinion(String reviewopinion) {
        this.reviewopinion = reviewopinion;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Long getSubid() {
        return subid;
    }

    public void setSubid(Long subid) {
        this.subid = subid;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getUsedyear() {
        return usedyear;
    }

    public void setUsedyear(String usedyear) {
        this.usedyear = usedyear;
    }

    public String getSubkind() {
        return subkind;
    }

    public void setSubkind(String subkind) {
        this.subkind = subkind;
    }

    public String getSubsource() {
        return subsource;
    }

    public void setSubsource(String subsource) {
        this.subsource = subsource;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public int getIsoutschool() {
        return isoutschool;
    }

    public void setIsoutschool(int isoutschool) {
        this.isoutschool = isoutschool;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getAsstid() {
        return asstid;
    }

    public void setAsstid(String asstid) {
        this.asstid = asstid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubdirection() {
        return subdirection;
    }

    public void setSubdirection(String subdirection) {
        this.subdirection = subdirection;
    }

    public String getSubsort() {
        return subsort;
    }

    public void setSubsort(String subsort) {
        this.subsort = subsort;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getRefpapers() {
        return refpapers;
    }

    public void setRefpapers(String refpapers) {
        this.refpapers = refpapers;
    }

    public String getSubprog() {
        return subprog;
    }

    public void setSubprog(String subprog) {
        this.subprog = subprog;
    }

    public Subject() {
        simSubsInHis=new ArrayList<SubSim>();
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subid=" + subid +
                ", subname='" + subname + '\'' +
                ", usedyear='" + usedyear + '\'' +
                ", subkind='" + subkind + '\'' +
                ", subsource='" + subsource + '\'' +
                ", subtype='" + subtype + '\'' +
                ", isoutschool=" + isoutschool +
                ", tid='" + tid + '\'' +
                ", asstid='" + asstid + '\'' +
                ", status='" + status + '\'' +
                ", subdirection='" + subdirection + '\'' +
                ", subsort='" + subsort + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", requirement='" + requirement + '\'' +
                ", refpapers='" + refpapers + '\'' +
                ", subprog='" + subprog + '\'' +
                ", specid='" + specid + '\'' +
                ", condition='" + condition + '\'' +
                ", reviewopinion='" + reviewopinion + '\'' +
                ", simSubsInHis=" + simSubsInHis +
                ", operation='" + operation + '\'' +
                ", tnames='" + tnames + '\'' +
                ", tdept='" + tdept + '\'' +
                ", tpost='" + tpost + '\'' +
                ", specname='" + specname + '\'' +
                ", auditoption='" + auditoption + '\'' +
                '}';
    }
}