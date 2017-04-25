package edu.nju.service.common.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.nju.Vo.common.GroupAllScore;
import edu.nju.dao.CheckDao;
import edu.nju.dao.StudentScoreDao;
import edu.nju.dao.TeacherScoreDao;
import edu.nju.entities.CheckLog;
import edu.nju.entities.StudentScore;
import edu.nju.entities.TeacherScore;
import edu.nju.service.common.ScoreService;

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
        Collections.sort(checks, Comparator.comparing(CheckLog::getCheckDate));

        List<TeacherScore> tscores;
        List<StudentScore> pmdscores;
        List<StudentScore> checkScores;
        for(CheckLog item : checks) {
            if (ifCheckdayPass(item.getCheckDate())) {
                querys.put("checkId", item.getId());
                tscores = teacherScoreDao.getTeacherScoreByQuery(querys);
                querys.put("toolName", "pmd");
                pmdscores = studentScoreDao.getStudentScoreByQuery(querys);
                querys.put("toolName", "checkstyle");
                checkScores = studentScoreDao.getStudentScoreByQuery(querys);
                if (tscores.size() > 0 && pmdscores.size() > 0 && checkScores.size() > 0) {
                    groupScores.addTeacherScore(tscores.get(0).getScore());
                    groupScores.addCheckstyleScore(checkScores.get(0).getScore());
                    groupScores.addPmdScore(pmdscores.get(0).getScore());
                }
            }
        }
        return groupScores;
    }

	private boolean ifCheckdayPass(Date date) {
		if (date.compareTo(new Date()) <= 0) {
			return true;
		} else {
			return false;
		}
	}
}
