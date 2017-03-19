package edu.nju.dao.pmd;

import java.util.List;

import edu.nju.entities.pmd.PMD_FileIssues;
import edu.nju.entities.pmd.PMD_Measure;

public interface PMDDao {
	public boolean saveIssueNum(PMD_Measure measure);

	public List<PMD_Measure> getAllGroup(int iter);

	public boolean saveFileIssues(PMD_FileIssues issues);

	public List<PMD_FileIssues> getOneGroup(int iter, String issueType,String groupName);
}
