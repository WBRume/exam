package com.inspur.train.exam.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import com.inspur.train.exam.dao.ExamQuestionAnswerDao;
import com.inspur.train.exam.entity.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.inspur.train.exam.entity.Exam;
import com.inspur.train.exam.entity.ExamQuestion;
import com.inspur.train.exam.entity.ExamQuestionAnswer;

@Component("examQuestionAnswerDao")
@Transactional
public class ExamQuestionAnswerDaoImpl implements ExamQuestionAnswerDao {
	private final static Logger logger = LoggerFactory.getLogger(ExamQuestionAnswerDaoImpl.class);
	@Resource
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public ExamQuestionAnswer findById(int id) {
		ExamQuestionAnswer eqa = sessionFactory.getCurrentSession().get(ExamQuestionAnswer.class, id);
		return eqa;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<ExamQuestionAnswer> findAll(){
		Query q=sessionFactory.getCurrentSession().createQuery("from ExamQuestionAnswer");
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<ExamQuestionAnswer> findByExamQuestion(ExamQuestion eq){
		Query q=sessionFactory.getCurrentSession().createQuery("from ExamQuestionAnswer where examQuestion=?0");
		q.setParameter("0", eq);
		return q.list();
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public ExamQuestionAnswer findByStudentAndExamQuestion(User user, ExamQuestion examQuestion){
		logger.debug("user="+ user);
		Query q=sessionFactory.getCurrentSession().createQuery("from ExamQuestionAnswer where user=?0 and examQuestion=?1");
		q.setParameter("0", user);
		q.setParameter("1", examQuestion);
		return (ExamQuestionAnswer)q.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<ExamQuestionAnswer> findByStudentAndExam(Exam exam,User user){
		Query q=sessionFactory.getCurrentSession().createQuery("from ExamQuestionAnswer eqa where eqa.user=?0 and eqa.examQuestion.exam=?1");
		q.setParameter("0", user);
		q.setParameter("1", exam);
		return q.list();
	}
	
	@Override
	public void save(ExamQuestionAnswer eqa){
		sessionFactory.getCurrentSession().save(eqa);
	}
	
	@Override
	public void update(ExamQuestionAnswer eqa){
		sessionFactory.getCurrentSession().update(eqa);
	}
	
	@Override
	public void delete(ExamQuestionAnswer eqa){
		sessionFactory.getCurrentSession().delete(eqa);
	}

}
