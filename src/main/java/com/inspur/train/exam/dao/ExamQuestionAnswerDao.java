package com.inspur.train.exam.dao;

import java.util.List;

import com.inspur.train.exam.entity.Exam;
import com.inspur.train.exam.entity.ExamQuestion;
import com.inspur.train.exam.entity.ExamQuestionAnswer;
import com.inspur.train.exam.entity.User;

public interface ExamQuestionAnswerDao {
	
	ExamQuestionAnswer findById(int id);
	List<ExamQuestionAnswer> findAll();
//	List<ExamQuestionAnswer> findForExam(Exam exam);
	
	//针对对每种题库中的题型搜索
//	List<ExamQuestionAnswer> findForBankChoiceQuestion(BankChoiceQuestion q);
//	List<ExamQuestionAnswer> findForBankBlankFillingQuestion(BankBlankFillingQuestion q);
//	List<ExamQuestionAnswer> findForBankJudgeQuestion(BankJudgeQuestion q);
	
	List<ExamQuestionAnswer> findByExamQuestion(ExamQuestion eq);
	ExamQuestionAnswer findByStudentAndExamQuestion(User user, ExamQuestion examQuestion);
	
	//针对某个学生的对某次考试的搜索，相当于该学生的一份答卷
	List<ExamQuestionAnswer> findByStudentAndExam(Exam exam,User user);
	
	void save(ExamQuestionAnswer eqa);
	void update(ExamQuestionAnswer eqa);
	void delete(ExamQuestionAnswer eqa);

}