package edu.nju.entities.checkstyle;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/4/18.
 */
@Entity
@Table(name = "checkstyle_subtype_stat")
public class SubTypeStat {
    private long id;
    private long checkId;
    private long groupId;
    private String subType;
    private int count;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "check_id")
    public long getCheckId() {
        return checkId;
    }

    public void setCheckId(long checkId) {
        this.checkId = checkId;
    }

    @Column(name = "group_id")
    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Column(name = "sub_type")
    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
