package org.app.service.ejb;
import javax.ejb.Remote;
import java.util.Collection;
import org.app.service.entities.Task;

@Remote
public interface TaskDataService {
	// CREATE or UPDATE
	Task addTask(Task taskToAdd);

	// DELETE
	String removeTask(Task taskToRemove);

	// READ
	Task getTaskByID(Integer taskID);
	Collection<Task> getTasks();
	
	// Others
	String getMessage();
}
