package edu.nju.Vo.checkstyle;

/**
 * Created by Administrator on 2017/4/7.
 */
public class GroupForCheck {
    private long id;
    private String name;
    private int warnCount;
    private int errorCount;

    public  GroupForCheck(){}
    public  GroupForCheck(long id, String name, int warnCount, int errorCount){
        this.id = id;
        this.name = name;
        this.warnCount = warnCount;
        this.errorCount = errorCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWarnCount() {
        return warnCount;
    }

    public void setWarnCount(int warnCount) {
        this.warnCount = warnCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }
}
