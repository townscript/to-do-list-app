package com.townscript.todo.service.tag;

import com.townscript.todo.dao.tag.TagDao;
import com.townscript.todo.model.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TagServiceImpl implements TagService{

	@Autowired
	TagDao tagDao;
	
	public TagDao getTagDao() {
		return tagDao;
	}

	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}

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
