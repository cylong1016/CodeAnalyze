package edu.nju.entities.checkstyle;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Floyd on 2017/3/28.
 */
@Entity
@Table(name = "checkstyle_check_log")
public class CheckLog {
    private long id;
    private Date checkDate;
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

    @Column(name = "check_date")
    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }
}
