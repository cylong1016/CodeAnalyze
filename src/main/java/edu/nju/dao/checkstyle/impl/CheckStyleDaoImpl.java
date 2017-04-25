package edu.nju.dao.checkstyle.impl;

import edu.nju.dao.BaseDao;
import edu.nju.dao.checkstyle.CheckStyleDao;
import edu.nju.entities.checkstyle.*;
import edu.nju.utils.checkstyle.Constant;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by Floyd on 2017/3/28.
 */
@Repository
public class CheckStyleDaoImpl implements CheckStyleDao{

    @Autowired
    private BaseDao baseDao;

    @Override
    public List<CheckstyleResult> getAllResult() {
        List<CheckstyleResult> checkstyleResults = baseDao.getAllList(CheckstyleResult.class);
        return baseDao.getAllList(CheckstyleResult.class);
    }

    @Override
    public CheckstyleResult findResultById() {
        return null;
    }

    public List<CheckLog> getAllCheck() {
        return baseDao.getAllList(CheckLog.class);
    }

    public List<Group> getAllGroup() {
        return baseDao.getAllList(Group.class);
    }

    @Override
    public List<CheckstyleResult> findResult(Map<String, Object> querys) {
        return baseDao.find(CheckstyleResult.class, querys);
    }

    @Override
    public List findByHql(String hql) {
        Query query = baseDao.getNewSession().createQuery(hql);
        return query.getResultList();
    }

    @Override
    public boolean check(long checkId, long groupId) {
//        调用脚本 运行 checkstyle
        return false;
    }

    @Override
    public List<SubTypeStat> getSubTypeStat(Map<String, Object> querys) {
        return baseDao.find(SubTypeStat.class, querys);
    }

    @Override
    public List<InternalStat> getStatList(Map<String, Object> querys) {
        return baseDao.find(InternalStat.class, querys);
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

