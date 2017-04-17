package edu.nju.dao.checkstyle.impl;

import edu.nju.dao.BaseDao;
import edu.nju.dao.checkstyle.CheckStyleDao;
import edu.nju.entities.checkstyle.*;
import edu.nju.utils.checkstyle.Constant;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.rmi.MarshalledObject;
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
        List<Result> results = baseDao.getAllList(Result.class);
        return baseDao.getAllList(Result.class);
    }

    @Override
    public Result findResultById() {
        return null;
    }

    public List<CheckLog> getAllCheck() {
        return baseDao.getAllList(CheckLog.class);
    }

    public List<Group> getAllGroup() {
        return baseDao.getAllList(Group.class);
    }

    @Override
    public List<Result> findResult(Map<String, Object> querys) {
        return baseDao.find(Result.class, querys);
    }

    @Override
    public List findByHql(String hql) {
        Query query = baseDao.getNewSession().createQuery(hql);
        return query.getResultList();
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

    public boolean addCheck(Date date, String description) {
        CheckLog check = new CheckLog();
        check.setCheckDate(date);
        check.setDescription(description);
        try{
            baseDao.save(check);
        }catch (Exception exp){
            System.out.println(exp.getMessage());
            return false;
        }
        return true;
    }

    public CheckLog findCheckById(long id) {
        Map<String, Object> querys = new HashMap<>();
        querys.put("id", id);
        List<CheckLog> checkLogs = baseDao.find(CheckLog.class, querys);
        if(checkLogs.size()==1){
            return checkLogs.get(0);
        }else {
            return null;
        }
    }

    public Group findGroupById(long id) {
        Map<String, Object> querys = new HashMap<>();
        querys.put("id", id);
        List<Group> groups = baseDao.find(Group.class, querys);
        if(groups.size()==1){
            return groups.get(0);
        }else {
            return null;
        }
    }

    public boolean addGrade(Grade grade) {
        try{
            baseDao.save(grade);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public List<Grade> getGradeByQuery(Map<String, Object> querys) {
        return baseDao.find(Grade.class,querys);
    }

    @Override
    public List<StatResult> getStatList(Map<String, Object> querys) {
        return baseDao.find(StatResult.class, querys);
    }

    @Override
    public List<CheckstyleRegression> getRegressionByInternalType(String internalType) {
        Map<String, Object> querys = new HashMap<>();
        querys.put("internalType", internalType);
        return baseDao.find(CheckstyleRegression.class, querys);
    }

    @Override
    public List<CheckType> getAllCheckType() {
        return baseDao.getAllList(CheckType.class);
    }

    @Override
    public List<CheckType> findTypeByQuery(Map<String, Object> querys) {
        return baseDao.find(CheckType.class, querys);
    }

    @Override
    public List<CheckType> getActiveType() {
        Map<String, Object> querys = new HashMap<>();
        querys.put("status", Constant.ACTIVE);
        List<CheckType> activeTypes = baseDao.find(CheckType.class, querys);
        return activeTypes;
    }

    public List<CheckType> getNotActiveType() {
        Map<String, Object> querys = new HashMap<>();
        querys.put("status", Constant.NOT_ACTIVE);
        List<CheckType> notActiveTypes = baseDao.find(CheckType.class, querys);
        return notActiveTypes;
    }

    @Override
    public boolean updateCheckType(CheckType type) {
        try{
            baseDao.update(type);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 检查针对某个type的检查是否开启
     * @param type
     * @return boolean
     */
    private boolean ifCheckActive(String type, List<CheckType> activeTypesEntity){
//        List<CheckType> activeTypesEntity = getActiveType();
        List<String> activeTypes = new ArrayList<>();
        for (CheckType typeEntity : activeTypesEntity){
            activeTypes.add(typeEntity.getSubType());
        }
        if (activeTypes.indexOf(type)==-1){
            return false;
        } else{
            return true;
        }
    }
}

