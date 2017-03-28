package edu.nju.dao.pmd;

import java.util.ArrayList;
import java.util.List;

import edu.nju.entities.PMD.PMD_FileIssues;
import edu.nju.entities.PMD.PMD_Measure;

public interface PMDDao {
	public boolean saveIssueNum(PMD_Measure measure);

	public List<PMD_Measure> getAllGroup(int iter);

	public boolean saveFileIssues(PMD_FileIssues issues);

	public List<PMD_FileIssues> getOneGroup(int iter, String issueType,String groupName);

	public PMD_Measure getMeasure(int iter, String groupName);

	public int getIter();

	public boolean setIter();

	public ArrayList<Double[]> getAve();
}
