package edu.nju.Vo.checkstyle;

/**
 * Created by Administrator on 2017/4/14.
 */
public class SubType {
    private long id;
    private String text;
    private boolean status;
    public SubType(long id, String name, boolean status){
        this.id = id;
        this.text = name;
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
