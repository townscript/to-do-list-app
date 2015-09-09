package com.townscript.todo.dao.user;

import com.townscript.todo.model.User;
public interface UserDao {
	int addUser(User user);
	void deleteUser(int userId);
	void updateUser(User user);
	boolean isAuthenticUser(String username, String password);
	User loadUser(int userId);
}
