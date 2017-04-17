package edu.nju.Vo.checkstyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */
public class InternalType {
    private String text;
    // 为了方便前端渲染添加的这个属性，实际上无用处
    private boolean status;
    private List<SubType> children;

    public InternalType(String name){
        this.text = name;
        this.status = false;
        this.children = new ArrayList<>();
    }
    public void addSubType(SubType type){
        this.children.add(type);
    }
    public List<SubType> getChildren() {
        return children;
    }

    public void setChildren(List<SubType> subTypes) {
        this.children = subTypes;
    }

    public boolean isStatus() {
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
}
