package org.app.service.ejb;

import java.util.Collection;
import java.util.List;
import javax.ejb.Remote;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.app.patterns.EntityRepository;
import org.app.service.entities.EntityBase;
import org.app.service.entities.Feature;
import org.app.service.entities.Diagnostic;

@Remote
public interface DiagnosticDataService{
	// CREATE or UPDATE
	Diagnostic addDiagnostic(Diagnostic dianosticToAdd);
	Diagnostic CreateDiagnostic(int a, String b);

	// DELETE
	String removeDiagnostic(Diagnostic diagnosticToDischage);

	// READ
	Diagnostic getDiagnosticByID(int diagnosticID);
	Collection<Diagnostic> getDiagnostics();
	
	
	// Custom READ: custom query
	Diagnostic getDiagnosticByName(String diagnosticName);
	
	// Others
	String getMessage();
}
