package edu.nju.entities;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/4/17.
 */
@Entity
@Table(name = "regression")
public class Regression {
    private long id;
    private float cffcA;
    private float cffcB;
    private String toolName;
    private long checkId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "cffc_a")
    public float getCffcA() {
        return cffcA;
    }

    public void setCffcA(float cffcA) {
        this.cffcA = cffcA;
    }

    @Column(name = "cffc_b")
    public float getCffcB() {
        return cffcB;
    }

    public void setCffcB(float cffcB) {
        this.cffcB = cffcB;
    }

    @Column(name = "check_id")
    public long getCheckId() {
        return checkId;
    }

    public void setCheckId(long checkId) {
        this.checkId = checkId;
    }

    @Column(name = "tool_name")
    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }
}
