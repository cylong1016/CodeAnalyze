package edu.nju.dao.pdm.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.nju.dao.BaseDao;
import edu.nju.dao.pmd.PMDDao;
import edu.nju.entities.PMD.PMD_FileIssues;
import edu.nju.entities.PMD.PMD_Iter;
import edu.nju.entities.PMD.PMD_Measure;

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
		if(iter==0){
			iter=getIter();
		}
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

	@SuppressWarnings("unchecked")
	@Override
	public PMD_Measure getMeasure(int iter, String groupName) {
		String hql = "from PMD_Measure where iter = :iter and groupName=:groupName";
		List<PMD_Measure> list=baseDao.getNewSession().createQuery(hql).setParameter("iter", iter).setParameter("groupName", groupName).getResultList();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getIter() {
		String hql = "from PMD_Iter";
		List<PMD_Iter> list=baseDao.getNewSession().createQuery(hql).getResultList();
		if(list.size()>0){
			return list.get(0).getIter();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean setIter() {
		String hql = "from PMD_Iter";
		List<PMD_Iter> list=baseDao.getNewSession().createQuery(hql).getResultList();
		int iter=0;
		if(list.size()>0){
			PMD_Iter pmd=list.get(0);
			iter=pmd.getIter();
			iter++;
			pmd.setIter(iter);
			baseDao.update(pmd);
		}else{
			PMD_Iter pmd = new PMD_Iter();
			pmd.setIter(1);
			baseDao.save(pmd);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Double[]> getAve() {
		int iter = getIter();
		ArrayList<Double[]> result=new ArrayList<Double[]>();
		for(int i=1;i<=iter;i++){
			String hql = "from PMD_Measure where iter = :iter";
			List<PMD_Measure> list=baseDao.getNewSession().createQuery(hql).setParameter("iter", i).getResultList();
			double basic=0,clone=0,size=0,coupling=0,naming=0,unused=0;
			for(int j=0;j<list.size();j++){
				PMD_Measure mea= list.get(j);
				basic=basic+mea.getBasic();
				clone=clone+mea.getClone();
				size=size+mea.getCodesize();
				coupling=coupling+mea.getCoupling();
				naming=naming+mea.getNaming();
				unused=unused+mea.getUnusedcode();
			}
			int listSize=list.size();
			DecimalFormat df = new DecimalFormat("#.##");
			Double[] darr=new Double[6];
			darr[0]=Double.parseDouble(df.format(basic/listSize));
			darr[1]=Double.parseDouble(df.format(clone/listSize));
			darr[2]=Double.parseDouble(df.format(size/listSize));
			darr[3]=Double.parseDouble(df.format(coupling/listSize));
			darr[4]=Double.parseDouble(df.format(naming/listSize));
			darr[5]=Double.parseDouble(df.format(unused/listSize));
			result.add(darr);
		}
		return result;
	}


}
