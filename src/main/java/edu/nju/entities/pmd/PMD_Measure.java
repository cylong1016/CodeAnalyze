package edu.nju.entities.pmd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "pmd_measure")
public class PMD_Measure {
	private String id;
	private long groupId;
	private String groupName;
	private int basic;
	private int naming;
	private int unusedcode;
	private int codesize;
	private int clone;
	private int coupling;
	private int iter;//第几次迭代
	
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
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getIter() {
		return iter;
	}
	public void setIter(int iter) {
		this.iter = iter;
	}
	public int getBasic() {
		return basic;
	}
	public void setBasic(int basic) {
		this.basic = basic;
	}
	public int getNaming() {
		return naming;
	}
	public void setNaming(int naming) {
		this.naming = naming;
	}
	public int getUnusedcode() {
		return unusedcode;
	}
	public void setUnusedcode(int unusedcode) {
		this.unusedcode = unusedcode;
	}
	public int getCodesize() {
		return codesize;
	}
	public void setCodesize(int codesize) {
		this.codesize = codesize;
	}
	public int getClone() {
		return clone;
	}
	public void setClone(int clone) {
		this.clone = clone;
	}
	public int getCoupling() {
		return coupling;
	}
	public void setCoupling(int coupling) {
		this.coupling = coupling;
	}
	@Override
	public String toString() {
		return "PMD_Measure [id=" + id + ", basic=" + basic + ", naming=" + naming
				+ ", unusedcode=" + unusedcode + ", codesize=" + codesize + ", clone=" + clone + ", coupling="
				+ coupling + ", iter=" + iter + "]";
	}
	
	
}
