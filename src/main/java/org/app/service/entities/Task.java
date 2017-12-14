package org.app.service.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Task {

@Id
@GeneratedValue
private int taskID;
private String taskDescription;
private String taskType;
@Temporal(TemporalType.DATE)
private Date dueDate;
@ManyToOne
private Employee responsible;

public int getTaskID() {
	return taskID;
}

public void setTaskID(int taskID) {
	this.taskID = taskID;
}


public String getTaskDescription() {
	return taskDescription;
}


public void setTaskDescription(String taskDescription) {
	this.taskDescription = taskDescription;
}


public String getTaskType() {
	return taskType;
}


public void setTaskType(String taskType) {
	this.taskType = taskType;
}


public Date getDueDate() {
	return dueDate;
}


public void setDueDate(Date dueDate) {
	this.dueDate = dueDate;
}


public Employee getResponsible() {
	return responsible;
}


public void setResponsible(Employee responsible) {
	this.responsible = responsible;
	}
}
