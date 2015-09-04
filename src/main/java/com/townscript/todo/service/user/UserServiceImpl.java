package main.java.com.townscript.todo.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import main.java.com.townscript.todo.dao.category.CategoryDao;
import main.java.com.townscript.todo.dao.category.CategoryDaoImpl;
import main.java.com.townscript.todo.dao.tag.TagDao;
import main.java.com.townscript.todo.dao.tag.TagDaoImpl;
import main.java.com.townscript.todo.dao.task.TaskDao;
import main.java.com.townscript.todo.dao.task.TaskDaoImpl;
import main.java.com.townscript.todo.dao.user.UserDao;
import main.java.com.townscript.todo.dao.user.UserDaoImpl;
import main.java.com.townscript.todo.model.Category;
import main.java.com.townscript.todo.model.Tag;
import main.java.com.townscript.todo.model.Task;
import main.java.com.townscript.todo.model.User;

public class UserServiceImpl implements UserService{
	
	UserDao userDao = new UserDaoImpl();
	TaskDao taskDao = new TaskDaoImpl();
	TagDao tagDao = new TagDaoImpl();
	CategoryDao categoryDao = new CategoryDaoImpl();
	
	@Override
	public int registerUser(User user) {
		int userid = userDao.addUser(user);
		//add default tasks
		Task defaultTask1 = new Task();
		Task defaultTask2 = new Task();
		defaultTask1.setTaskName("Buy groceries today");
		defaultTask2.setTaskName("Write code tomorrow");
		defaultTask1.setUserid(userid);
		defaultTask2.setUserid(userid);
		int taskid1 = taskDao.addTask(defaultTask1);
		int taskid2 = taskDao.addTask(defaultTask2);
		String defaulttask1Id = Integer.toString(taskid1);
		String defaulttask2Id = Integer.toString(taskid2);
		//add default tags
		Tag defaulttag1 = new Tag();
		Tag defaulttag2 = new Tag();
		Tag defaulttag3 = new Tag();
		Tag defaulttag4 = new Tag();
		defaulttag1.setTagName("office");
		defaulttag1.setTaskids(defaulttask2Id);
		defaulttag2.setTagName("home");
		defaulttag2.setTaskids(defaulttask1Id);
		defaulttag3.setTagName("vegetables");
		defaulttag3.setTaskids(defaulttask1Id);
		defaulttag4.setTagName("project");
		defaulttag4.setTaskids(defaulttask2Id);
		tagDao.addTag(defaulttag1);
		tagDao.addTag(defaulttag2);
		tagDao.addTag(defaulttag3);
		tagDao.addTag(defaulttag4);
		//add default categories
		Category defaultCategory1 = new Category();
		Category defaultCategory2 = new Category();
		defaultCategory1.setCategoryName("work");
		defaultCategory1.setTaskids(defaulttask2Id);
		defaultCategory2.setCategoryName("personal");
		defaultCategory2.setTaskids(defaulttask1Id);
		categoryDao.addCategory(defaultCategory1);
		categoryDao.addCategory(defaultCategory2);
		return userid;
	}

	@Override
	public boolean authenticateUser(String username, String password) throws Exception {
		
			boolean returnValue = userDao.isAuthenticUser(username, password);
			if (returnValue == false){
				throw new Exception("Invalid credentials");
			}
			return returnValue;
		
	}
	

	@Override
	public void changePassword(int userid, String newPassword) {
		User user = userDao.readUser(userid);
		user.setPassword(newPassword);
		userDao.updateUser(user);
	}

	@Override
	public User getUserInfo(int userid) {
		return userDao.readUser(userid);
	}

	@Override
	public void deleteUser(int userid) {
		userDao.deleteUser(userid);	
	}

	@Override
	public List<Task> loadTasks(int userid) {
		List<Task> tasksList = taskDao.readTasksofUsers(userid);
		return tasksList;
	}

	//load tags of all tasks of the user
	@Override
	public TreeSet<Tag> loadTags(int userid) { 
		TreeSet<Tag> tagsList = new TreeSet<Tag>(); //use treeset to disallow duplicates and get alphabetical order of tags
		List<Task> tasksList = taskDao.readTasksofUsers(userid);
		for (Task task : tasksList) {
			List<Tag> tagsListofTask = new ArrayList<Tag>();
			tagsListofTask = tagDao.getTagsofTask(task.getId());
			for(Tag tag : tagsListofTask){
				tagsList.add(tag);
			}
		}
		return tagsList;
	}

	//load categories of all tasks of the user
	@Override
	public TreeSet<Category> loadCategories(int userid) {
		TreeSet<Category> categoriesList = new TreeSet<Category>(); //use treeset to disallow duplicates and get alphabetical order of categories
		List<Task> tasksList = taskDao.readTasksofUsers(userid);
		for (Task task : tasksList) {
		    Category category = categoryDao.getCategoryofTask(task.getId());
		    categoriesList.add(category);
		}
		return categoriesList;
	}
}
