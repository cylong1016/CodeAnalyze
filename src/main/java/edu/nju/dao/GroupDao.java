package edu.nju.dao;

import edu.nju.entities.StudentGroup;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/17.
 */
public interface GroupDao {

    public List<StudentGroup> getAllGroup();

    public StudentGroup getGroupById(long id);

    public boolean addGroup(StudentGroup group);

    public List<StudentGroup> getGroupByQuery(Map<String, Object> querys);
}
