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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        for(CheckLog item : checks){
            groupScores.addCheckDate(item.getCheckDate());
        }
        List<TeacherScore> tscores = teacherScoreDao.getTeacherScoreByQuery(querys);
        List<StudentScore> sscores = studentScoreDao.getStudentScoreByQuery(querys);
        for(TeacherScore item : tscores){
            groupScores.addTeacherScore(item.getScore());
        }
        for(StudentScore item : sscores){
            groupScores.addCheckstyleScore(item.getCheckstyleScore());
            groupScores.addPmdScore(item.getPmdScore());
            groupScores.addSqScore(item.getSqScore());
        }
        return groupScores;
    }
}
