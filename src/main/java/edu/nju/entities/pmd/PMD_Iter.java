package edu.nju.entities.PMD;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "pmd_iter")
public class PMD_Iter {
	private String id;
	private int iter;
	
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
	public int getIter() {
		return iter;
	}
	public void setIter(int iter) {
		this.iter = iter;
	}
	@Override
	public String toString() {
		return "PMD_Iter [id=" + id +  ", iter=" + iter + "]";
	}
	
	

}
