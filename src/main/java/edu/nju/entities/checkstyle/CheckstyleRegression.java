package edu.nju.entities.checkstyle;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/4/16.
 */
//@Entity
//@Table(name = "checkstyle_regression")
public class CheckstyleRegression {
    private long id;
    private float cffcA;
    private float cffcB;
    private String internalType;
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

    @Column(name = "internal_type")
    public String getInternalType() {
        return internalType;
    }

    public void setInternalType(String internalType) {
        this.internalType = internalType;
    }

    @Column(name = "check_id")
    public long getCheckId() {
        return checkId;
    }

    public void setCheckId(long checkId) {
        this.checkId = checkId;
    }
}
