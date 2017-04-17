package edu.nju.dao;

import edu.nju.entities.CheckLog;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/17.
 */
public interface CheckDao {

    public List<CheckLog> getAllCheck();

    public List<CheckLog> getCheckByQuery(Map<String, Object> querys);

    public List getCheckByHql(String hql);

    public boolean addCheck(CheckLog check);

}
