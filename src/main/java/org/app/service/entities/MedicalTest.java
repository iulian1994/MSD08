package org.app.service.entities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class MedicalTest implements Comparable<MedicalTest>, Serializable {
	@Id @GeneratedValue @NotNull
	protected Integer testID;
	private String name;
	private String description;
	@ManyToOne
	private MedicalService medsrv;
	
	public MedicalTest() {
		super();
	}
	public Integer getTestID() {
		return testID;
	}
	public void setTestID(Integer testID) {
		this.testID = testID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int compareTo(MedicalTest other) {
		if (this.equals(other))
			return 0;
		return this.getName().compareTo(other.getName());
	}
	public MedicalTest(Integer testID, String name) {
		super();
		this.testID = testID;
		this.name = name;
	}
	@Override
	public String toString() {
		return "MedicalTest [testID=" + testID + ", name=" + name + ", description=" + description + "]";
	}
	
}
