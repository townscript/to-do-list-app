package com.townscript.todo.service.tag;

import com.townscript.todo.model.Tag;

public interface TagService {
	
	// As mentioned in another comment, no "Public" is required here
	public int addTag(Tag tag);
	public void addExistingTagtoTask(Tag tag,int taskid);
	public void deleteTag(int tagid);
	public Tag readTag(int tagid);
	public void changeTagName(int tagid, String newTagName);
}
