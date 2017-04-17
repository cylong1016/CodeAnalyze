package edu.nju.Vo.checkstyle;


import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/4/3.
 */
public class GroupInfo {
    private long id;
    private Map<String, ResultList> results;

    public GroupInfo(){}
    public GroupInfo(long groupId){
        this.id = groupId;
        this.results = new HashMap<>();
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<String, ResultList> getResults() {
        return results;
    }

    public void setResults(Map<String, ResultList> results) {
        this.results = results;
    }

    public void addSingleCheckInfo(ResultList list){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        this.results.put(sdf.format(list.getCheckDate()), list);
    }
}
