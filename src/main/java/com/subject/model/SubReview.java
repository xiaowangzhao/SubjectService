package com.subject.model;

/**
 * @author liangqin
 * @date 2019/4/1 19:35
 */
public class SubReview {

    String tid;//审核教师号
    String subid;//课题编号
    String reviewopinion;//审核意见（盲审）

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getReviewopinion() {
        return reviewopinion;
    }

    public void setReviewopinion(String reviewopinion) {
        this.reviewopinion = reviewopinion;
    }
}
