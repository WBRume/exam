package com.inspur.train.exam.dao;

import java.util.List;

import com.inspur.train.exam.entity.Exam;
import com.inspur.train.exam.entity.User;
import com.inspur.train.exam.entity.StudentExamScore;

public interface StudentExamScoreDao {
	
	List<StudentExamScore> findByStudent(int id);
	List<StudentExamScore> findByExamPhase(String examPhase);
	List<StudentExamScore> findByClassIdAndExamNameAndExamPhase(String classId, String examName, String examPhase);
	List<StudentExamScore> findByStudentAndExamPhase(User s, String examPhase);
	List<StudentExamScore> findByStudentAndExam(User s, Exam e);
	List<StudentExamScore> findByStudentAndExamAndExamPhase(User s,Exam e,String examPhase);
	List<User> getAbsentStudentsForExamName(String classId, String examName);


	//分页查询
    List<StudentExamScore> findByStudentWithPage(int id,int pageIndex, int pageSize);
	StudentExamScore findById(int id);
	List<StudentExamScore> findAll();
	void save(StudentExamScore ses);
	void update(StudentExamScore ses);
	void delete(StudentExamScore ses);
}