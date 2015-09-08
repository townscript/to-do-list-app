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
	public void deleteCategory(int categoryid) {
		categoryDao.removeCategory(categoryid);
	}

	@Override
	public Category readCategory(int categoryid) {
		return categoryDao.readCategory(categoryid);
	}

	@Override
	public void changeCategoryName(int categoryid, String newCategoryName) {
		Category category = categoryDao.readCategory(categoryid);
		category.setCategoryName(newCategoryName);
		categoryDao.updateCategory(category);
	}

	@Override
	public void addExistingCategorytoTask(Category category, int taskid) {
		String taskids = category.getTaskids();
		String taskidString = Integer.toString(taskid);
		category.setTaskids(taskids+", "+taskidString); //updating taskids of category
		categoryDao.updateCategory(category);
	}
}
