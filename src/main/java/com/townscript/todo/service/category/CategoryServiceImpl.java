package main.java.com.townscript.todo.service.category;

import main.java.com.townscript.todo.dao.category.CategoryDao;
import main.java.com.townscript.todo.dao.category.CategoryDaoImpl;
import main.java.com.townscript.todo.model.Category;

public class CategoryServiceImpl implements CategoryService{

	CategoryDao categoryDao = new CategoryDaoImpl();
	
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
