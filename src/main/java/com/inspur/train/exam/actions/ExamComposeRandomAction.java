package com.inspur.train.exam.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.inspur.train.exam.common.Constants;
import com.inspur.train.exam.common.ExamPhase;
import com.inspur.train.exam.dao.ExamDao;
import com.inspur.train.exam.dao.ExamQuestionDao;
import com.inspur.train.exam.dao.ExamStrategyDao;
import com.inspur.train.exam.dao.UserDao;
import com.inspur.train.exam.entity.Exam;
import com.inspur.train.exam.entity.ExamStrategy;
import com.inspur.train.exam.entity.User;
import com.inspur.train.exam.entity.StudentExamScore;
import com.inspur.train.exam.utils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import com.inspur.train.exam.dao.StudentExamScoreDao;

@Component("examComposeRandom")
@Scope("prototype")
public class ExamComposeRandomAction extends ActionSupport {

	private static final long serialVersionUID = -9026822432724575083L;
	private final static Logger logger = LoggerFactory.getLogger(ExamComposeRandomAction.class);
	@Resource
	private UserDao studentDao;
	@Resource
	private StudentExamScoreDao studentExamScoreDao;
	@Resource
	private ExamDao examDao;
	@Resource
	private ExamQuestionDao examQuestionDao;
	@Resource
	private ExamStrategyDao examStrategyDao;
	
	private int choiceQuestionNum = 5; //选择题 个数
	private int blankQuestionNum = 5; // 填空题 个数
	private int judgeQuestionNum = 5; // 判断题 个数
	
	private int choicePerScore = 8; //选择题 每题分数
	private int blankPerScore = 6;  //填空题 每题分数
	private int judgePerScore = 6;  //判断题 每题分数
	
	private String examName = "随机试卷005";
	private String examStrategyName = "随机抽题给分策略005"; 
	
	private List<User> studentList = new ArrayList<>();
	
	public int getChoiceQuestionNum() {
		return choiceQuestionNum;
	}

	public void setChoiceQuestionNum(int choiceQuestionNum) {
		this.choiceQuestionNum = choiceQuestionNum;
	}

	public int getBlankQuestionNum() {
		return blankQuestionNum;
	}

	public void setBlankQuestionNum(int blankQuestionNum) {
		this.blankQuestionNum = blankQuestionNum;
	}

	public int getJudgeQuestionNum() {
		return judgeQuestionNum;
	}

	public void setJudgeQuestionNum(int judgeQuestionNum) {
		this.judgeQuestionNum = judgeQuestionNum;
	}

	public int getChoicePerScore() {
		return choicePerScore;
	}

	public void setChoicePerScore(int choicePerScore) {
		this.choicePerScore = choicePerScore;
	}

	public int getBlankPerScore() {
		return blankPerScore;
	}

	public void setBlankPerScore(int blankPerScore) {
		this.blankPerScore = blankPerScore;
	}

	public int getJudgePerScore() {
		return judgePerScore;
	}

	public void setJudgePerScore(int judgePerScore) {
		this.judgePerScore = judgePerScore;
	}
	
	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getExamStrategyName() {
		return examStrategyName;
	}

	public void setExamStrategyName(String examStrategyName) {
		this.examStrategyName = examStrategyName;
	}

	public List<User> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<User> studentList) {
		this.studentList = studentList;
	}

	@Override
	public String execute() throws Exception {
		ActionContext ctx =ActionContext.getContext();
		if(ctx.getSession().containsKey("USER_INFO")){
			if(ctx.getValueStack().findValue("selectStudentFor")!=null){  //从学生选择action chain过来
				int[] studentIds = (int[])ctx.getSession().get("EXAM_STUDENT_IDS");
				if(studentIds!=null){
					studentList = new ArrayList<>();
					for(int sid:studentIds){
						User stu = studentDao.findById(sid);
						studentList.add(stu);
					}
				}
			}else{
				ctx.getSession().remove("EXAM_STUDENT_IDS");
			}
			return SUCCESS;
		}else{
			this.addActionError("您还没有登录，请登录后再点击进入");
			return ERROR;
		}
	}
	
	
	public String executeForCreateStudentExam() throws Exception{
		//让画面仍然显示student列表
		ActionContext ctx =ActionContext.getContext();
		logger.info("为选择的学生生成试卷");
		int[] studentIds = (int[])ctx.getSession().get("EXAM_STUDENT_IDS");
		if(studentIds!=null){
			for(int sid:studentIds){
				User theStudent = studentDao.findById(sid);
				studentList.add(theStudent);
			}
		}
		
		int totalScore = choiceQuestionNum*choicePerScore + blankQuestionNum*blankPerScore + judgeQuestionNum*judgePerScore;
		if(totalScore != 100){
			this.addActionError("总分不为100分，请修改策略！");
		}else{
			if(studentIds!=null){
				for(int sid:studentIds){
					User theStudent = studentDao.findById(sid);
					
					Exam theExam = new Exam(examName+"->"+theStudent.getName(), examName+"->"+theStudent.getName(),1);
					theExam.setCreateDate(new Date());
					theExam.setScheduledTime(Integer.parseInt(PropertyUtils.getProperty(Constants.DEFAULT_EXAM_SCHEDULED_TIME)));
					examDao.save(theExam);
					
					examDao.composeExamRandom(theExam, choiceQuestionNum, blankQuestionNum, judgeQuestionNum);
					
					ExamStrategy theStrategy = new ExamStrategy(theExam,examStrategyName+"->"+theStudent.getName(),choicePerScore,blankPerScore,judgePerScore,examStrategyName+"->"+theStudent.getName());
					examStrategyDao.save(theStrategy);
					
					StudentExamScore ses=new StudentExamScore(theStudent,theExam,theStrategy,0, ExamPhase.PAPER_INITIALIZED.getChineseName());
					studentExamScoreDao.save(ses);
				}
				this.clearMessages();
				this.addActionMessage("已经为这"+studentIds.length+"个学生生成了试卷, "
									+  ", 请登录后进入试卷列表查看。");
			}
		}
		return SUCCESS;
	}
	
	
}
