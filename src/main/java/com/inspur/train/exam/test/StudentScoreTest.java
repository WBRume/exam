package com.inspur.train.exam.test;

import com.inspur.train.exam.dao.StudentExamScoreDao;
import com.inspur.train.exam.entity.StudentExamScore;
import com.inspur.train.exam.entity.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class StudentScoreTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentExamScoreDao studentExamScoreDao = (StudentExamScoreDao) context.getBean("studentExamScoreDao");
        User user = new User();
        user.setId(606);
        List<StudentExamScore> studentExamScoreList = studentExamScoreDao.findByStudent(user.getId());
        for(StudentExamScore s : studentExamScoreList){
            System.out.println(s.toString());
        }
    }
}
