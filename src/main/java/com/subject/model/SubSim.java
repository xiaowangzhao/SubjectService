package com.subject.model;

/**
 * @author liangqin
 * @date 2019/4/10 9:04
 */
public class SubSim {

    private String subname;//课题名
    private String tid;//指导教师名
    private Float similard;//相似度
    private String usedyear;//被使用的年份

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Float getSimilard() {
        return similard;
    }

    public void setSimilard(Float similard) {
        this.similard = similard;
    }

    public String getUsedyear() {
        return usedyear;
    }

    public void setUsedyear(String usedyear) {
        this.usedyear = usedyear;
    }

    @Override
    public String toString() {
        return "SubSim{" +
                "subname='" + subname + '\'' +
                ", tid='" + tid + '\'' +
                ", similard=" + similard +
                ", usedyear='" + usedyear + '\'' +
                '}';
    }
}
