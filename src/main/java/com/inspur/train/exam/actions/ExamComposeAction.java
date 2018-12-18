package com.inspur.train.exam.actions;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.inspur.train.exam.common.Constants;
import com.inspur.train.exam.common.ExamPhase;
import com.inspur.train.exam.common.QuestionType;
import com.inspur.train.exam.dao.BankQuestionDao;
import com.inspur.train.exam.dao.ExamStrategyDao;
import com.inspur.train.exam.utils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import com.inspur.train.exam.dao.ExamDao;
import com.inspur.train.exam.dao.ExamQuestionDao;
import com.inspur.train.exam.dao.UserDao;
import com.inspur.train.exam.dao.StudentExamScoreDao;
import com.inspur.train.exam.entity.BankBlankFillingQuestion;
import com.inspur.train.exam.entity.BankChoiceQuestion;
import com.inspur.train.exam.entity.BankJudgeQuestion;
import com.inspur.train.exam.entity.Exam;
import com.inspur.train.exam.entity.ExamQuestion;
import com.inspur.train.exam.entity.ExamStrategy;
import com.inspur.train.exam.entity.User;
import com.inspur.train.exam.entity.StudentExamScore;
@Component("examCompose")
@Scope("prototype")
public class ExamComposeAction extends ActionSupport {
	
	private static final long serialVersionUID = 625313130792094673L;
	private final static Logger logger = LoggerFactory.getLogger(ExamComposeAction.class);
	
	@Resource
	private ExamDao examDao;
	@Resource
	private ExamQuestionDao examQuestionDao;
	@Resource 
	private BankQuestionDao bankQuestionDao;
	@Resource
	private ExamStrategyDao examStrategyDao;
	@Resource
	private UserDao userDao;
	@Resource
	private StudentExamScoreDao studentExamScoreDao;



	private List<Exam> examList;
	
	private int examSelect;
	
	private List<ExamStrategy> strategyList;
	private List<ExamQuestion> examQuestionList;


	private Map<String,Integer> eqCntMap;
	private int strategyId;//用于删除某个策略
	private int strategySelect;
	
	private List<User> studentList;

    private int choiceQuestionNum = 0; //选择题 个数
    private int blankQuestionNum = 0; // 填空题 个数
    private int judgeQuestionNum = 0; // 判断题 个数

    public List<ExamQuestion> getExamQuestionList() {
        return examQuestionList;
    }

    public void setExamQuestionList(List<ExamQuestion> examQuestionList) {
        this.examQuestionList = examQuestionList;
    }

