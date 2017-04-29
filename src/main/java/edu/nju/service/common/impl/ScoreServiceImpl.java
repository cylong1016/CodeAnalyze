package edu.nju.service.common.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.nju.Vo.common.GroupAllScore;
import edu.nju.dao.CheckDao;
import edu.nju.dao.GroupDao;
import edu.nju.dao.StudentScoreDao;
import edu.nju.dao.TeacherScoreDao;
import edu.nju.entities.CheckLog;
import edu.nju.entities.StudentGroup;
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
	@Autowired
	private GroupDao groupDao;

	@Override
	public GroupAllScore getGroupAllScore(long groupId) {
		GroupAllScore groupScores = new GroupAllScore(groupId);
		List<CheckLog> checks = checkDao.getAllCheck();
		Collections.sort(checks, Comparator.comparing(CheckLog::getCheckDate));

		List<TeacherScore> tscores;
		List<StudentScore> pmdscores;
		List<StudentScore> checkScores;
		for(CheckLog item : checks) {
			if (ifCheckdayPass(item.getCheckDate())) {
				Map<String, Object> querys = new HashMap<>();
				querys.put("groupId", groupId);
				querys.put("checkId", item.getId());
				groupScores.addCheckDate(item.getCheckDate());
				tscores = teacherScoreDao.getTeacherScoreByQuery(querys);
				if (!tscores.isEmpty()) {
					groupScores.addTeacherScore(tscores.get(0).getScore());
				}
				querys.put("toolName", "pmd");
				pmdscores = studentScoreDao.getStudentScoreByQuery(querys);
				if (!pmdscores.isEmpty()) {
					groupScores.addPmdScore(pmdscores.get(0).getScore());
				}
				querys.put("toolName", "checkstyle");
				checkScores = studentScoreDao.getStudentScoreByQuery(querys);
				if (!checkScores.isEmpty()) {
					groupScores.addCheckstyleScore(checkScores.get(0).getScore());
				}
			}
		}
		return groupScores;
	}

	private boolean ifCheckdayPass(Date date) {
		return date.compareTo(new Date()) <= 0;
	}

	/**
	 * @see edu.nju.service.common.ScoreService#getAllGroupScore()
	 */
	@Override
	public List<GroupAllScore> getAllGroupScore() {
		List<GroupAllScore> allGroupScore = new ArrayList<GroupAllScore>();
		List<StudentGroup> allGroup = groupDao.getAllGroup();
		for(StudentGroup studentGroup : allGroup) {
			GroupAllScore groupScore = getGroupAllScore(studentGroup.getId());
			groupScore.setGroupName(studentGroup.getGroupName());
			allGroupScore.add(groupScore);
		}
		return allGroupScore;
	}
}
