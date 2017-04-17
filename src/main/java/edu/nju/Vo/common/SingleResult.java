package edu.nju.Vo.common;

/**
 * Created by Administrator on 2017/4/18.
 */
public class SingleResult {
    /**
     * 每种错误有多少个
     * 如 name=Indentation, count=32
     * 表示在这次检查中，indentation错误有32个
     */
    private String name;
    private int count;

    public SingleResult(){}
    public SingleResult(String name, int count){
        this.name = name;
        this.count = count;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
