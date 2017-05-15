package edu.nju.dao.impl;

import edu.nju.dao.BaseDao;
import edu.nju.dao.RegressionDao;
import edu.nju.entities.Regression;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/17.
 */
@Repository
public class RegressionDaoImpl implements RegressionDao {

    @Autowired
    private BaseDao baseDao;
    @Override
    public List<Regression> getRegressionByQuery(Map<String, Object> querys) {
        return baseDao.find(Regression.class, querys);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Regression> getRegressionByHql(String hql) {
        Query<?> query = baseDao.getNewSession().createQuery(hql);
        return (List<Regression>)query.getResultList();
    }
}
