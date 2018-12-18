package com.inspur.train.exam.entity;

import javax.persistence.*;

@Entity
@Table(name = "grade")
@SequenceGenerator(name = "gradeSeq",sequenceName = "GRADE_SEQ",allocationSize = 1,initialValue = 1)
public class Group {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator = "gradeSeq")
	private int id;
	private String name;
	
	public Group(){}
	
	public Group(String name) {
		this.name = name;
	}

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

}
