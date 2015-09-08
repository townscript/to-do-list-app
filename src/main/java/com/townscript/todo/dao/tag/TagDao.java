package com.townscript.todo.dao.tag;

import java.util.List;

import com.townscript.todo.model.Tag;

public interface TagDao {
public int addTag(Tag tag);
public void removeTag(int tagid);
public Tag readTag(int tagid);
public void updateTag(Tag tag);
public List<Tag> getTagsofTask(int taskid);
}
