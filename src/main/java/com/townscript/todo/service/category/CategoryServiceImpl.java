package com.townscript.todo.service.category;

import com.townscript.todo.dao.category.CategoryDao;
import com.townscript.todo.model.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryDao categoryDao;
	
	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Override
	public int addCategory(Category category) {
		return categoryDao.addCategory(category);
	}

	@Override
	public void deleteCategory(int categoryId) {
		categoryDao.removeCategory(categoryId);
	}

	@Override
	public Category readCategory(int categoryId) {
		return categoryDao.loadCategory(categoryId);
	}

	@Override
	public void changeCategoryName(int categoryId, String newCategoryName) {
		Category category = categoryDao.loadCategory(categoryId);
		category.setCategoryName(newCategoryName);
		categoryDao.updateCategory(category);
	}

	@Override
	public void addExistingCategorytoTask(Category category, int taskId) {
		String taskIds = category.getTaskids();
		String taskIdString = Integer.toString(taskId);
		category.setTaskids(taskIds+", "+taskIdString); //updating taskids of category
		categoryDao.updateCategory(category);
	}
}
