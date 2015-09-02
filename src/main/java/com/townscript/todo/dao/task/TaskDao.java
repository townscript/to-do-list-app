package main.java.com.townscript.todo.dao.task;

import java.util.List;

import main.java.com.townscript.todo.model.Task;

public interface TaskDao {
	public int addTask(Task task);
	public Task readTask(int taskid);
	public List<Task> readTasksofUsers(int userid);
	public void updateTask(Task task);
	public void removeTask(int taskid);
}
