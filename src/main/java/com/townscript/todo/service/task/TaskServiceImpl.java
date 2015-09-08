package com.townscript.todo.service.task;



import java.util.ArrayList;
import java.util.List;

import com.townscript.todo.dao.category.CategoryDao;
import com.townscript.todo.dao.tag.TagDao;
import com.townscript.todo.dao.task.TaskDao;
import com.townscript.todo.model.Category;
import com.townscript.todo.model.Tag;
import com.townscript.todo.model.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	TaskDao taskDao;
	@Autowired
	TagDao tagDao;
	@Autowired
	CategoryDao categoryDao;
	
	public TaskDao getTaskDao() {
		return taskDao;
	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public TagDao getTagDao() {
		return tagDao;
	}

	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	@Override
	public int addTask(Task task) {
		return taskDao.addTask(task);
	}

	@Override
	public void toggleTask(int taskid, Boolean value) {
		Task task = taskDao.readTask(taskid);
		task.setMark(value);
		taskDao.updateTask(task);
	}

	@Override
	public Task readTask(int taskid) {
		return taskDao.readTask(taskid);
	}

	@Override
	public void makeSubtaskTask(int taskid) {
		Task task = taskDao.readTask(taskid);
		task.setSubtask(false);
		task.setParentid(-1);
		taskDao.updateTask(task);
	}

	@Override
	public void makeTaskSubtask(int taskid, int parentid,int sequenceNumber) {
		Task task = taskDao.readTask(taskid);
		task.setParentid(parentid);
		task.setSubtask(true);
		task.setSequenceNumber(sequenceNumber);
		taskDao.updateTask(task);
	}

	@Override
	public List<Tag> getTagsList(int taskid) {
		List<Tag> tagsList = new ArrayList<Tag>();
		tagsList = tagDao.getTagsofTask(taskid);
		return tagsList;
	}

	@Override
	public Category getCategory(int taskid) {
		return categoryDao.getCategoryofTask(taskid);
	}

	@Override
	public void changeTaskName(int taskid, String newTaskName) {
		Task task = taskDao.readTask(taskid);
		task.setTaskName(newTaskName);
		taskDao.updateTask(task);
		
	}

	@Override
	public void DeleteTask(int taskid) {
		taskDao.removeTask(taskid);
		//fetch tags of the task being removed
		List<Tag> tagsList = tagDao.getTagsofTask(taskid);
		String taskidString = Integer.toString(taskid);
		if(tagsList!=null){
		for(Tag tag : tagsList){
			String taskids = tag.getTaskids();
			//remove tag if it is associated only with the task being removed
			if(taskidString == taskids){
				tagDao.removeTag(tag.getId());
			}
			//otherwise remove the taskid of the task being removed from taskids of tag
			else{
				String newTaskids = "";
			      for (String retval: taskids.split("taskid, ")){
			    	  newTaskids +=  retval;
			      }
				tag.setTaskids(newTaskids);
				tagDao.updateTag(tag);
			}
		}
		}
		Category category = categoryDao.getCategoryofTask(taskid); //fetch category of the task being removed
		if(category!=null){
		String taskids = category.getTaskids();
		if(taskidString == taskids){ //remove category if it is associated only with the task being removed
			tagDao.removeTag(category.getId());
		}
		//otherwise remove the taskid of the task being removed from taskids of tag
		else{
			String newTaskids = "";
		      for (String retval: taskids.split("taskid, ")){
		    	  newTaskids +=  retval;
		      }
			category.setTaskids(newTaskids);
			categoryDao.updateCategory(category);
		}
		}
	}
}
