package edu.nju.entities.checkstyle;

import javax.persistence.*;

/**
 * Created by Floyd on 2017/3/28.
 */
//@Entity
//@Table(name = "checkstyle_sub_type")
public class SubType {
    private long id;
    private long fatherId;
    private String name;
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "father_id")
    public long getFatherId() {
        return fatherId;
    }

    public void setFatherId(long fatherId) {
        this.fatherId = fatherId;
    }
}
