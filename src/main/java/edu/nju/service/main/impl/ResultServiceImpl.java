package edu.nju.service.main.impl;

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
import edu.nju.entities.checkstyle.CheckType;
import edu.nju.entities.checkstyle.SubTypeStat;
import edu.nju.entities.pmd.PMD_Measure;
import edu.nju.service.main.ResultService;

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
		List<String> activeTypes = getActiveTypes();
		for(CheckLog check : checkDao.getAllCheck()) {
			if (ifCheckdayPass(check.getCheckDate())) {
				SingleCheck singleCheck = new SingleCheck(check.getCheckDate());
				Map<String, Object> querys = new HashMap<>();
				querys.put("checkId", check.getId());
				querys.put("groupId", groupId);
				for(SubTypeStat subTypeStat : checkStyleDao.getSubTypeStat(querys)) {
					if (activeTypes.indexOf(subTypeStat.getSubType()) != -1) {
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
		List<SingleCheck> groupChecks = new ArrayList<>();
		for(CheckLog check : checkDao.getAllCheck()) {
			if (ifCheckdayPass(check.getCheckDate())) {
				SingleCheck singleCheck = new SingleCheck(check.getCheckDate());
				singleCheck.setCheckDate(check.getCheckDate());
				List<SingleResult> results = new ArrayList<SingleResult>();
				PMD_Measure measure = pmdDao.getById((int)check.getId(), groupId);
				int[] nums = {measure.getBasic(), measure.getNaming(), measure.getUnusedcode(), measure.getCodesize(), measure.getBraces(), measure.getCoupling()};
				String[] labels = {"basic", "naming", "unusedcode", "codesize", "braces", "coupling"};
				for(int i = 0; i < 6; i++) {
					SingleResult result = new SingleResult();
					result.setName(labels[i]);
					result.setCount(nums[i]);
					results.add(result);
				}
				singleCheck.setResults(results);
				groupChecks.add(singleCheck);
			}
		}
		for(int i=0;i<groupChecks.size();i++){
			System.out.println(groupChecks.get(i).getCheckDate());
			List<SingleResult> singleResult=groupChecks.get(i).getResults();
			for(int j=0;j<singleResult.size();j++){
				System.out.println("  "+singleResult.get(j).getCount()+singleResult.get(j).getName());
			}
			System.out.println();
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
	private boolean ifCheckdayPass(Date date) {
		if (date.compareTo(new Date()) <= 0) {
			return true;
		} else {
			return false;
		}
	}

	private List<String> getActiveTypes() {
		List<String> activeTypes = new ArrayList<>();
		List<CheckType> activeTypesEntity = checkStyleDao.getActiveType();
		for(CheckType type : activeTypesEntity) {
			activeTypes.add(type.getSubType());
		}
		return activeTypes;
	}
}
