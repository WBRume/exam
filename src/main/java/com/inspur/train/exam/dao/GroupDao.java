package com.inspur.train.exam.dao;

import java.util.List;

import com.inspur.train.exam.entity.Group;

public interface GroupDao {

	Group findById(int id);
	List<Group> findAll();
	List<Group> findByName(String name);
	void save(Group g);
	void update(Group g);
	void delete(Group g);

}