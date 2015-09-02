package main.java.com.townscript.todo.service.user;

import java.util.HashSet;
import java.util.List;

import main.java.com.townscript.todo.model.Category;
import main.java.com.townscript.todo.model.Tag;
import main.java.com.townscript.todo.model.Task;
import main.java.com.townscript.todo.model.User;

public interface UserService {
	public int registerUser(User user);
	public boolean authenticateUser(String username,String password) throws Exception;
	public void changePassword(int userid, String newPassword);
	public User getUserInfo(int userid);
	public void deleteUser(int userid);
	public List<Task> loadTasks(int userid);
	public HashSet<Tag> loadTags(int userid);
	public HashSet<Category> loadCategories(int userid);
}
