package com.townscript.todo.dao.category;

import com.townscript.todo.model.Category;

public interface CategoryDao {
	public int addCategory(Category category);
	public void removeCategory(int categoryid);
	public Category readCategory(int categoryid);
	public void updateCategory(Category category);
	public Category getCategoryofTask(int taskid);
}
