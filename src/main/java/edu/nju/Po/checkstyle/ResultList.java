package edu.nju.Po.checkstyle;


import edu.nju.entities.checkstyle.Result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/3.
 */
public class ResultList {
    private Date checkDate;
    private List<ResultItem> warn;
    private List<ResultItem> error;

    public ResultList(){}

    public ResultList(Date date){
        this.checkDate = date;
        this.warn = new ArrayList<>();
        this.error = new ArrayList<>();
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public List<ResultItem> getWarn() {
        return warn;
    }

    public void setWarn(List<ResultItem> warn) {
        this.warn = warn;
    }

    public List<ResultItem> getError() {
        return error;
    }

    public void setError(List<ResultItem> error) {
        this.error = error;
    }
    public void addWarn(ResultItem item){
        this.warn.add(item);
    }
    public void addError(ResultItem item){
        this.error.add(item);
    }
    public void transWarnToPo(List<Result> results){
        for(Result result: results){
            this.addWarn(new ResultItem(result.getFatherType(), result.getSubType(),result.getFile(), result.getRow(), result.getCol(),result.getDescription()));
        }
    }
    public void transErrorToPo(List<Result> results){
        for(Result result: results){
            this.addError(new ResultItem(result.getFatherType(), result.getSubType(),result.getFile(), result.getRow(), result.getCol(),result.getDescription()));
        }
    }
}
