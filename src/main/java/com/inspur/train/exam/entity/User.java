package com.inspur.train.exam.entity;

import javax.persistence.*;

@Entity
@Table(name = "VIEW_MO_USERS_V3_EXAM")
@SequenceGenerator(name = "userSeq",sequenceName = "STUDENT_SEQ",allocationSize = 1,initialValue = 1)
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY,generator = "userSeq")
    private int id;
    private String registerNo;
    private String name;
    private boolean gender;
    private String password;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="grade_id")
    private Group group;
    private int role; //权限  role=0表示成员， role=1表示“教师或管理员”

    public User(){}

    public User(String name, String registerNo, boolean gender, String password) {
        this.name = name;
        this.gender = gender;
        this.registerNo = registerNo;
        this.password = password;
    }
    public User(String name, String registerNo, boolean gender, String password, Group group) {
        this.name = name;
        this.gender = gender;
        this.registerNo = registerNo;
        this.password = password;
        this.group = group;
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
    public String getRegisterNo() {
        return registerNo;
    }
    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public boolean isGender() {
        return gender;
    }
    public void setGender(boolean gender) {
        this.gender = gender;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Group getGrade() {
        return group;
    }
    public void setGrade(Group grade) {
        this.group = group;
    }
    public int getRole() {
        return role;
    }
    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", "
                + "name=" + name
                + "registerNo=" + registerNo
                + ", gender=" + gender
                + ", password=" + password
                + ", group=" + group
                + ", role="+ role + "]";
    }

}
