package main.java.com.townscript.todo.service.tag;

import main.java.com.townscript.todo.dao.tag.TagDao;
import main.java.com.townscript.todo.dao.tag.TagDaoImpl;
import main.java.com.townscript.todo.model.Tag;

public class TagServiceImpl implements TagService{

	TagDao tagDao = new TagDaoImpl();
	
	@Override
	public int addTag(Tag tag) {
		return tagDao.addTag(tag);
	}

	@Override
	public void deleteTag(int tagid) {
		tagDao.removeTag(tagid);
	}

	@Override
	public Tag readTag(int tagid) {
		return tagDao.readTag(tagid);
	}

	@Override
	public void addDefaultTags() {
		Tag defaulttag1 = new Tag();
		Tag defaulttag2 = new Tag();
		Tag defaulttag3 = new Tag();
		Tag defaulttag4 = new Tag();
		defaulttag1.setTagName("office");
		defaulttag2.setTagName("home");
		defaulttag3.setTagName("movie");
		defaulttag4.setTagName("project");
		tagDao.addTag(defaulttag1);
		tagDao.addTag(defaulttag2);
		tagDao.addTag(defaulttag3);
		tagDao.addTag(defaulttag4);
	}

	@Override
	public void changeTagName(int tagid, String newTagName) {
		tagDao.updateTagName(tagid, newTagName);
		
	}

}