    public List<Exam> getExamList() {
		return examList;
	}
	public void setExamList(List<Exam> examList) {
		this.examList = examList;
	}
	public int getExamSelect() {
		return examSelect;
	}
	public void setExamSelect(int examSelect) {
		this.examSelect = examSelect;
	}
	public List<ExamStrategy> getStrategyList() {
		return strategyList;
	}
	public void setStrategyList(List<ExamStrategy> strategyList) {
		this.strategyList = strategyList;
	}
	public Map<String, Integer> getEqCntMap() {
		return eqCntMap;
	}
	public void setEqCntMap(Map<String, Integer> eqCntMap) {
		this.eqCntMap = eqCntMap;
	}
	public int getStrategyId() {
		return strategyId;
	}
	public void setStrategyId(int strategyId) {
		this.strategyId = strategyId;
	}
	public int getStrategySelect() {
		return strategySelect;
	}
	public void setStrategySelect(int strategySelect) {
		this.strategySelect = strategySelect;
	}
	public List<User> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<User> studentList) {
		this.studentList = studentList;
	}

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

    @Override
	public String execute() throws Exception {
		ActionContext ctx =ActionContext.getContext();
		if(ctx.getSession().containsKey("USER_INFO")){
			getAllExamList();
			if(examSelect>0){ //从学生选择action chain过来，已经选过了examSelect
				strategyList = examStrategyDao.findByExam(examDao.findById(examSelect));
				int[] studentIds = (int[])ctx.getSession().get("EXAM_STUDENT_IDS");
				if(studentIds!=null){
					studentList=new ArrayList<>();
					for(int sid:studentIds){
						User stu = userDao.findById(sid);
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
	
	public String executeForSearchStrategy() throws Exception {
		strategyList = examStrategyDao.findByExam(examDao.findById(examSelect));
		return SUCCESS;
	}
	
	public String executeForCreateStrategy() throws Exception {
		ExamStrategy es = new ExamStrategy(examDao.findById(examSelect),"新建分数分配策略",0,0,0,null);
		examStrategyDao.save(es);
		getExamQuestionCnt();
		return SUCCESS;
	}
	public String executeForDeleteStrategy() throws Exception {
		ExamStrategy es = examStrategyDao.findById(strategyId);
		examStrategyDao.delete(es);
		getExamQuestionCnt();
		return SUCCESS;
	}
	
	public String executeForCreateStudentExam() throws Exception{
		//让画面仍然显示exam,strategy,student三个列表
		getAllExamList();
		Exam theExam = examDao.findById(examSelect);
		strategyList = examStrategyDao.findByExam(theExam);
		ExamStrategy theStrategy = examStrategyDao.findById(strategySelect);
		int count = 0;
		ActionContext ctx =ActionContext.getContext();
		int[] studentIds = (int[])ctx.getSession().get("EXAM_STUDENT_IDS");

		if(studentIds!=null){
			studentList=new ArrayList<>();
			StringBuilder builder = new StringBuilder();
			for(int sid:studentIds){
				User theStudent = userDao.findById(sid);
				studentList.add(theStudent);
                List<StudentExamScore> studentExamScoreExitList =  studentExamScoreDao.findByStudentAndExamAndExamPhase(theStudent,theExam,"试卷初始化");
                if(studentExamScoreExitList.size() > 0){
                    count ++;
                    builder.append(theStudent.getName() + " ");
                    continue;
                }
				StudentExamScore ses=new StudentExamScore(theStudent,theExam,theStrategy,0, ExamPhase.PAPER_INITIALIZED.getChineseName());
				studentExamScoreDao.save(ses);
			}
			this.clearMessages();
			StringBuilder message = new StringBuilder();
			message.append("已经为"+(studentIds.length - count)+"个学生生成了试卷, "
                    + "试卷名称："+theExam.getName());
			if(builder != null && builder.length() > 0){
			    message.append("-----");
			    message.append(builder.toString());
			    message.append("职工已经分配此考试,不再分配");
            }
			this.addActionMessage(message.toString());
		}
		return SUCCESS;
	}

    /**
     * 固定组卷下的随机分题
     *
     *
     * @return
     * @throws Exception
     */
	public String executeForCreateStudentExamRandom() throws Exception{
	    //TODO  功能实现

        //让画面仍然显示exam,strategy,student三个列表
        getAllExamList();
        int blankCount = 0; //填空题个数
        int choiceCount = 0; //选择题个数
        int judgeCount = 0; //判断题个数

        Exam theExam = examDao.findById(examSelect);
        strategyList = examStrategyDao.findByExam(theExam);
        ExamStrategy theStrategy = examStrategyDao.findById(strategySelect);
        examQuestionList = examQuestionDao.findByExam(theExam);

        int blankPerScore = theStrategy.getBlankPerScore();  //填空题分值 （最好不用客户选定的，不好实现）
        int choicePerScore = theStrategy.getChoicePerScore(); //选择题分值 （同上)d
        int judgePerScore = theStrategy.getJudgePerScore(); //判断题分值


		List<BankChoiceQuestion> listChoice = null; //选择题
		List<BankJudgeQuestion> listJudge = null; //判断题
		List<BankBlankFillingQuestion> listBlankFilling = null; //填空题

		for(ExamQuestion examQuestion : examQuestionList){
			int examType = examQuestion.getQuestionType();
			if(examType == 0){
				listChoice.add(examQuestion.getBankChoiceQuestion());
			}else if(examType == 1){
				listBlankFilling.add(examQuestion.getBankBlankFillingQuestion());
			}else if(examType == 2){
				listJudge.add(examQuestion.getBankJudgeQuestion());
			}
		}

		//TODO 获取各种题型个数 可以从页面传值
        int[] questionNum = {5,5,5};



		ActionContext ctx = ActionContext.getContext();
        int[] studentIds = (int[])ctx.getSession().get("EXAM_STUDENT_IDS");

        //TODO 需要页面传值
        int totalScore = choiceQuestionNum*choicePerScore + blankQuestionNum*blankPerScore + judgeQuestionNum*judgePerScore;
        if(totalScore != 100){
            this.addActionError("总分不为100分，请修改策略！");
        }else {
            if (studentIds != null) {
                for (int sid : studentIds) {
                    User theStudent = userDao.findById(sid);

                    Exam newExam = new Exam(theExam + "随机组卷->" + theStudent.getName(), theExam + "->" + theStudent.getName(), 1);
                    theExam.setCreateDate(new Date());
                    theExam.setScheduledTime(Integer.parseInt(PropertyUtils.getProperty(Constants.DEFAULT_EXAM_SCHEDULED_TIME)));
                    examDao.save(newExam);

                    examDao.composeExamToRandom(newExam, listChoice, listBlankFilling, listJudge, questionNum);

                    //确定每题多少分
                    ExamStrategy newStrategy = new ExamStrategy(newExam, "随机分题策略", 5, 5, 5, "随机分题策略");
                    examStrategyDao.save(newStrategy);

                    StudentExamScore studentExamScore = new StudentExamScore(theStudent, newExam, newStrategy, 0, ExamPhase.PAPER_INITIALIZED.getChineseName());
                    studentExamScoreDao.save(studentExamScore);
                }

                this.clearMessages();
                StringBuilder message = new StringBuilder();
                message.append("已经为"+ studentIds.length + "个学生生成了试卷, "
                        + "试卷名称："+theExam.getName());
                this.addActionMessage(message.toString());
            }
        }
        return SUCCESS;
    }



    /*
	 * 重新计算当前exam包含的各类题目（填空题为空白）数
	 */
	private void getExamQuestionCnt(){
		Exam exam=examDao.findById(examSelect); 
		List<ExamQuestion> eqList = examQuestionDao.findByExam(exam);
        int choiceCnt=0, blankCnt=0, judgeCnt=0;
        for(ExamQuestion eq:eqList){
        	if(eq.getQuestionType()== QuestionType.CHOICE.ordinal()){
        		choiceCnt++;
        	}else if(eq.getQuestionType()==QuestionType.BLANK_FILLING.ordinal()){
        		BankBlankFillingQuestion bq =bankQuestionDao.findBlankFillingById(eq.getBankBlankFillingQuestion().getId());
        		blankCnt+=countBlank(bq.getContent());
        	}else if(eq.getQuestionType()==QuestionType.JUDGE.ordinal()){
        		judgeCnt++;
        	}
        }
        eqCntMap = new HashMap<>();
        eqCntMap.put("CHOICE_CNT", choiceCnt);
        eqCntMap.put("BLANK_CNT", blankCnt);
        eqCntMap.put("JUDGE_CNT", judgeCnt);
	}
	
	private void getAllExamList(){
		ActionContext ctx =ActionContext.getContext();
		logger.info("获取所有固定抽题考试列表");
		examList = examDao.findAllFixedExam();
		
        for(Exam exam:examList){
	        List<ExamQuestion> eqList = examQuestionDao.findByExam(exam);
	        
	        ArrayList<BankChoiceQuestion> choiceList=new ArrayList<>();
	        ArrayList<BankBlankFillingQuestion> blankFillingList = new ArrayList<>();
	        ArrayList<BankJudgeQuestion> judgeList = new ArrayList<>();
	        for(ExamQuestion eq:eqList){
	        	if(eq.getQuestionType()==QuestionType.CHOICE.ordinal()){
	        		choiceList.add(bankQuestionDao.findChoiceById(eq.getBankChoiceQuestion().getId()));
	        	}else if(eq.getQuestionType()==QuestionType.BLANK_FILLING.ordinal()){
	        		blankFillingList.add(bankQuestionDao.findBlankFillingById(eq.getBankBlankFillingQuestion().getId()));
	        	}else if(eq.getQuestionType()==QuestionType.JUDGE.ordinal()){
	        		judgeList.add(bankQuestionDao.findJudgeById(eq.getBankJudgeQuestion().getId()));
	        	}
	        }
	        Map<String,Object> eqListMap = new HashMap<>();
	        eqListMap.put("CHOICE_LIST", choiceList);
	        eqListMap.put("BLANK_LIST", blankFillingList);
	        int blankCnt=0;
	        for(BankBlankFillingQuestion bq:blankFillingList){
	        	blankCnt+=countBlank(bq.getContent());
	        }
	        eqListMap.put("BLANK_CNT", blankCnt);
	        eqListMap.put("JUDGE_LIST", judgeList);
	        ctx.put("EXAM_QUESTION_"+exam.getId(), eqListMap);
        }
	}
	
	/*
	 * 统计填空题中空的个数
	 */
	private int countBlank(String content){
		Pattern p = Pattern.compile("[_]{2,}");//含有至少两个_符号表示空白
		Matcher m = p.matcher(content);
		int cnt=0;
		while (m.find()) {
		    cnt++;
		}
		return cnt;
	}

}
