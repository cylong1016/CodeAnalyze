package edu.nju.entities.codacy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author lsy
 * 目前项目水平
 */
@Entity
@Table(name = "codacy_level")
public class CodacyLevel {
	private String id;
	private String projectId;
	private String commitId;
	private String state;
	private int nrIssues;
	private String grade;
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
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	public int getNrIssues() {
		return nrIssues;
	}
	public void setNrIssues(int nrIssues) {
		this.nrIssues = nrIssues;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
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
