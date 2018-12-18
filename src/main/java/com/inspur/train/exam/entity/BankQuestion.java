package com.inspur.train.exam.entity;

import java.util.Date;

import javax.persistence.*;

@MappedSuperclass

public abstract class BankQuestion {

    /*@GeneratedValue(strategy=GenerationType.SEQUENCE)*/
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "bankQuestionSeq")
	@SequenceGenerator(name = "bankQuestionSeq",sequenceName = "BANKQUESTION_SEQ",allocationSize = 1,initialValue = 1)
	private int id;
	private String name;//题目标题，可省略
	private String content; //题干，对于填空题、判断题就是题目本身，
	//如果题目包含图片，这是图片的存放路径；如果题目包含多张图片，则用“[pic1,pic2,...]”的语法
	private String picLocation;
	private String description;//对题目的额外要求（例如，该题目必须用英语回答，等）
	private String answer;
	private Date createDate;
	private String contributor;//出题人，或题目贡献者，如果有的话
	private String knowledgePoint;//知识点
	private String memo;
	private int composeFlag;//组卷标志：（目前只用于题目是否参与随机组卷，1表示用于随机组卷）

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPicLocation() {
		return picLocation;
	}
	public void setPicLocation(String picLocation) {
		this.picLocation = picLocation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getContributor() {
		return contributor;
	}
	public void setContributor(String contributor) {
		this.contributor = contributor;
	}
	public String getKnowledgePoint() {
		return knowledgePoint;
	}
	public void setKnowledgePoint(String knowledgePoint) {
		this.knowledgePoint = knowledgePoint;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getComposeFlag() {
		return composeFlag;
	}
	public void setComposeFlag(int composeFlag) {
		this.composeFlag = composeFlag;
	}
	
}
