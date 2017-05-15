package edu.nju.dao;

import edu.nju.entities.TeacherScore;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/17.
 */
public interface TeacherScoreDao {

    public List<TeacherScore> getTeacherScoreByQuery(Map<String, Object> querys);

    public List<?> getTeacherScoreByHql(String hql);

    public boolean addTeacherScore(TeacherScore score);

    public boolean deleteTeacherScore(TeacherScore score);
}
