package edu.nju.entities.checkstyle;

import javax.persistence.*;

/**
 * Created by Floyd on 2017/3/28.
 */
@Entity
@Table(name = "checkstyle_result")
public class CheckstyleResult {

    private long id;

    private long groupId;

    private long checkId;

    private String fatherType;

    private String subType;
    private String file;
    private int row;
    private int col;
    private String description;

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

    @Column(name = "father_type")
    public String getFatherType() {
        return fatherType;
    }

    public void setFatherType(String fatherType) {
        this.fatherType = fatherType;
    }

    @Column(name = "sub_type")
    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "group_id")
    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
