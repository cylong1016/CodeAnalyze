package edu.nju.Vo.checkstyle;


import edu.nju.entities.checkstyle.Result;
import edu.nju.utils.checkstyle.Constant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/3.
 */
public class ResultList {
    private Date checkDate;
    private int grade;
    private List<ResultItem> warn;
    private List<ResultItem> error;

    public ResultList(){}

    public ResultList(Date date, int grade){
        this.checkDate = date;
        this.grade = grade;
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
    public void addResultItem(ResultItem item){
        if (item.getFather_type().equals(Constant.FATHER_TYPE_WARN) ){
            this.warn.add(item);
        } else if( item.getFather_type().equals(Constant.FATHER_TYPE_ERROR) ){
            this.error.add(item);
        }
    }
    public void transToPo(List<Result> results){
        for(Result result: results){
            this.addResultItem(new ResultItem(result.getFatherType(), result.getSubType(),result.getFile(), result.getRow(), result.getCol(),result.getDescription()));
        }
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
