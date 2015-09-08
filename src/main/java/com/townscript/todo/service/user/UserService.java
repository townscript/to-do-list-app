package com.townscript.todo.service.user;

import java.util.List;
import java.util.TreeSet;

import com.townscript.todo.model.Category;
import com.townscript.todo.model.Tag;
import com.townscript.todo.model.Task;
import com.townscript.todo.model.User;

public interface UserService {
	public int registerUser(User user);
	public boolean authenticateUser(String username,String password) throws Exception;
	public void changePassword(int userid, String newPassword);
	public User getUserInfo(int userid);
	public void deleteUser(int userid);
	public List<Task> loadTasks(int userid);
	public TreeSet<Tag> loadTags(int userid);
	public TreeSet<Category> loadCategories(int userid);
}
