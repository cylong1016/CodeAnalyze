package edu.nju.service.codacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.nju.dao.codacy.CodacyDao;
import edu.nju.entities.codacy.CodacyProject;

@Service
public class CodacyService {
	
	@Autowired 
	private CodacyDao dao;
	
	public void insertData(){
		CodacyProject cp = new CodacyProject();
		cp.setApi_token("2drew0z1Eifw2P5g7jBO");
		cp.setProject_token("87fb09243da64a73960856cf5f1db4fd");
		cp.setProjectName("tss");
		cp.setUserName("864986781");
		dao.insert(cp);
	}
}
