package edu.nju.Vo.checkstyle;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/5.
 */
public class GroupBriefInfo {
    private long id;
    private Map<String, int[]> briefInfo;

    public GroupBriefInfo(long id) {
        this.id = id;
        briefInfo = new HashMap<>();
    }

    public Map<String, int[]> getBriefInfo() {
        return briefInfo;
    }

    public void setBriefInfo(Map<String, int[]> briefInfo) {
        this.briefInfo = briefInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void addSingleCheckBriefInfo(Date checkDate, int[] singleCheckBriefInfo){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        this.briefInfo.put(sdf.format(checkDate), singleCheckBriefInfo);
    }
}
