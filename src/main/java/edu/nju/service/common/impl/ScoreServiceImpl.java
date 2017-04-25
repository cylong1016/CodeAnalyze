package edu.nju.service.common.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
<<<<<<< HEAD
        List<TeacherScore> tscores=new ArrayList<TeacherScore>();
        List<StudentScore> pmdscores=new ArrayList<StudentScore>();
        List<StudentScore> checkScores=new ArrayList<StudentScore>();
        for(CheckLog item : checks){
        	long checkId = item.getId();
            groupScores.addCheckDate(item.getCheckDate());
            querys=new HashMap<>();
            querys.put("groupId", groupId);
            querys.put("checkId", checkId);
	        List<TeacherScore> tempScores = teacherScoreDao.getTeacherScoreByQuery(querys);
	        querys.put("toolName", "pmd");
	        List<StudentScore> tempPmd = studentScoreDao.getStudentScoreByQuery(querys);
	        querys.replace("toolName", "checkstyle");
	        List<StudentScore> tempCheck = studentScoreDao.getStudentScoreByQuery(querys);
            if(tempScores.size()>0&&tempPmd.size()>0&&tempCheck.size()>0){
            	tscores.add(tempScores.get(0));
            	pmdscores.add(tempPmd.get(0));
            	checkScores.add(tempCheck.get(0));
            }
        }
        for(TeacherScore item : tscores){
        	groupScores.addTeacherScore(item.getScore());
        }
        for(StudentScore item : pmdscores){
        	groupScores.addPmdScore(item.getScore());
        }
        for(StudentScore item : checkScores){
        	groupScores.addCheckstyleScore(item.getScore());
=======
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
>>>>>>> refs/remotes/origin/wenzi
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
