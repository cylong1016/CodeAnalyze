package edu.nju.service.main.impl;

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
import edu.nju.dao.RegressionDao;
import edu.nju.dao.StudentScoreDao;
import edu.nju.dao.TeacherScoreDao;
import edu.nju.dao.checkstyle.CheckStyleDao;
import edu.nju.entities.CheckLog;
import edu.nju.entities.Regression;
import edu.nju.entities.StudentGroup;
import edu.nju.entities.StudentScore;
import edu.nju.entities.TeacherScore;
import edu.nju.entities.checkstyle.InternalStat;
import edu.nju.service.main.ScoreService;
import edu.nju.utils.Constant;

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
	@Autowired
	private RegressionDao regressionDao;
	@Autowired
	private CheckStyleDao checkStyleDao;

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
	 * @see edu.nju.service.main.ScoreService#getAllGroupScore()
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

	/**
	 */
	@Override
	public List<Regression> getRegression(String iter) {
		Map<String, Object> querys = new HashMap<>();
		querys.put("checkId", Long.parseLong(iter));
		List<Regression> regression = regressionDao.getRegressionByQuery(querys);
		return regression;
	}
	
	public Map<String, Object> getScatter(long checkId) {
		Map<String, Object> returnMap = new HashMap<>();

		Map<String, Object> querys = new HashMap<>();
		querys.put("checkId", checkId);
		List<TeacherScore> tscore = teacherScoreDao.getTeacherScoreByQuery(querys);
		Map<Long, Integer> tscoreMap = new HashMap<>();
		for(TeacherScore t : tscore) {
			tscoreMap.put(t.getGroupId(), t.getScore());
		}

		querys.put("toolName", Constant.CHECKSTYLE);
		List<StudentScore> cscore = studentScoreDao.getStudentScoreByQuery(querys);
		Collections.sort(cscore, Collections.reverseOrder(Comparator.comparing(StudentScore::getScore)));
		List<Regression> regressionList = regressionDao.getRegressionByQuery(querys);
		Regression regression = regressionList.get(0);
		Map<String, Object> content = getContent(cscore.get(0).getScore(), regression);
		// 获取散点
		int[][] checkstylescore = new int[cscore.size()][2];
		for(int i = 0; i < cscore.size(); i++) {
			StudentScore s = cscore.get(i);
			checkstylescore[i][0] = tscoreMap.get(s.getGroupId());
			checkstylescore[i][1] = s.getScore();
		}
		content.put("scatter", checkstylescore);
		returnMap.put(Constant.CHECKSTYLE, content);

		querys.put("toolName", Constant.PMD);
		List<StudentScore> pscore = studentScoreDao.getStudentScoreByQuery(querys);
		Collections.sort(pscore, Collections.reverseOrder(Comparator.comparing(StudentScore::getScore)));
		regressionList = regressionDao.getRegressionByQuery(querys);
		regression = regressionList.get(0);
		content = getContent(pscore.get(0).getScore(), regression);
		int[][] pmdscore = new int[pscore.size()][2];
		for(int i = 0; i < pscore.size(); i++) {
			StudentScore s = pscore.get(i);
			pmdscore[i][0] = tscoreMap.get(s.getGroupId());
			pmdscore[i][1] = s.getScore();
		}
		content.put("scatter", pmdscore);
		returnMap.put(Constant.PMD, content);

		Map<String, Object> internalTypeQuery = new HashMap<>();
		internalTypeQuery.put("checkId", checkId);
		for(String errorName : Constant.ERROR_NAME) {
			internalTypeQuery.put("internalType", errorName);
			querys.put("toolName", errorName);
			List<InternalStat> eStat = checkStyleDao.getStatList(internalTypeQuery);
			Collections.sort(eStat, Collections.reverseOrder(Comparator.comparing(InternalStat::getCount)));
			regressionList = regressionDao.getRegressionByQuery(querys);
			regression = regressionList.get(0);
			content = getContent(eStat.get(0).getCount(), regression);
			int[][] errorStat = new int[tscore.size()][2];
			for(int i = 0; i < eStat.size(); i++) {
				InternalStat e = eStat.get(i);
				errorStat[i][0] = tscoreMap.get(e.getGroupId());
				errorStat[i][1] = e.getCount();
			}
			content.put("scatter", errorStat);
			returnMap.put(errorName, content);
		}
		return returnMap;
	}

	private Map<String, Object> getContent(int maxScore, Regression regression){
		Map<String, Object> content = new HashMap<>();
		// 获取回归线起点
		double[] start = new double[2];
		if ( regression.getCffcA() < 0 ){
			start[1] = 0.0;
			start[0] = computeX(0.0, regression);
		} else {
			if (regression.getCffcA() > maxScore ){
				start[1] = maxScore;
				start[0] = computeX(start[1], regression);
			}else{
				start[0] = 0.0;
				start[1] = computeY(0.0, regression);
			}
		}
		content.put("start", start);
		// 获取回归线终点
		double[] end = new double[2];
		if ( regression.getCffcB() < 0 ){
			if ( computeX(0.0, regression) > 100 ){
				end[0] = 100.0;
				end[1] = computeY(end[0], regression);
			} else {
				end[1] = 0.0;
				end[0] = computeX(end[1], regression);
			}
		} else {
			if ( computeX(maxScore, regression) > 100 ){
				end[0] = 100.0;
				end[1] = computeY(end[0], regression);
			}else {
				end[1] = maxScore;
				end[0] = computeX(end[1], regression);
			}
		}
		content.put("end", end);
		return content;
	}

	private double computeX(double y, Regression regression){
		if (regression.getCffcB() == 0.0){
			return 0.0;
		}
		return (y - regression.getCffcA()) / regression.getCffcB();
	}
	private double computeY(double x, Regression regression){
		return regression.getCffcA() + regression.getCffcB() * x;
	}

}
