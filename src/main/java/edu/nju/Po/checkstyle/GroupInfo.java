package edu.nju.Po.checkstyle;

import com.sun.org.apache.regexp.internal.RE;

import java.util.*;

/**
 * Created by Administrator on 2017/4/3.
 */
public class GroupInfo {
    private long id;
    private Map<Long, ResultList> results;

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

    public Map<Long, ResultList> getResults() {
        return results;
    }

    public void setResults(Map<Long, ResultList> results) {
        this.results = results;
    }

    public void addSingleCheckInfo(long checkId, ResultList list){
        this.results.put(checkId, list);
    }
}
