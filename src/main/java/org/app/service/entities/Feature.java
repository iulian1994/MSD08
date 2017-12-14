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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

//abstract 

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Feature implements Comparable<Feature>, Serializable
{
	@Id @GeneratedValue @NotNull
	protected Integer featureID;
	private String name;
	private String description;
	
	@Enumerated
	protected FeatureCategory category;
	
	@Transient
	private List<Task> tasks = new ArrayList<>();
	
	public Integer getFeatureID() {
		return featureID;
	}
	public void setFeatureID(Integer featureID) {
		this.featureID = featureID;
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
	public FeatureCategory getCategory() {
		return category;
	}
	public void setCategory(FeatureCategory category) {
		this.category = category;
	}
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	public Feature() {
		super();
	}
	
	public Feature(Integer featureID, String name, String description,
			FeatureCategory category, List<Task> tasks) {
		super();
		this.featureID = featureID;
		this.name = name;
		this.description = description;
		this.category = category;
		this.tasks = tasks;
	}

	
	
	public Feature(Integer featureID, String name, String description) {
		super();
		this.featureID = featureID;
		this.name = name;
		this.description = description;
	}



	public enum FeatureCategory {
		BUSINESS, TECHNICAL;
	}

	@Override
	public int compareTo(Feature other) {
		if (this.equals(other))
			return 0;
		return this.getName().compareTo(other.getName());
	}
	public Feature(Integer featureID, String name) {
		super();
		this.featureID = featureID;
		this.name = name;
	}
	@Override
	public String toString() {
		return "Feature [featureID=" + featureID + ", name=" + name
				+ ", description=" + description + ", category=" + category
				+ ", tasks=" + tasks + "]";
	}	
}