package main.java.com.townscript.todo.service.tag;

import main.java.com.townscript.todo.model.Tag;

public interface TagService {
	public int addTag(Tag tag);
	public void addExistingTagtoTask(Tag tag,int taskid);
	public void deleteTag(int tagid);
	public Tag readTag(int tagid);
	public void changeTagName(int tagid, String newTagName);
}
