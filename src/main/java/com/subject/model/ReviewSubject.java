package com.subject.model;

public class ReviewSubject {
    private String tid;

    private String asstid;

    private Long subid;

    private String reviewopinion;

    private String tnames;

    private String reviewerid;

    private String subname;

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getReviewerid() {
        return reviewerid;
    }

    public void setReviewerid(String reviewerid) {
        this.reviewerid = reviewerid;
    }

    public String getTnames() {
        return tnames;
    }

    public void setTnames(String tnames) {
        this.tnames = tnames;
    }

    public String getReviewername() {
        return reviewername;
    }

    public void setReviewername(String reviewername) {
        this.reviewername = reviewername;
    }

    private String reviewername;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Long getSubid() {
        return subid;
    }

    public void setSubid(Long subid) {
        this.subid = subid;
    }

    public String getReviewopinion() {
        return reviewopinion;
    }

    public void setReviewopinion(String reviewopinion) {
        this.reviewopinion = reviewopinion;
    }

    public String getAsstid() {
        return asstid;
    }

    public void setAsstid(String asstid) {
        this.asstid = asstid;
    }

    @Override
    public String toString() {
        return "ReviewSubject{" +
                "tid='" + tid + '\'' +
                ", asstid='" + asstid + '\'' +
                ", subid=" + subid +
                ", reviewopinion='" + reviewopinion + '\'' +
                ", tnames='" + tnames + '\'' +
                ", reviewername='" + reviewername + '\'' +
                '}';
    }
}