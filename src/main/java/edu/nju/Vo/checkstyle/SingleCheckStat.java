package edu.nju.Vo.checkstyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/16.
 */
public class SingleCheckStat {
    private List<SampleCoordinate> coords;
    private float cffc_a;
    private float cffc_b;
    private String internalType;

    public SingleCheckStat(){}
    public SingleCheckStat(float cffc_a, float cffc_b, String internalType){
        this.cffc_a = cffc_a;
        this.cffc_b = cffc_b;
        this.internalType = internalType;
        this.coords = new ArrayList<>();
    }
    public void addCoordinate(SampleCoordinate coordinate){
        this.coords.add(coordinate);
    }
    public List<SampleCoordinate> getCoords() {
        return coords;
    }

    public void setCoords(List<SampleCoordinate> coords) {
        this.coords = coords;
    }

    public float getCffc_a() {
        return cffc_a;
    }

    public void setCffc_a(float cffc_a) {
        this.cffc_a = cffc_a;
    }

    public float getCffc_b() {
        return cffc_b;
    }

    public void setCffc_b(float cffc_b) {
        this.cffc_b = cffc_b;
    }
}
