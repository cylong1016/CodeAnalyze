package edu.nju.entities.codacy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author lsy
 * 项目基本信息
 */
@Entity
@Table(name = "codacy_project")
public class CodacyProject {
	private String id;
	private String userName;
	private String projectName;
	private String api_token;
	private String project_token;
	
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getApi_token() {
		return api_token;
	}
	public void setApi_token(String api_token) {
		this.api_token = api_token;
	}
	public String getProject_token() {
		return project_token;
	}
	public void setProject_token(String project_token) {
		this.project_token = project_token;
	}
	
}
