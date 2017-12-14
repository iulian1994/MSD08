package org.app.service.ejb;

import javax.ejb.Remote;

import org.app.patterns.EntityRepository;
import org.app.service.entities.Sprint;

@Remote
public interface SprintDataService extends EntityRepository<Sprint>{
	// Classic CRUD methods
	// CREATE and UPDATE
	/* Sprint add(Sprint)					inherited from EntityRepository*/
	// READ
	/* Sprint getById(Sprint)				inherited from EntityRepository*/
	/* Collection<Sprint> toCollection()	inherited from EntityRepository*/
	// DELETE
	/* boolean remove(Sprint)				inherited from EntityRepository*/
	
	// Other operations
	String getMessage();
}