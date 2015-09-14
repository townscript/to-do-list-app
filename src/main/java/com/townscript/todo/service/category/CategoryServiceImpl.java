package com.townscript.todo.service.category;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.townscript.todo.dao.category.CategoryDao;
import com.townscript.todo.model.Category;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
	private static final Logger logger = Logger.getLogger(CategoryServiceImpl.class);
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
		logger.info("deleted category");
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
		logger.info("changed category name");
	}

	@Override
	public void addExistingCategorytoTask(Category category, int taskId) {
		String taskIds = category.getTaskids();
		String taskIdString = Integer.toString(taskId);
		category.setTaskids(taskIds+", "+taskIdString); //updating taskids of category
		categoryDao.updateCategory(category);
		logger.debug("updated taskids for category - "+category.getCategoryName());
	}
}
