package edu.nju.Vo.checkstyle;


/**
 * Created by Administrator on 2017/4/3.
 */
public class ResultItem {
    private String father_type;
    private String sub_type;
    private String file_name;
    private int row;
    private int col;
    private String description;

    public ResultItem(){
    }
    public ResultItem(String father_type, String sub_type, String file_name, int row, int col, String description){
        this.father_type = father_type;
        this.sub_type = sub_type;
        this.file_name = file_name;
        this.row = row;
        this.col = col;
        this.description = description;
    }

    public String getFather_type() {
        return father_type;
    }

    public void setFather_type(String father_type) {
        this.father_type = father_type;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
