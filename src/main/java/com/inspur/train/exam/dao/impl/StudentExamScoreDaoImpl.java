package com.inspur.train.exam.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.inspur.train.exam.common.ExamPhase;
import com.inspur.train.exam.dao.UserDao;
import com.inspur.train.exam.entity.Exam;
import com.inspur.train.exam.entity.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.inspur.train.exam.dao.GroupDao;
import com.inspur.train.exam.dao.StudentExamScoreDao;
import com.inspur.train.exam.entity.StudentExamScore;

@Component("studentExamScoreDao")
@Transactional

public class StudentExamScoreDaoImpl implements StudentExamScoreDao {
	private final static Logger logger = LoggerFactory.getLogger(StudentExamScoreDaoImpl.class);
	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private UserDao studentDao;
	@Resource
	private GroupDao gradeDao;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	//TODO
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<StudentExamScore> findByStudentWithPage(int id, int pageIndex, int pageSize) {
		Query q=sessionFactory.getCurrentSession().createQuery("from StudentExamScore where student_id=?0 and examphase=?1 order by examendtime desc");
		int firstResult = pageIndex*pageSize;
		q.setFirstResult(firstResult);
		q.setMaxResults(pageSize);
		q.setParameter("0", id);
		q.setString("1","最终得分");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<StudentExamScore> findByStudent(int id) {
		Query q=sessionFactory.getCurrentSession().createQuery("from StudentExamScore where student_id=?0 and examphase=?1 order by examendtime desc");
		q.setParameter("0", id);
		q.setString("1","最终得分");
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<StudentExamScore> findByExamPhase(String examPhase) {
		Query q=sessionFactory.getCurrentSession().createQuery("from StudentExamScore where examPhase=?0 order by score desc");
		q.setParameter("0", examPhase);
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<StudentExamScore> findByClassIdAndExamNameAndExamPhase(String classId, String examName,
			String examPhase) {
		StringBuilder sb = new StringBuilder();
		sb.append("from StudentExamScore ses where ");
		
		classId = classId.trim();
		int nPos = 0;
		String[] ids = null;
		if(!classId.equals("")){
			ids = classId.split(",");
			sb.append("(");
			for(int i=0; i<ids.length; i++){
				sb.append("ses.user.group.id=?"+i);
				if(i<ids.length-1) sb.append(" or ");
				nPos++;
			}
			sb.append(") and ");
		}
		sb.append("ses.exam.name like ?" + nPos + " and ses.examPhase=?" + (nPos+1) + "  order by score desc");
		Query q=sessionFactory.getCurrentSession().createQuery(sb.toString());
		for(int i=0; i<nPos; i++) q.setInteger(String.valueOf(i), Integer.parseInt(ids[i].trim()));
		q.setParameter(String.valueOf(nPos), examName+"%");
		q.setParameter(String.valueOf(nPos+1), examPhase);
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<StudentExamScore> findByStudentAndExamPhase(User s, String examPhase) {
		Query q=sessionFactory.getCurrentSession().createQuery("from StudentExamScore where user=?0 and examPhase=?1 order by examEndTime desc, exam.createDate desc");
		q.setParameter("0", s);
		q.setParameter("1", examPhase);
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<StudentExamScore> findByStudentAndExam(User s, Exam e) {
		Query q=sessionFactory.getCurrentSession().createQuery("from StudentExamScore where user=?0 and exam=?1");
		q.setParameter("0", s);
		q.setParameter("1", e);
		return q.list();
	}

	@Override
	public List<StudentExamScore> findByStudentAndExamAndExamPhase(User s, Exam e, String examPhase) {
		Query q=sessionFactory.getCurrentSession().createQuery("from StudentExamScore where user=?0 and exam=?1 and examphase=?2");
		q.setParameter("0", s);
		q.setParameter("1", e);
		q.setString("2","试卷初始化");
		return q.list();
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public StudentExamScore findById(int id) {
		StudentExamScore ses = sessionFactory.getCurrentSession().get(StudentExamScore.class, id);
		return ses;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<StudentExamScore> findAll() {
		Query q=sessionFactory.getCurrentSession().createQuery("from StudentExamScore");
		return q.list();
	}
	
	@Override
	public void save(StudentExamScore ses){
		sessionFactory.getCurrentSession().save(ses);
	}
	
	@Override
	public void update(StudentExamScore ses){
		sessionFactory.getCurrentSession().update(ses);
	}
	
	@Override
	public void delete(StudentExamScore ses){
		sessionFactory.getCurrentSession().delete(ses);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<User> getAbsentStudentsForExamName(String classId, String examName) {
		logger.debug("classId="+classId);
		logger.debug("examName="+examName);
		Query q=sessionFactory.getCurrentSession().createQuery("from StudentExamScore ses where ses.exam.name like ?0 "
				+ " and ses.student.grade.id=?1 "
				+ " and ses.examPhase=?2");
		int gradeId = Integer.parseInt(classId.trim());
		q.setString("0", examName+"%");
		q.setInteger("1",gradeId);
		q.setString("2", ExamPhase.FINAL_SCORED.getChineseName());
		List<StudentExamScore> sesList = q.list();
		List<User> stuList = studentDao.findByGrade(gradeDao.findById(gradeId));
		List<User> absentStuList = new ArrayList<>();
		for(User stu:stuList){
			boolean stuAbsent = true;
			for(StudentExamScore ses:sesList){
				if(ses.getUser().getId()==stu.getId()){
					stuAbsent = false;
					break;
				}
			}
			if(stuAbsent) absentStuList.add(stu);
		}
		return absentStuList;
	}

}
