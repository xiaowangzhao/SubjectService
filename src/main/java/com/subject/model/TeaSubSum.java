package com.subject.model;

import java.util.ArrayList;

/**
 * @author liangqin
 * @date 2019/4/1 19:39
 */
public class TeaSubSum {
    String tid;//教师编号
    int subsum;//总题目数
    ArrayList<String> subjects;//教师申报的题目

    public TeaSubSum() {
        subsum = 0;
        subjects = new ArrayList<>();
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public int getSubsum() {
        return subsum;
    }

    public void setSubsum(int subsum) {
        this.subsum = subsum;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }
}
