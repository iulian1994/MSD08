package org.app.service.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Diagnostic implements Serializable {
private static final long serialVersionUID = 7745880111522579906L;

	@Id
	private Integer diagnosticId;
	private String Comments;
	public Integer getDiagnosticId() {
		return diagnosticId;
	}
	public void setDiagnosticId(Integer diagnosticId) {
		this.diagnosticId = diagnosticId;
	}
	public String getComments() {
		return Comments;
	}
	public void setComments(String comments) {
		Comments = comments;
	}
	public Diagnostic(Integer diagnosticId, String comments) {
		super();
		this.diagnosticId = diagnosticId;
		this.Comments = comments;
	}
	public Diagnostic() {
		super();
	}

	

}
