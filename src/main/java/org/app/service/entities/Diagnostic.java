package org.app.service.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Diagnostic implements Serializable {
private static final long serialVersionUID = 7745880111522579906L;

	@Id
	@GeneratedValue
	private int diagnosticId;
	private String Comments;
	private Long labourCost;

	public Diagnostic() {
			super();
		}   
	public int getDiagnosticId() {
			return this.diagnosticId;
		}

	public void setDiagnosticId(int diagnosticId) {
			this.diagnosticId = diagnosticId;
		}   
	public String getComments() {
			return this.Comments;
		}

	public void setComments(String Comments) {
			this.Comments = Comments;
		}   
	public Long getLabourCost() {
			return this.labourCost;
		}

	public void setLabourCost(Long labourCost) {
			this.labourCost = labourCost;
		}
	public Diagnostic(int diagnosticId, String comments, Long labourCost) {
		super();
		this.diagnosticId = diagnosticId;
		Comments = comments;
		this.labourCost = labourCost;
	}

}
