package edu.nju.service.common.impl;

import edu.nju.Vo.common.GroupAllScore;
import edu.nju.dao.CheckDao;
import edu.nju.dao.StudentScoreDao;
import edu.nju.dao.TeacherScoreDao;
import edu.nju.entities.CheckLog;
import edu.nju.entities.StudentScore;
import edu.nju.entities.TeacherScore;
import edu.nju.service.common.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2017/4/18.
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private TeacherScoreDao teacherScoreDao;
    @Autowired
    private StudentScoreDao studentScoreDao;
    @Autowired
    private CheckDao checkDao;

    @Override
    public GroupAllScore getGroupAllScore(long groupId) {
        GroupAllScore groupScores = new GroupAllScore(groupId);
        Map<String, Object> querys = new HashMap<>();
        querys.put("groupId", groupId);
        List<CheckLog> checks = checkDao.getAllCheck();
//        Collections.sort(checks, Comparator.comparing(CheckLog::getCheckDate));
        for(CheckLog item : checks){
            if(ifCheckdayPass(item.getCheckDate())){
                groupScores.addCheckDate(item.getCheckDate());
                querys.put("checkId", item.getId());
                TeacherScore tscore = teacherScoreDao.getTeacherScoreByQuery(querys).get(0);
                groupScores.addTeacherScore(tscore.getScore());
                StudentScore sscores = studentScoreDao.getStudentScoreByQuery(querys).get(0);
                groupScores.addCheckstyleScore(sscores.getCheckstyleScore());
                groupScores.addPmdScore(sscores.getPmdScore());
                groupScores.addSqScore(sscores.getSqScore());
            }
        }
        return groupScores;
    }

    private boolean ifCheckdayPass(Date date){
        if ( date.compareTo(new Date()) <= 0 ){
            return true;
        }else {
            return false;
        }
    }
}
