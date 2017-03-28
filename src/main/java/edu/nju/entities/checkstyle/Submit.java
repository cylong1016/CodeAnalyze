package edu.nju.entities.checkstyle;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Floyd on 2017/3/28.
 */
@Entity
public class Submit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private Date date;

    protected Submit() {
    }

    public Submit(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
