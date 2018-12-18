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

import com.inspur.train.exam.dao.GroupDao;
import com.inspur.train.exam.entity.Group;

@Component("groupDao")
@Transactional

public class GradeDaoImpl implements GroupDao {
	private final static Logger logger = LoggerFactory.getLogger(GradeDaoImpl.class);
	@Resource
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Group findById(int id) {
		Group g = sessionFactory.getCurrentSession().get(Group.class, id);
		return g;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Group> findAll() {
		Query q=sessionFactory.getCurrentSession().createQuery("from Group");
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Group> findByName(String name){
		logger.debug("name="+name);
		Query q=sessionFactory.getCurrentSession().createQuery("from Group where name=?0");
		q.setString("0", name);
		return q.list();
	}
	
	@Override
	public void save(Group g){
		sessionFactory.getCurrentSession().save(g);
	}
	
	@Override
	public void update(Group g){
		sessionFactory.getCurrentSession().update(g);
	}
	
	@Override
	public void delete(Group g){
		sessionFactory.getCurrentSession().delete(g);
	}
}
