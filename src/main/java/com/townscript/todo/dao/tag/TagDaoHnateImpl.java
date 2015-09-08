package com.townscript.todo.dao.tag;

import java.util.List;

import com.townscript.todo.model.Tag;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class TagDaoHnateImpl extends HibernateDaoSupport implements TagDao{

	@Override
	public int addTag(Tag tag) {
		getHibernateTemplate().save(tag);
		return tag.getId();
	}

	@Override
	public void removeTag(int tagid) {
		Tag tag = new Tag();
		tag.setId(tagid);
		getHibernateTemplate().delete(tag);
	}

	@Override
	public Tag readTag(int tagid) {
		String queryString = "FROM "+ Tag.class.getName() +" WHERE id = :tagId";
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(queryString);

		query.setParameter("tagId", tagid);
		List<Tag> tagsList = query.list();
		return tagsList.get(0);	
	}

	@Override
	public void updateTag(Tag tag) {
		getHibernateTemplate().update(tag);
	}

	@Override
	public List<Tag> getTagsofTask(int taskid) {
		String taskidString = Integer.toString(taskid);
		String queryString = "FROM "+ Tag.class.getName() +" WHERE taskids like :taskid";
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(queryString);

		query.setParameter("taskid", "%"+taskidString+"%");
		List<Tag> tagsList = query.list();
		return tagsList;	
	}

}
