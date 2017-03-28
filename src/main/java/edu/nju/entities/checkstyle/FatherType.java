package edu.nju.entities.checkstyle;

import javax.persistence.*;

/**
 * Created by Floyd on 2017/3/28.
 */
@Entity
@Table(name = "father_type")
public class FatherType {
    private long id;
    private String name;
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
