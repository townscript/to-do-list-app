package main.java.com.townscript.todo.service.tag;

import main.java.com.townscript.todo.model.Tag;

public interface TagService {
	public int addTag(Tag tag);
	public void deleteTag(int tagid);
	public Tag readTag(int tagid);
	public void addDefaultTags();
	public void changeTagName(int tagid, String newTagName);
}
