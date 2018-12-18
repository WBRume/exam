package com.inspur.train.exam.dao;

import java.io.File;
import java.util.List;

import com.inspur.train.exam.entity.Group;
import com.inspur.train.exam.entity.User;

public interface UserDao {
	User findByRegNoAndPassword(String regNo, String password);
	List<User> findStudentForSearch(String regNo, String name, Boolean gender, String gradeName);

	User findById(int id);
	List<User> findAll();
	List<User> findByGrade(Group g);
	
	void save(User s);
	void update(User s);
	void delete(User s);
	
	int importFromTxt(File file);

}