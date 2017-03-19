package edu.nju.dao.pdm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.nju.dao.BaseDao;
import edu.nju.dao.pmd.PMDDao;
import edu.nju.entities.pmd.PMD_FileIssues;
import edu.nju.entities.pmd.PMD_Measure;

@Repository
public class PMDDaoImpl implements PMDDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public boolean saveIssueNum(PMD_Measure measure) {
		try{
			baseDao.save(measure);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public List<PMD_Measure> getAllGroup(int iter) {
		String hql = "from PMD_Measure where iter = "+iter;
		List<PMD_Measure> list = baseDao.find(hql);
		return list;
	}

	@Override
	public boolean saveFileIssues(PMD_FileIssues issues) {
		try{
			baseDao.save(issues);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PMD_FileIssues> getOneGroup(int iter, String issueType,String groupName) {
		String hql = "from PMD_FileIssues where iter = :iter and issueType = :issueType and groupName=:groupName";
		List<PMD_FileIssues> list=baseDao.getNewSession().createQuery(hql).setParameter("iter", iter).setParameter("issueType", issueType).setParameter("groupName", groupName).getResultList();
		return list;
	}

}
