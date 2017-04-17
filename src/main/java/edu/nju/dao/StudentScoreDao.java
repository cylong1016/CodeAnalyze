package edu.nju.dao;

import edu.nju.entities.StudentScore;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/17.
 */
public interface StudentScoreDao {

    public List<StudentScore> getStudentScoreByQuery(Map<String, Object> querys);

    public List<StudentScore> getAllStudentScore();

    public List getStudentScoreByHql(String hql);
}
