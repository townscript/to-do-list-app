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
	public void addDefaultCategories() {
		Category defaultCategory1 = new Category();
		Category defaultCategory2 = new Category();
		defaultCategory1.setCategoryName("Work");
		defaultCategory2.setCategoryName("Personal");
		categoryDao.addCategory(defaultCategory1);
		categoryDao.addCategory(defaultCategory2);
	}

	@Override
	public void changeCategoryName(int categoryid, String newCategoryName) {
		categoryDao.updateCategoryName(categoryid, newCategoryName);
	}
}
