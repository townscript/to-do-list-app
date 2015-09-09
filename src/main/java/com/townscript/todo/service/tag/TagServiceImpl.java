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
	public void deleteTag(int tagId) {
		tagDao.removeTag(tagId);
	}

	@Override
	public Tag readTag(int tagId) {
		return tagDao.loadTag(tagId);
	}

	@Override
	public void changeTagName(int tagId, String newTagName) {
		Tag tag = tagDao.loadTag(tagId);
		tag.setTagName(newTagName);
		tagDao.updateTag(tag);
		
	}

	@Override
	public void addExistingTagtoTask(Tag tag, int taskId) {
		String taskIds = tag.getTaskids();
		String taskIdString = Integer.toString(taskId);
		tag.setTaskids(taskIds+", "+taskIdString); //updating taskids of tag
		tagDao.updateTag(tag);
		
	}

}
