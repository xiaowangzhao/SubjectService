package com.subject.model;

/**
 * @author liangqin
 * @date 2019/5/13 13:17
 */
public class Student {

    private String stuid;//学号，12位固定长度
    private String sname;//姓名
    private String ssex;//性别
    private String classname;//班级名
    private String email;
    private String telphone;//电话
    private String remark;//备注
    private String status;//学生状态
    private Subject subject;//学生选择的课题列表

    public Student(){
        subject=new Subject();
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSsex() {
        return ssex;
    }

    public void setSsex(String ssex) {
        this.ssex = ssex;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuid='" + stuid + '\'' +
                ", sname='" + sname + '\'' +
                ", ssex='" + ssex + '\'' +
                ", classname='" + classname + '\'' +
                ", email='" + email + '\'' +
                ", telphone='" + telphone + '\'' +
                ", remark='" + remark + '\'' +
                ", status='" + status + '\'' +
                ", subject=" + subject +
                '}';
    }
}
