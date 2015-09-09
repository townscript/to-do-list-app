package com.townscript.todo.service.tag;

import com.townscript.todo.model.Tag;

public interface TagService {
	int addTag(Tag tag);
	void addExistingTagtoTask(Tag tag,int taskId);
	void deleteTag(int tagId);
	Tag readTag(int tagId);
	void changeTagName(int tagId, String newTagName);
}
