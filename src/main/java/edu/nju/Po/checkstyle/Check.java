package edu.nju.Po.checkstyle;

import edu.nju.entities.checkstyle.CheckLog;

import java.util.*;

/**
 * Created by Administrator on 2017/4/7.
 */
public class Check {
    private long id;
    private Date date;
    private String description;
//  参与这次检查的小组 [ {id, name} , ... ]
    private List<GroupForCheck> groups;

    public Check(){}
    public Check(CheckLog vo){
        this.id = vo.getId();
        this.date = vo.getCheckDate();
        this.description = vo.getDescription();
        this.groups = new ArrayList<>();
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GroupForCheck> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupForCheck> groups) {
        this.groups = groups;
    }
    public void addGroupInfo(GroupForCheck group){
        this.groups.add(group);
    }
}


