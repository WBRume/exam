package com.inspur.train.exam.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.inspur.train.exam.dao.ExamStrategyDao;
import com.inspur.train.exam.entity.Exam;
import com.inspur.train.exam.entity.ExamStrategy;

@Component("examStrategyDao")
@Transactional
public class ExamStrategyDaoImpl implements ExamStrategyDao {
	private final static Logger logger = LoggerFactory.getLogger(ExamStrategyDaoImpl.class);
	@Resource
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<ExamStrategy> findAll(){
		Query q=sessionFactory.getCurrentSession().createQuery("from ExamStrategy");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<ExamStrategy> findByExam(Exam exam) {
		logger.debug("exam="+exam);
		Query q=sessionFactory.getCurrentSession().createQuery("from ExamStrategy where exam=?0");
		q.setParameter("0", exam);
		return q.list();
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public ExamStrategy findById(int id) {
		ExamStrategy es = sessionFactory.getCurrentSession().get(ExamStrategy.class, id);
		return es;
	}
	
	@Override
	public void save(ExamStrategy es){
		sessionFactory.getCurrentSession().save(es);
	}
	
	@Override
	public void update(ExamStrategy es){
		sessionFactory.getCurrentSession().update(es);
	}
	
	@Override
	public void delete(ExamStrategy es){
		sessionFactory.getCurrentSession().delete(es);
	}

	
}
