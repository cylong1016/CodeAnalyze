package edu.nju.entities.codacy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author lsy
 * 某次提交水平
 */
@Entity
@Table(name = "codacy_commit")
public class CodacyCommit {

	private String id;
	private String commitId;
	private String state;
	private int newIssues;
	private int fixIssues;
	private int complexity;
	private int nrClones;
	
	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCommitId() {
		return commitId;
	}
	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getNewIssues() {
		return newIssues;
	}
	public void setNewIssues(int newIssues) {
		this.newIssues = newIssues;
	}
	public int getFixIssues() {
		return fixIssues;
	}
	public void setFixIssues(int fixIssues) {
		this.fixIssues = fixIssues;
	}
	public int getComplexity() {
		return complexity;
	}
	public void setComplexity(int complexity) {
		this.complexity = complexity;
	}
	public int getNrClones() {
		return nrClones;
	}
	public void setNrClones(int nrClones) {
		this.nrClones = nrClones;
	}
	
}
