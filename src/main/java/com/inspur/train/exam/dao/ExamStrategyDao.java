package com.inspur.train.exam.dao;

import java.util.List;

import com.inspur.train.exam.entity.Exam;
import com.inspur.train.exam.entity.ExamStrategy;

public interface ExamStrategyDao {

	List<ExamStrategy> findAll();
	List<ExamStrategy> findByExam(Exam exam);
	ExamStrategy findById(int id);
	void save(ExamStrategy e);
	void update(ExamStrategy e);
	void delete(ExamStrategy e);

}