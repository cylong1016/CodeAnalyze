package edu.nju.dao.impl;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.nju.dao.TestDao;

@Transactional
@Repository("testDao")
public class TestDaoImpl implements TestDao{

	public void test() {
		System.out.println("success!");
	}

}
