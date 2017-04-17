package edu.nju.entities;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/4/17.
 */
@Entity
@Table(name = "s_group")
public class StudentGroup {

    private long id;
    private String groupName;
    private String projectName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "group_name")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Column(name = "pro_name")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
