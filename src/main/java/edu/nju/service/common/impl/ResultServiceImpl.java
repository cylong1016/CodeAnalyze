package edu.nju.service.common.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.nju.Vo.common.SingleCheck;
import edu.nju.Vo.common.SingleResult;
import edu.nju.dao.CheckDao;
import edu.nju.dao.checkstyle.CheckStyleDao;
import edu.nju.dao.pmd.PMDDao;
import edu.nju.entities.CheckLog;
import edu.nju.entities.checkstyle.CheckstyleResult;
import edu.nju.entities.pmd.PMD_Measure;
import edu.nju.service.common.ResultService;

/**
 * Created by Administrator on 2017/4/18.
 */
@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    private CheckDao checkDao;
    @Autowired
    private CheckStyleDao checkStyleDao;
    @Autowired
    private PMDDao pmdDao;
    @Override
    public List<SingleCheck> getGroupAllCheckstyleChecks(long groupId) {
        List<SingleCheck> groupChecks = new ArrayList<>();
        for(CheckLog check : checkDao.getAllCheck()){
            if(ifCheckdayPass(check.getCheckDate())){
                SingleCheck singleCheck = new SingleCheck(check.getCheckDate());
                Map<String, Object> querys = new HashMap<>();
                querys.put("checkId", check.getId());
                querys.put("groupId", groupId);
                Map<String, Integer> errorCount = new HashMap<>();
                for(CheckstyleResult checkstyleResult : checkStyleDao.findResult(querys));
            }
        }
        return null;
    }

    @Override
    public List<SingleCheck> getGroupAllPmdChecks(long groupId) {
	   List<SingleCheck> groupChecks = new ArrayList<>();
       for(CheckLog check : checkDao.getAllCheck()){
    	   if(ifCheckdayPass(check.getCheckDate())){
    		   SingleCheck singleCheck = new SingleCheck(check.getCheckDate());
    		   singleCheck.setCheckDate(check.getCheckDate());
    		   List<SingleResult> results=new ArrayList<SingleResult>();
    		   PMD_Measure measure = pmdDao.getById((int)check.getId(), groupId);
    		   int[] nums={measure.getBasic(),measure.getNaming(),measure.getUnusedcode(),
    				   measure.getCodesize(),measure.getBraces(),measure.getCoupling()};
    		   String[] labels={"basic","naming","unusedcode","codesize","braces","coupling"};
    		   for(int i=0;i<6;i++){
    			   SingleResult result=new SingleResult();
    			   result.setName(labels[i]);
    			   result.setCount(nums[i]);
    			   results.add(result);
    		   }
               singleCheck.setResults(results);
               groupChecks.add(singleCheck);
    	   }
       }
       return groupChecks; 
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
