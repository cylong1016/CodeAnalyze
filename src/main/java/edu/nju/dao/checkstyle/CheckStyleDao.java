package edu.nju.dao.checkstyle;

import edu.nju.entities.checkstyle.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Floyd on 2017/3/28.
 */
public interface CheckStyleDao {

    public List<CheckstyleResult> getAllResult();

    public CheckstyleResult findResultById();

    public List<CheckstyleResult> findResult(Map<String, Object> querys);

    public List findByHql(String hql);

    public boolean check(long checkId, long groupId);

    public List<InternalStat> getStatList(Map<String, Object> querys);

    public List<CheckstyleRegression> getRegressionByInternalType(String internalType);

    public List<CheckType> getAllCheckType();
    public List<CheckType> findTypeByQuery(Map<String, Object> querys);
    public List<CheckType> getActiveType();

    public boolean updateCheckType(CheckType type);
}
