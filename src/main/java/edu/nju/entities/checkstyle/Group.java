package edu.nju.entities.checkstyle;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/4/4.
 */
@Entity
@Table(name = "checkstyle_group")
public class Group
{
    private long id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
