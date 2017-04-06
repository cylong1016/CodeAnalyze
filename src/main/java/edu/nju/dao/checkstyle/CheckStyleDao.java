package edu.nju.dao.checkstyle;

import edu.nju.Po.checkstyle.GroupBriefInfo;
import edu.nju.Po.checkstyle.GroupInfo;
import edu.nju.entities.checkstyle.CheckLog;
import edu.nju.entities.checkstyle.Group;
import edu.nju.entities.checkstyle.Result;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Floyd on 2017/3/28.
 */
public interface CheckStyleDao {

    public List<Result> getAllResult();

    public Result findResultById();

    public List<CheckLog> getAllCheck();

    public List<Group> getAllGroup();

    public List<Result> findResult(Map<String, Object> querys);

    public List findByHql(String hql);

    public boolean check(long checkId, long groupId);

    public boolean addCheck(Date date, String desccription);

    public CheckLog findCheckById(long id);

    public Group findGroupById(long id);
}
