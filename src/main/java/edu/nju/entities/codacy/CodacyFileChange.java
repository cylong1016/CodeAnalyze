package edu.nju.entities.codacy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author lsy
 * 某次提交的文件内容变化
 */
@Entity
@Table(name = "codacy_file_change")
public class CodacyFileChange {
	private String id;
	/** * CodacyCommit的主键 */
	private String cid;
	private String path;
	private int newIssues;
	private int fixedIssues;
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
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getNewIssues() {
		return newIssues;
	}
	public void setNewIssues(int newIssues) {
		this.newIssues = newIssues;
	}
	public int getFixedIssues() {
		return fixedIssues;
	}
	public void setFixedIssues(int fixedIssues) {
		this.fixedIssues = fixedIssues;
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
