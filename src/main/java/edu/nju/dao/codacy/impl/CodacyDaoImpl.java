package edu.nju.dao.codacy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.nju.dao.BaseDao;
import edu.nju.dao.codacy.CodacyDao;
import edu.nju.entities.codacy.CodacyProject;


@Repository
public class CodacyDaoImpl implements CodacyDao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public void insert(CodacyProject cp) {
		baseDao.save(cp);
	}

}
