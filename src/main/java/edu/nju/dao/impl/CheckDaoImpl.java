package edu.nju.dao.impl;

import edu.nju.dao.BaseDao;
import edu.nju.dao.CheckDao;
import edu.nju.entities.CheckLog;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/17.
 */
@Repository
public class CheckDaoImpl implements CheckDao {

    @Autowired
    private BaseDao baseDao;

    @Override
    public List<CheckLog> getAllCheck() {
        return baseDao.getAllList(CheckLog.class);
    }

    @Override
    public List<CheckLog> getCheckByQuery(Map<String, Object> querys) {
        return baseDao.find(CheckLog.class, querys);
    }

    @Override
    public List getCheckByHql(String hql) {
        Query query = baseDao.getNewSession().createQuery(hql);
        return query.getResultList();
    }

    @Override
    public boolean addCheck(CheckLog check) {
        try{
            baseDao.save(check);
        }catch (Exception exp){
            System.out.println(exp.getMessage());
            return false;
        }
        return true;
    }
}
