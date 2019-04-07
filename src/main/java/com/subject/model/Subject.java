package com.subject.model;

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
                '}';
    }
}