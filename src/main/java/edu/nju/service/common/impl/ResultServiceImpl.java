package edu.nju.service.common.impl;

import edu.nju.Vo.common.SingleCheck;
import edu.nju.Vo.common.SingleResult;
import edu.nju.dao.CheckDao;
import edu.nju.dao.checkstyle.CheckStyleDao;
import edu.nju.entities.CheckLog;
import edu.nju.entities.checkstyle.CheckstyleResult;
import edu.nju.entities.checkstyle.SubTypeStat;
import edu.nju.service.common.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2017/4/18.
 */
@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    private CheckDao checkDao;
    @Autowired
    private CheckStyleDao checkStyleDao;
    @Override
    public List<SingleCheck> getGroupAllCheckstyleChecks(long groupId) {
        List<SingleCheck> groupChecks = new ArrayList<>();
        for(CheckLog check : checkDao.getAllCheck()){
            if(ifCheckdayPass(check.getCheckDate())){
                SingleCheck singleCheck = new SingleCheck(check.getCheckDate());
                Map<String, Object> querys = new HashMap<>();
                querys.put("checkId", check.getId());
                querys.put("groupId", groupId);
                for(SubTypeStat subTypeStat : checkStyleDao.getSubTypeStat(querys)){
                    if(subTypeStat.getCount()!=0){
                        SingleResult singleResult = new SingleResult(subTypeStat.getSubType(), subTypeStat.getCount());
                        singleCheck.addSingleResult(singleResult);
                    }
                }
                groupChecks.add(singleCheck);
            }
        }
        return groupChecks;
    }

    @Override
    public List<SingleCheck> getGroupAllPmdChecks(long groupId) {
        return null;
    }

    @Override
    public List<SingleCheck> getGroupAllSonarChecks(long groupId) {
        return null;
    }

    /**
     * 判断检查日期是否已过
     * @param date
     * @return boolean
     */
    private boolean ifCheckdayPass(Date date){
        if ( date.compareTo(new Date()) <= 0 ){
            return true;
        }else {
            return false;
        }
    }
}
