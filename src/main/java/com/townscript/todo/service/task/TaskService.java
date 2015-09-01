package main.java.com.townscript.todo.service.task;

import main.java.com.townscript.todo.model.Task;

public interface TaskService {
	public int addTask(Task task);
	public void markTaskDone(int taskid);
	public Task readTask(int taskid);
	public void addDefaultTasks();
	public void makeSubtaskTask(int taskid);
	public void makeTaskSubtask(int taskid,int parentid);
	public String getTagsList(int taskid);
	public int getCategory(int taskid);
	public void changeTaskName(int taskid,String newTaskName);
}