package com.student.bean;

public class System {
    private Integer id;

    private String schoolname;

    private Byte forbidteacher;

    private Byte forbidstudent;

    private String noticeteacher;

    private String noticestudent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname == null ? null : schoolname.trim();
    }

    public Byte getForbidteacher() {
        return forbidteacher;
    }

    public void setForbidteacher(Byte forbidteacher) {
        this.forbidteacher = forbidteacher;
    }

    public Byte getForbidstudent() {
        return forbidstudent;
    }

    public void setForbidstudent(Byte forbidstudent) {
        this.forbidstudent = forbidstudent;
    }

    public String getNoticeteacher() {
        return noticeteacher;
    }

    public void setNoticeteacher(String noticeteacher) {
        this.noticeteacher = noticeteacher == null ? null : noticeteacher.trim();
    }

    public String getNoticestudent() {
        return noticestudent;
    }

    public void setNoticestudent(String noticestudent) {
        this.noticestudent = noticestudent == null ? null : noticestudent.trim();
    }
}