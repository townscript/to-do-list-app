package main.java.com.townscript.todo.service.category;

import main.java.com.townscript.todo.model.Category;

public interface CategoryService {
	public int addCategory(Category category);
	public void deleteCategory(int categoryid);
	public Category readCategory(int categoryid);
	public void addDefaultCategories();
	public void changeCategoryName(int categoryid,String newCategoryName);
}
