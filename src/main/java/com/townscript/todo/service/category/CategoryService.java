package main.java.com.townscript.todo.service.category;

import main.java.com.townscript.todo.model.Category;

public interface CategoryService {
	public int addCategory(Category category);
	public void addExistingCategorytoTask(Category category, int taskid);
	public void deleteCategory(int categoryid);
	public Category readCategory(int categoryid);
	public void changeCategoryName(int categoryid,String newCategoryName);
}
