package edu.nju.Vo.common;

import edu.nju.Vo.checkstyle.GroupForCheck;
import edu.nju.entities.checkstyle.CheckLog;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/4/7.
 */
public class Check {
    private long id;
    private String date;
    private String description;
    private int checkCount;
//  参与这次检查的小组 [ {id, name} , ... ]
//    private List<GroupForCheck> groups;

    public Check(){}
    public Check(long id, Date date, String description){
        this.id = id;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.date = sdf.format(date);
        this.description = description;
//        this.groups = new ArrayList<>();
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(int checkCount) {
        this.checkCount = checkCount;
    }

//    public List<GroupForCheck> getGroups() {
//        return groups;
//    }
//
//    public void setGroups(List<GroupForCheck> groups) {
//        this.groups = groups;
//    }
//    public void addGroupInfo(GroupForCheck group){
//        this.groups.add(group);
//    }
}


