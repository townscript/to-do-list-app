package main.java.com.townscript.todo.service.task;

import main.java.com.townscript.todo.dao.task.TaskDao;
import main.java.com.townscript.todo.dao.task.TaskDaoImpl;
import main.java.com.townscript.todo.model.Task;

public class TaskServiceImpl implements TaskService{

	TaskDao taskDao = new TaskDaoImpl();
	
	@Override
	public int addTask(Task task) {
		return taskDao.addTask(task);
	}

	@Override
	public void markTaskDone(int taskid) {
		taskDao.markTaskDone(taskid);
	}

	@Override
	public Task readTask(int taskid) {
		return taskDao.readTask(taskid);
	}

	@Override
	public void addDefaultTasks() {
		Task defaultTask1 = new Task();
		Task defaultTask2 = new Task();
		defaultTask1.setTaskName("Buy groceries today");
		defaultTask2.setTaskName("Write code tomorrow");
		taskDao.addTask(defaultTask1);
		taskDao.addTask(defaultTask2);
	}

	@Override
	public void makeSubtaskTask(int taskid) {
		taskDao.makeSubtaskTask(taskid);
	}

	@Override
	public void makeTaskSubtask(int taskid, int parentid) {
		taskDao.makeTaskSubtask(taskid, parentid);
	}

	@Override
	public String getTagsList(int taskid) {
		return taskDao.getTagsList(taskid);
	}

	@Override
	public int getCategory(int taskid) {
		return taskDao.getCategory(taskid);
	}

	@Override
	public void changeTaskName(int taskid, String newTaskName) {
		taskDao.updateTaskName(taskid, newTaskName);
	}

}
