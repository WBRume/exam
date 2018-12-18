package com.inspur.train.exam.entity;

import java.util.Date;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "studentExamScoreSeq",sequenceName = "STUDENTEXAMSCORE_SEQ",allocationSize = 1,initialValue = 1)
public class StudentExamScore {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator = "studentExamScoreSeq")
	private int id;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="student_id")
	private User user;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="exam_id")
	private Exam exam;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="exam_strategy_id")	
	private ExamStrategy examStrategy;
	private int score;
	private String examPhase;
	private Date examStartTime;
	private Date examEndTime;
	private int timeUsed; //考试用时（单位：秒）
	
	public StudentExamScore() {}
	
	public StudentExamScore(User user, Exam exam, ExamStrategy examStrategy, int score, String examPhase) {
		this.user = user;
		this.exam = exam;
		this.examStrategy = examStrategy;
		this.score = score;
		this.examPhase = examPhase;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Exam getExam() {
		return exam;
	}
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	public ExamStrategy getExamStrategy() {
		return examStrategy;
	}

	public void setExamStrategy(ExamStrategy examStrategy) {
		this.examStrategy = examStrategy;
	}

	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public String getExamPhase() {
		return examPhase;
	}

	public Date getExamStartTime() {
		return examStartTime;
	}

	public void setExamStartTime(Date examStartTime) {
		this.examStartTime = examStartTime;
	}

	public Date getExamEndTime() {
		return examEndTime;
	}

	public void setExamEndTime(Date examEndTime) {
		this.examEndTime = examEndTime;
	}

	public void setExamPhase(String examPhase) {
		this.examPhase = examPhase;
	}

	public int getTimeUsed() {
		return timeUsed;
	}

	public void setTimeUsed(int timeUsed) {
		this.timeUsed = timeUsed;
	}
	
}
