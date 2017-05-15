package edu.nju.dao.impl;

import edu.nju.dao.BaseDao;
import edu.nju.dao.TeacherScoreDao;
import edu.nju.entities.TeacherScore;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/17.
 */
@Repository
public class TeacherScoreDaoImpl implements TeacherScoreDao{

    @Autowired
    private BaseDao baseDao;
    @Override
    public List<TeacherScore> getTeacherScoreByQuery(Map<String, Object> querys) {
        return baseDao.find(TeacherScore.class, querys);
    }

    @Override
    public List<?> getTeacherScoreByHql(String hql) {
        Query<?> query = baseDao.getNewSession().createQuery(hql);
        return query.getResultList();
    }

    @Override
    public boolean addTeacherScore(TeacherScore score) {
        try{
            baseDao.save(score);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteTeacherScore(TeacherScore score) {
        try{
            baseDao.delete(score);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
