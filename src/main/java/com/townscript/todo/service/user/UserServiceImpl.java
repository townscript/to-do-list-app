package main.java.com.townscript.todo.service.user;

import main.java.com.townscript.todo.dao.user.UserDao;
import main.java.com.townscript.todo.dao.user.UserDaoImpl;
import main.java.com.townscript.todo.model.User;

public class UserServiceImpl implements UserService{
	
	UserDao userDao = new UserDaoImpl();
	
	@Override
	public int registerUser(User user) {
		return userDao.addUser(user);
	}

	@Override
	public boolean authenticateUser(String username, String password) throws Exception {
		if(!userDao.checkUserExist(username)){
			throw new Exception("Invalid Username");
		}
		else{
			boolean returnValue = userDao.isAuthenticUser(username, password);
			if (returnValue == false){
				throw new Exception("Invalid password");
			}
			return returnValue;
		}
	}
	

	@Override
	public void changePassword(int userid, String newPassword) {
		userDao.updatePassword(userid, newPassword);
	}

	@Override
	public User getUserInfo(int userid) {
		return userDao.readUser(userid);
	}

	@Override
	public void deleteUser(int userid) {
		userDao.deleteUser(userid);	
	}

}
