package edu.nju.Vo.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
public class GroupAllScore {
    private long groupId;
    private List<String> checkDate;
    private List<Integer> teacherScore;
    private List<Integer> checkstyleScore;
    private List<Integer> pmdScore;
    private List<Integer> sqScore;
    public GroupAllScore(long groupId){
        this.groupId = groupId;
        this.checkDate = new ArrayList<>();
        this.teacherScore = new ArrayList<>();
        this.checkstyleScore = new ArrayList<>();
        this.pmdScore = new ArrayList<>();
        this.sqScore = new ArrayList<>();
    }
    public void addCheckDate(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        this.checkDate.add(sdf.format(date));
    }
    public void addTeacherScore(int score){
        this.teacherScore.add(score);
    }
    public void addCheckstyleScore(int score){
        this.checkstyleScore.add(score);
    }
    public void addPmdScore(int score){
        this.pmdScore.add(score);
    }
    public void addSqScore(int score){
        this.sqScore.add(score);
    }
    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public List<Integer> getTeacherScore() {
        return teacherScore;
    }

    public void setTeacherScore(List<Integer> teacherScore) {
        this.teacherScore = teacherScore;
    }

    public List<Integer> getCheckstyleScore() {
        return checkstyleScore;
    }

    public void setCheckstyleScore(List<Integer> checkstyleScore) {
        this.checkstyleScore = checkstyleScore;
    }

    public List<Integer> getPmdScore() {
        return pmdScore;
    }

    public void setPmdScore(List<Integer> pmdScore) {
        this.pmdScore = pmdScore;
    }

    public List<Integer> getSqScore() {
        return sqScore;
    }

    public void setSqScore(List<Integer> sqScore) {
        this.sqScore = sqScore;
    }

    public List<String> getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(List<String> checkDate) {
        this.checkDate = checkDate;
    }
}
