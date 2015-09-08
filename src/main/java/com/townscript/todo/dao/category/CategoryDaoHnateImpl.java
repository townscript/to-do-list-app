package com.townscript.todo.dao.category;

import java.util.List;

import com.townscript.todo.model.Category;
import com.townscript.todo.model.Tag;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDaoHnateImpl extends HibernateDaoSupport implements CategoryDao{

	@Override
	public int addCategory(Category category) {
		getHibernateTemplate().save(category);
		return category.getId();
	}

	@Override
	public void removeCategory(int categoryid) {
		Category category = new Category();
		category.setId(categoryid);
		getHibernateTemplate().delete(category);
		
	}

	@Override
	public Category readCategory(int categoryid) {
		String queryString = "FROM "+ Category.class.getName() +" WHERE id = :categoryId";
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(queryString);

		query.setParameter("categoryId", categoryid);
		List<Category> categoriesList = query.list();
		return categoriesList.get(0);	
	}

	@Override
	public void updateCategory(Category category) {
		getHibernateTemplate().update(category);
	}

	@Override
	public Category getCategoryofTask(int taskid) {
		String queryString = "FROM "+ Category.class.getName() +" WHERE taskids like :taskid";
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(queryString);

		query.setParameter("taskid", "%"+taskid+"%");
		List<Category> categoriesList = query.list();
		return categoriesList.get(0);
	}

}
