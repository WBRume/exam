package com.inspur.train.exam.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.inspur.train.exam.dao.BankQuestionDao;
import com.inspur.train.exam.entity.BankBlankFillingQuestion;
import com.inspur.train.exam.entity.BankJudgeQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import com.inspur.train.exam.common.Constants;
import com.inspur.train.exam.dao.ExamDao;
import com.inspur.train.exam.dao.ExamQuestionDao;
import com.inspur.train.exam.entity.BankChoiceQuestion;
import com.inspur.train.exam.entity.Exam;
import com.inspur.train.exam.utils.PropertyUtils;

@Component("examCreate")
@Scope("prototype")
public class ExamCreateAction extends ActionSupport {
	private static final long serialVersionUID = 5887916965376081691L;
	private final static Logger logger = LoggerFactory.getLogger(ExamCreateAction.class);
	private String examName;
	private String examDetail;
	
	private int[] choiceSelect;//对话框中的checkbox
	private int[] blankSelect;//对话框中的checkbox
	private int[] judgeSelect;//对话框中的checkbox
	
	@Resource
	private ExamDao examDao;
	@Resource
	private BankQuestionDao bankQuestionDao;
	@Resource
	private ExamQuestionDao examQuestionDao;
	
	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getExamDetail() {
		return examDetail;
	}

	public void setExamDetail(String examDetail) {
		this.examDetail = examDetail;
	}

	public int[] getChoiceSelect() {
		return choiceSelect;
	}

	public void setChoiceSelect(int[] choiceSelect) {
		this.choiceSelect = choiceSelect;
	}

	public int[] getBlankSelect() {
		return blankSelect;
	}

	public void setBlankSelect(int[] blankSelect) {
		this.blankSelect = blankSelect;
	}

	public int[] getJudgeSelect() {
		return judgeSelect;
	}

	public void setJudgeSelect(int[] judgeSelect) {
		this.judgeSelect = judgeSelect;
	}


	public String executeForSelectQuestions() throws Exception{
		ActionContext ctx=ActionContext.getContext();
		Map<String,Object> session = ctx.getSession();
		if(examName!=null){
			session.put("EXAM_CREATE_NAME", examName);
		}
		if(examDetail!=null){
			session.put("EXAM_CREATE_DETAIL", examDetail);
		}
		if(choiceSelect!=null){
			@SuppressWarnings("unchecked")
			List<BankChoiceQuestion> choiceListSelected = (List<BankChoiceQuestion>)session.get("EXAM_CREATE_CHOICELIST");
			if(choiceListSelected==null) choiceListSelected = new ArrayList<>();
			for(int id:choiceSelect)
				choiceListSelected.add(bankQuestionDao.findChoiceById(id));
			session.put("EXAM_CREATE_CHOICELIST",choiceListSelected);
		}
		if(blankSelect!=null){
			@SuppressWarnings("unchecked")
			List<BankBlankFillingQuestion> blankListSelected = (List<BankBlankFillingQuestion>)session.get("EXAM_CREATE_BLANKLIST");
			if(blankListSelected==null) blankListSelected = new ArrayList<>();
			for(int id:blankSelect)
				blankListSelected.add(bankQuestionDao.findBlankFillingById(id));
			session.put("EXAM_CREATE_BLANKLIST",blankListSelected);
		}
		if(judgeSelect!=null){
			@SuppressWarnings("unchecked")
			List<BankJudgeQuestion> judgeListSelected = (List<BankJudgeQuestion>)session.get("EXAM_CREATE_JUDGELIST");
			if(judgeListSelected==null) judgeListSelected = new ArrayList<>();
			for(int id:judgeSelect)
				judgeListSelected.add(bankQuestionDao.findJudgeById(id));
			session.put("EXAM_CREATE_JUDGELIST",judgeListSelected);
		}
		return SUCCESS;
	}



	@SuppressWarnings("unchecked")
	public String executeForCreateExam() throws Exception{
		if (examName.equals(null) || examName.equals("")) {
			this.clearErrorsAndMessages();
			this.addActionError("请输入考试名称");
			return  INPUT;
		}else {

			ActionContext ctx = ActionContext.getContext();
			Map<String, Object> session = ctx.getSession();
			logger.debug("examName=" + examName);
			logger.debug("examDetail=" + examDetail);

			Exam exam = new Exam(examName, examDetail, 0); //固定抽题
			exam.setCreateDate(new Date());
			exam.setScheduledTime(Integer.parseInt(PropertyUtils.getProperty(Constants.DEFAULT_EXAM_SCHEDULED_TIME)));
			List<BankChoiceQuestion> choiceListSelected = (List<BankChoiceQuestion>) session.get("EXAM_CREATE_CHOICELIST");
			List<BankBlankFillingQuestion> blankListSelected = (List<BankBlankFillingQuestion>) session.get("EXAM_CREATE_BLANKLIST");
			List<BankJudgeQuestion> judgeListSelected = (List<BankJudgeQuestion>) session.get("EXAM_CREATE_JUDGELIST");
			examDao.examCreateWithQuestions(exam, choiceListSelected, blankListSelected, judgeListSelected);

		}
		return SUCCESS;

	}


}
