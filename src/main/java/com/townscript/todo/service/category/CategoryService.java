package com.townscript.todo.service.category;

import com.townscript.todo.model.Category;

public interface CategoryService {
	int addCategory(Category category);
	void addExistingCategorytoTask(Category category, int taskId);
	void deleteCategory(int categoryId);
	Category readCategory(int categoryId);
	void changeCategoryName(int categoryId,String newCategoryName);
}
