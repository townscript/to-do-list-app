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
	public void changeTagName(int tagid, String newTagName) {
		Tag tag = tagDao.readTag(tagid);
		tag.setTagName(newTagName);
		tagDao.updateTag(tag);
		
	}

	@Override
	public void addExistingTagtoTask(Tag tag, int taskid) {
		String taskids = tag.getTaskids();
		String taskidString = Integer.toString(taskid);
		tag.setTaskids(taskids+", "+taskidString); //updating taskids of tag
		tagDao.updateTag(tag);
		
	}

}
