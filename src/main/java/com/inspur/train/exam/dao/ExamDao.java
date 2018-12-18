package com.inspur.train.exam.dao;

import java.util.List;

import com.inspur.train.exam.entity.BankBlankFillingQuestion;
import com.inspur.train.exam.entity.BankChoiceQuestion;
import com.inspur.train.exam.entity.BankJudgeQuestion;
import com.inspur.train.exam.entity.Exam;

public interface ExamDao {

	List<Exam> findAllFixedExam();//查找固定组卷的考试
	List<String> findAllDistinctExamName();//查找所有考试名（随机试卷算1个）
	List<Exam> findByStudentNameAndExamNameAlike(String studentName, String examName);

	Exam findById(int id);
	void save(Exam e);
	void update(Exam e);
	void delete(Exam e);
	void composeExamRandom(Exam exam, int choiceNum,int blankFillingNum, int judgeNum);
	void composeExamToRandom(Exam exam,List<BankChoiceQuestion> listChoice,List<BankBlankFillingQuestion> listBlankFilling,List<BankJudgeQuestion> listJudge,int[] questionNum);
	void examCreateWithQuestions(Exam exam, List<BankChoiceQuestion> choiceList,
                                 List<BankBlankFillingQuestion> blankList, List<BankJudgeQuestion> judgeList);

}