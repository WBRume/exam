package com.inspur.train.exam.dao;

import java.util.List;

import com.inspur.train.exam.entity.BankBlankFillingQuestion;
import com.inspur.train.exam.entity.BankChoiceQuestion;
import com.inspur.train.exam.entity.BankJudgeQuestion;
import com.inspur.train.exam.entity.Exam;
import com.inspur.train.exam.entity.ExamQuestion;

public interface ExamQuestionDao {
	ExamQuestion findById(int id);
	List<ExamQuestion> findAll();
	List<ExamQuestion> findByExam(Exam e);
	
	List<ExamQuestion> findByBankChoiceQuestion(BankChoiceQuestion q);
	List<ExamQuestion> findByBankBlankFillingQuestion(BankBlankFillingQuestion q);
	List<ExamQuestion> findByBankJudgeQuestion(BankJudgeQuestion q);
	
	void save(ExamQuestion eq);
	void update(ExamQuestion eq);
	void delete(ExamQuestion eq);

}