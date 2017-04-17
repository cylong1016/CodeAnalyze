package edu.nju.entities;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/4/17.
 */
@Entity
@Table(name = "s_score")
public class StudentScore {
    private long id;
    private long groupId;
    private long checkId;
    private int checkstyleScore;
    private int pmdScore;
    private int sqScore;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Column(name = "checkstyle_score")
    public int getCheckstyleScore() {
        return checkstyleScore;
    }

    public void setCheckstyleScore(int checkstyleScore) {
        this.checkstyleScore = checkstyleScore;
    }

    @Column(name = "pmd_score")
    public int getPmdScore() {
        return pmdScore;
    }

    public void setPmdScore(int pmdScore) {
        this.pmdScore = pmdScore;
    }

    @Column(name = "sq_score")
    public int getSqScore() {
        return sqScore;
    }

    public void setSqScore(int sqScore) {
        this.sqScore = sqScore;
    }
}
