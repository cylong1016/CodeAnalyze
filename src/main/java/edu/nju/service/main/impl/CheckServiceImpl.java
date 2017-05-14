package edu.nju.service.main.impl;

import edu.nju.Vo.common.Check;
import edu.nju.dao.CheckDao;
import edu.nju.dao.checkstyle.CheckStyleDao;
import edu.nju.entities.CheckLog;
import edu.nju.service.main.CheckService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
@Service
public class CheckServiceImpl implements CheckService{
    @Autowired
    private CheckDao checkDao;
    @Autowired
    private CheckStyleDao checkStyleDao;
    @Override
    public List<Check> getAllChecks() {
        List<Check> checks = new ArrayList<>();
        for(CheckLog item : checkDao.getAllCheck()){
            Check checkVo = new Check(item.getId(), item.getCheckDate(), item.getDescription());
            String hql = "SELECT distinct S.groupId FROM InternalStat S WHERE S.checkId=" + String.valueOf(item.getId());
            checkVo.setCheckCount(checkStyleDao.findByHql(hql).size());
            checks.add(checkVo);
        }
        return checks;
    }

    @Override
    public boolean addCheck(Date date, String description) {
        CheckLog newCheck = new CheckLog();
        newCheck.setDescription(description);
        newCheck.setCheckDate(date);
        return checkDao.addCheck(newCheck);
    }
}
