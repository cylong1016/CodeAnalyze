package edu.nju.dao.checkstyle.impl;

import edu.nju.dao.BaseDao;
import edu.nju.dao.checkstyle.CheckStyleDao;
import edu.nju.entities.checkstyle.CheckLog;
import edu.nju.entities.checkstyle.Group;
import edu.nju.entities.checkstyle.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by Floyd on 2017/3/28.
 */
@Repository("checkStyleDao")
public class CheckStyleDaoImpl implements CheckStyleDao{

    @Autowired
    private BaseDao baseDao;

    @Override
    public List<Result> getAllResult() {
        return baseDao.getAllList(Result.class);
    }

    @Override
    public List<CheckLog> getAllCheck() {
        return baseDao.getAllList(CheckLog.class);
    }

    @Override
    public List<Group> getAllGroup() {
        return baseDao.getAllList(Group.class);
    }

    @Override
    public List<Result> findResult(Map<String, Object> querys) {
        return baseDao.find(Result.class, querys);
    }

    public List<Result> getResultByTimeLine(Date date) {
        Map<String, Object> checkLogQuery = new HashMap<>();
        checkLogQuery.put("check_date", date);
        List<CheckLog> check = baseDao.find(CheckLog.class, checkLogQuery);
        long checkId = 0;
        if (check.size()>1){
            return null;
        } else {
            checkId = check.get(0).getId();
        }
        Map<String, Object> resultQuery = new HashMap<>();
        resultQuery.put("check_id", checkId);
        return baseDao.find(Result.class, resultQuery);
    }

    public List<Result> getResultByFatherType(String type) {
        Map<String, Object> resultQuery = new HashMap<>();
        resultQuery.put("father_type", type);
        return baseDao.find(Result.class, resultQuery);
    }

    public List<Result> getResultBySubType(String type) {
        Map<String, Object> resultQuery = new HashMap<>();
        resultQuery.put("sub_type", type);
        return baseDao.find(Result.class, resultQuery);
    }

    public List<Result> getResultByGroup(long groupId) {
        Map<String, Object> resultQuery = new HashMap<>();
        resultQuery.put("group_id", groupId);
        return baseDao.find(Result.class, resultQuery);
    }

    @Override
    public boolean check(long checkId, long groupId) {
//        调用脚本 运行 checkstyle
        return false;
    }

    @Override
    public boolean addCheck(Date date, String desccription) {
        CheckLog check = new CheckLog();
        check.setCheckDate(date);
        check.setDescription(desccription);
        try{
            baseDao.save(check);
        }catch (Exception exp){
            return false;
        }
        return true;
    }

}
