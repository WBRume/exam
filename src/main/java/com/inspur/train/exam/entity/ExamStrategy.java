package com.inspur.train.exam.entity;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "examStrategySeq",sequenceName = "EXAMSTRATEGY_SEQ",allocationSize = 1,initialValue = 1)
public class ExamStrategy {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator = "examStrategySeq")
	private int id;
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="exam_id")
	private Exam exam;
	private String name;
	private int choicePerScore;
	private int blankPerScore;
	private int judgePerScore;
	@Column(name = "`comment`")
	private String comment;
	
	public ExamStrategy(){}
	
	public ExamStrategy(Exam exam, String name, int choicePerScore, int blankPerScore, int judgePerScore,
			String comment) {
		this.exam = exam;
		this.name = name;
		this.choicePerScore = choicePerScore;
		this.blankPerScore = blankPerScore;
		this.judgePerScore = judgePerScore;
		this.comment = comment;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "ExamStrategy [id=" + id + ", exam=" + exam + ", name=" + name + ", choicePerScore=" + choicePerScore
				+ ", blankPerScore=" + blankPerScore + ", judgePerScore=" + judgePerScore + ", comment=" + comment
				+ "]";
	}
	
}
