package edu.nju.entities.checkstyle;

import javax.persistence.*;

/**
 * Created by Floyd on 2017/3/28.
 */
@Entity
@Table(name = "check_result")
public class CheckResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long group;
    private long submit;

}
