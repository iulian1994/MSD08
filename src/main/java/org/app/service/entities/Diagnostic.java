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
	public Diagnostic(int diagnosticId, String comments) {
		super();
		this.diagnosticId = diagnosticId;
		this.Comments = comments;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Comments == null) ? 0 : Comments.hashCode());
		result = prime * result + diagnosticId;
		result = prime * result + ((labourCost == null) ? 0 : labourCost.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Diagnostic other = (Diagnostic) obj;
		if (Comments == null) {
			if (other.Comments != null)
				return false;
		} else if (!Comments.equals(other.Comments))
			return false;
		if (diagnosticId != other.diagnosticId)
			return false;
		if (labourCost == null) {
			if (other.labourCost != null)
				return false;
		} else if (!labourCost.equals(other.labourCost))
			return false;
		return true;
	}

}
