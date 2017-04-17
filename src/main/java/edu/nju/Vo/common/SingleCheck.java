package edu.nju.Vo.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
public class SingleCheck {
    private Date checkDate;
    private List<SingleResult> results;

    public SingleCheck(Date checkDate){
        this.checkDate = checkDate;
        this.results = new ArrayList<>();
    }

    public void addSingleResult(SingleResult singleResult){
        this.results.add(singleResult);
    }
    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public List<SingleResult> getResults() {
        return results;
    }

    public void setResults(List<SingleResult> results) {
        this.results = results;
    }
}
