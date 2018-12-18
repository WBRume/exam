package com.inspur.train.exam.actions;

import javax.annotation.Resource;

import com.inspur.train.exam.dao.BankQuestionDao;
import com.inspur.train.exam.entity.BankJudgeQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Component("judgeDetail")
@Scope("prototype")
public class JudgeDetailAction extends ActionSupport {
	private static final long serialVersionUID = 1320414130188344435L;
	private final static Logger logger = LoggerFactory.getLogger(JudgeDetailAction.class);
	
	private BankJudgeQuestion question;
	@Resource 
	private BankQuestionDao bankQuestionDao;
	
	public BankJudgeQuestion getQuestion() {
		return question;
	}
	public void setQuestion(BankJudgeQuestion question) {
		this.question = question;
	}

	@Override
	public String execute() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		logger.info("根据id查询判断题详细");
		String qid = ctx.getParameters().get("qid").getValue();
		question = bankQuestionDao.findJudgeById(Integer.parseInt(qid.trim()));
		
		return SUCCESS;
	}
	
}
