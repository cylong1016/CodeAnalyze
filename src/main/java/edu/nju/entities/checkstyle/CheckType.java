package edu.nju.entities.checkstyle;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/4/12.
 */
@Entity
@Table(name = "checkstyle_type")
public class CheckType {
    private long id;
    private String fatherType;
    private String internalType;
    private String subType;
    private int status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "father_type")
    public String getFatherType() {
        return fatherType;
    }

    public void setFatherType(String fatherType) {
        this.fatherType = fatherType;
    }

    @Column(name = "internal_type")
    public String getInternalType() {
        return internalType;
    }

    public void setInternalType(String internalType) {
        this.internalType = internalType;
    }

    @Column(name = "sub_type")
    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    /**
     * Status 表示针对这个类型的错误的检查，是否启用
     * 0 未启用
     * 1 启用
     * @return
     */
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
