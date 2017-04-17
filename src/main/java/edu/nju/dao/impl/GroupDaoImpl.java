package edu.nju.dao.impl;

import edu.nju.dao.BaseDao;
import edu.nju.dao.GroupDao;
import edu.nju.entities.StudentGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/17.
 */
@Repository
public class GroupDaoImpl implements GroupDao {

    @Autowired
    private BaseDao baseDao;
    @Override
    public List<StudentGroup> getAllGroup() {
        return baseDao.getAllList(StudentGroup.class);
    }

    @Override
    public StudentGroup getGroupById(long id) {
        Map<String, Object> querys = new HashMap<>();
        querys.put("id", id);
        return baseDao.find(StudentGroup.class, querys).get(0);
    }

    @Override
    public boolean addGroup(StudentGroup group) {
        try{
            baseDao.save(group);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<StudentGroup> getGroupByQuery(Map<String, Object> querys) {
        return baseDao.find(StudentGroup.class, querys);
    }
}
