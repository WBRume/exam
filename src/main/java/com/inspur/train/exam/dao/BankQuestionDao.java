package com.inspur.train.exam.dao;

import java.io.File;
import java.util.List;

import com.inspur.train.exam.entity.BankBlankFillingQuestion;
import com.inspur.train.exam.entity.BankChoiceQuestion;
import com.inspur.train.exam.entity.BankJudgeQuestion;
import com.inspur.train.exam.entity.BankQuestion;

public interface BankQuestionDao {
	
	List<BankQuestion> findAll();
	
	int countAllChoice();
	int countAllBlankFilling();
	int countAllJudge();
	
	List<BankChoiceQuestion> findAllChoice();
	List<BankBlankFillingQuestion> findAllBlankFilling();
	List<BankJudgeQuestion> findAllJudge();
	
	//根据组卷标志（目前用于随机抽题）查询
	List<BankChoiceQuestion> findChoiceWithComposeFlag(int composeFlag);
	List<BankBlankFillingQuestion> findBlankFillingWithComposeFlag(int composeFlag);
	List<BankJudgeQuestion> findJudgeWithComposeFlag(int composeFlag);
	
	//分页查询
	List<BankChoiceQuestion> findAllChoiceWithPage(int pageIndex, int pageSize);
	List<BankBlankFillingQuestion> findAllBlankFillingWithPage(int pageIndex, int pageSize);
	List<BankJudgeQuestion> findAllJudgeWithPage(int pageIndex, int pageSize);
	
	List<BankChoiceQuestion> findChoiceForSearch(String content,String choice,String answer,String knowledge);
	List<BankBlankFillingQuestion> findBlankForSearch(String content,String answer,String knowledge);
	List<BankJudgeQuestion> findJudgeForSearch(String content,String answer,String knowledge);
	
	BankChoiceQuestion findChoiceById(int id);
	BankBlankFillingQuestion findBlankFillingById(int id);
	BankJudgeQuestion findJudgeById(int id);
	
	void save(BankQuestion bq);
	void update(BankQuestion bq);
	void delete(BankQuestion bq);
	
	int importChoiceFromTxt(File txtFile);
	int importBlankFromTxt(File txtFile);
	int importJudgeFromTxt(File txtFile);
}