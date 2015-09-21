package com.townscript.todo.controller.user;

import java.util.List;
import java.util.TreeSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.townscript.todo.model.Category;
import com.townscript.todo.model.Tag;
import com.townscript.todo.model.Task;
import com.townscript.todo.model.User;
import com.townscript.todo.service.user.UserService;



@RestController
@RequestMapping("/user")
public class UserController {

	private UserService userService;
	public UserController() {

		if (userService == null) {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"com/townscript/todo/mainbeans.xml");
			userService = (UserService) context
					.getBean("UserServiceImpl");
		}
	};
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/loadtasks", method=RequestMethod.GET)
	public List<Task> loadUserTasks(@RequestParam(value="id",required=true) int id){
		return userService.loadTasks(id);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/loadtags", method=RequestMethod.GET)
	public TreeSet<Tag> loadUserTags(@RequestParam(value="id",required=true) int id){
		return userService.loadTags(id);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/loadcategory", method=RequestMethod.GET)
	public TreeSet<Category> loadCategory(@RequestParam(value="id",required=true) int id){
		return userService.loadCategories(id);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/deleteuser", method=RequestMethod.GET)
	public void deleteUser(@RequestParam(value="id",required=true) int id){
		userService.deleteUser(id);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/changepass", method=RequestMethod.GET)
	public void changePassword(@RequestParam(value="id",required=true) int id, @RequestParam(value="pass",required=true) String password){
		userService.changePassword(id, password);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/get", method=RequestMethod.GET)
	public User accessUser(@RequestParam(value="id",required=true) int id){
		return userService.getUserInfo(id);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value ="/showadd",method = RequestMethod.GET)
	public ModelAndView showAddUser(){
		 return new ModelAndView("/static/adduser.html"); 
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value ="/add", method=RequestMethod.POST)
	public User addUser(@RequestParam(value="username") String username,@RequestParam(value="password") String password,@RequestParam(value="firstname") String firstname, @RequestParam(value="lastname") String lastname){
		User user = new User();
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setUsername(username);
		user.setPassword(password);
		userService.registerUser(user);
		return user;
	}

}
