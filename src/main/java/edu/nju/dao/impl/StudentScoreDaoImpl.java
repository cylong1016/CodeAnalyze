package edu.nju.dao.impl;

import edu.nju.dao.BaseDao;
import edu.nju.dao.StudentScoreDao;
import edu.nju.entities.StudentScore;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/17.
 */
@Repository
public class StudentScoreDaoImpl implements StudentScoreDao {

    @Autowired
    private BaseDao baseDao;

    @Override
    public List<StudentScore> getStudentScoreByQuery(Map<String, Object> querys) {
        return baseDao.find(StudentScore.class, querys);
    }

    @Override
    public List<StudentScore> getAllStudentScore() {
        return baseDao.getAllList(StudentScore.class);
    }

    @Override
    public List getStudentScoreByHql(String hql) {
        Query query = baseDao.getNewSession().createQuery(hql);
        return query.getResultList();
    }
}
