package com.inspur.train.exam.entity;

import java.util.Date;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "examQuestionAnswerSeq",sequenceName = "EXAMQUESTIONANSWER_SEQ",allocationSize = 1,initialValue = 1)
public class ExamQuestionAnswer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator = "examQuestionAnswerSeq")
	private int id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="exam_question_id")
	private ExamQuestion examQuestion;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="student_id",foreignKey = @ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT))
	private User user;
	
	private String answer;
	
	private Date answerDate;
	
	public ExamQuestionAnswer(){}

	public ExamQuestionAnswer(ExamQuestion examQuestion, User user, String answer) {
		this.examQuestion = examQuestion;
		this.user = user;
		this.answer = answer;
		this.answerDate = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ExamQuestion getExamQuestion() {
		return examQuestion;
	}

	public void setExamQuestion(ExamQuestion examQuestion) {
		this.examQuestion = examQuestion;
	}

	public User getStudent() {
		return user;
	}

	public void setStudent(User student) {
		this.user = student;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Date getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(Date answerDate) {
		this.answerDate = answerDate;
	}
}
