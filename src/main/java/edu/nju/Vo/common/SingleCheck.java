package edu.nju.Vo.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
public class SingleCheck {
    private String checkDate;
    private List<SingleResult> results;

    public SingleCheck(Date checkDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.checkDate = sdf.format(checkDate);
        this.results = new ArrayList<>();
    }

    public void addSingleResult(SingleResult singleResult){
        this.results.add(singleResult);
    }
    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public void setCheckDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.checkDate = sdf.format(date);
    }

    public List<SingleResult> getResults() {
        return results;
    }

    public void setResults(List<SingleResult> results) {
        this.results = results;
    }

	@Override
	public String toString() {
		return "SingleCheck [checkDate=" + checkDate + ", results=" + results + "]";
	}
}
