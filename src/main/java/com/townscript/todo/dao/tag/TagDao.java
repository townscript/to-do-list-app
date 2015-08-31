package main.java.com.townscript.todo.dao.tag;

import main.java.com.townscript.todo.model.Tag;

public interface TagDao {
public int addTag(Tag tag);
public void removeTag(int tagid);
public Tag readTag(int tagid);
public void addDefaultTags();
}
