package com.townscript.todo.dao.category;

import com.townscript.todo.model.Category;


public interface CategoryDao {
	
	// No need to use "Public" here, as by default Interface declared methods are public
	public int addCategory(Category category);
	public void removeCategory(int categoryid);
	
	// For clearity of reading, we should use CamelCase, it should be categoryId
	public Category readCategory(int categoryid);
	public void updateCategory(Category category);
	public Category getCategoryofTask(int taskid);
}
