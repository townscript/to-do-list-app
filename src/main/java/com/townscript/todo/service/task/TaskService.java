package main.java.com.townscript.todo.service.task;

import java.util.List;

import main.java.com.townscript.todo.model.Category;
import main.java.com.townscript.todo.model.Tag;
import main.java.com.townscript.todo.model.Task;

public interface TaskService {
	public int addTask(Task task);
	public void toggleTask(int taskid,Boolean value);
	public Task readTask(int taskid);
	public void makeSubtaskTask(int taskid);
	public void makeTaskSubtask(int taskid,int parentid,int sequenceNumber);
	public List<Tag> getTagsList(int taskid);
	public Category getCategory(int taskid);
	public void changeTaskName(int taskid,String newTaskName);
	public void DeleteTask(int taskid);
}
