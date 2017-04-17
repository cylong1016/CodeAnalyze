package edu.nju.entities.checkstyle;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/4/8.
 */
//@Entity
//@Table(name = "checkstyle_grade")
public class Grade {
    private long id;
    private long checkId;
    private long groupId;
    private int grade;
    private String comment;

    public Grade(){}
    public Grade(long checkId, long groupId, int grade, String comment){
        this.checkId = checkId;
        this.groupId = groupId;
        this.grade = grade;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Column(name = "group_id")
    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Column(name = "check_id")
    public long getCheckId() {
        return checkId;
    }

    public void setCheckId(long checkId) {
        this.checkId = checkId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
