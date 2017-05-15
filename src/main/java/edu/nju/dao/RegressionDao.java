package edu.nju.dao;

import edu.nju.entities.Regression;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/17.
 */
public interface RegressionDao {

    public List<Regression> getRegressionByQuery(Map<String, Object> querys);

    public List<Regression> getRegressionByHql(String hql);
}
