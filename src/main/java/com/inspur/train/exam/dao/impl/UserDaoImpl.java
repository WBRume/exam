package com.inspur.train.exam.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import com.inspur.train.exam.entity.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.inspur.train.exam.dao.GroupDao;
import com.inspur.train.exam.dao.UserDao;
import com.inspur.train.exam.entity.Group;

@Component("userDao")
@Transactional

public class UserDaoImpl implements UserDao {
	private final static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private GroupDao gradeDao;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<User> findByRegNo(String regNo){
		Query q = sessionFactory.getCurrentSession().createQuery("from User where registerNo=?0");
		q.setString("0",regNo);
		return q.list();
	}
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public User findByRegNoAndPassword(String regNo, String password) {
		Query q=sessionFactory.getCurrentSession().createQuery("from User where registerNo=?0 and password=?1");
		q.setString("0", regNo);
		q.setString("1", password);
		return (User)q.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<User> findStudentForSearch(String regNo, String name, Boolean gender, String gradeName) {
		StringBuffer qStrSb =  new StringBuffer("from User where name like :n ");
		boolean regNoNotNull=false;
		if(regNo!=null && !regNo.equals("")){
			qStrSb.append(" and registerNo like :r ");
			regNoNotNull=true;
		}
		boolean genderNotNull=false;
		if(gender!=null){
			qStrSb.append(" and gender=:g ");
			genderNotNull=true;
		}
		boolean gradeNameNotNull=false;
		if(gradeName!=null && !gradeName.equals("")){
			qStrSb.append(" and grade.name like :g2 ");
			gradeNameNotNull=true;
		}
		Query query = sessionFactory.getCurrentSession().createQuery(qStrSb.toString());
		query.setString("n", "%"+name+"%");
		
		if(regNoNotNull){
			query.setString("r", regNo+"%");
		}
		if(genderNotNull){
			query.setBoolean("g", gender.booleanValue());
		}
		if(gradeNameNotNull){
			query.setString("g2", "%"+gradeName+"%");
		}
		return query.list();
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public User findById(int id) {
		User s = sessionFactory.getCurrentSession().get(User.class, id);
		return s;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<User> findAll() {
		Query q=sessionFactory.getCurrentSession().createQuery("from User");
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<User> findByGrade(Group g){
		Query q=sessionFactory.getCurrentSession().createQuery("from User where grade=?0");
		q.setParameter("0", g);
		return q.list();
	}
	
	@Override
	public void save(User s){
		sessionFactory.getCurrentSession().save(s);
	}
	
	@Override
	public void update(User s){
		sessionFactory.getCurrentSession().update(s);
	}
	
	@Override
	public void delete(User s){
		sessionFactory.getCurrentSession().delete(s);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public int importFromTxt(File file) {
		BufferedReader br= null;
		int cnt = 0;
		try{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			String fieldNameLine= null;
			String[] fieldNames = null;
			do{
				fieldNameLine=br.readLine();
				if( fieldNameLine==null || fieldNameLine.length()<=1  )  continue;//忽略空行
				char firstChar=0;
				if((int)(fieldNameLine.trim().charAt(0))==65279){//对有BOM头的第一行做处理
					firstChar = fieldNameLine.trim().charAt(1);
				}else{
					firstChar = fieldNameLine.trim().charAt(0);
				}
				if( firstChar=='#')  continue; //忽略注释行
				fieldNameLine=fieldNameLine.replace(" ", "");//将UTF-8的特殊空格替换掉，编码c2a0
				fieldNames = fieldNameLine.trim().split("[\\s\\p{Punct}]+");//任何一个或多个空白字符或标点符号作为分隔符
				logger.debug("fieldNameLine=["+fieldNameLine+"]");
				if(fieldNames.length>1) break;
			}while(true);
			
			logger.debug("fieldNames="+Arrays.toString(fieldNames));
			
			int regNoIdx=-1, nameIdx=-1, genderIdx=-1, gradeIdx=-1, passwordIdx=-1;
			for(int i=0; i<fieldNames.length; i++){
				switch(fieldNames[i].trim()){
					case "学号":
						regNoIdx=i;
						break;
					case "姓名":
						nameIdx=i;
						break;
					case "性别":
						genderIdx=i;
						break;
					case "班级":
						gradeIdx=i;
						break;
					case "密码":
						passwordIdx=i;
						break;
					default:
						logger.debug("非法的字段名,请使用"
										   +Arrays.toString(fieldNames)+"这"
										   +fieldNames.length+"个名称中的一个");
						break;
				}
			}
			String line = null;
			String regNo=null, gender=null, name=null, grade=null, password=null;
			while( (line=br.readLine())!=null ){
				line=line.replace(" ", "");//将UTF-8的特殊空格替换掉，编码c2a0
				if(line.trim().length()==0) continue;////忽略空行
				if(line.charAt(0)=='#') continue; //忽略注释行
				String[] fields = line.split("[\\s\\p{Punct}]+");
	
				if(fields.length >= fieldNames.length){
					if(regNoIdx!=-1) {
						regNo = fields[regNoIdx].trim();
						regNo=regNo.replace(" ", "");
					}
					if(genderIdx!=-1) {
						gender = fields[genderIdx].trim();
						gender=gender.replace(" ", "");
					}
					if(nameIdx!=-1) {
						name = fields[nameIdx].trim();
						name=name.replace(" ", "");
					}
					if(gradeIdx!=-1) {
						grade = fields[gradeIdx].trim();
						grade=grade.replace(" ", "");
					}
					if(passwordIdx!=-1) {
						password= fields[passwordIdx].trim();
						password=password.replace(" ", "");
					}
					logger.debug("regNo="+regNo
							+ ",name="+name 
							+ ",gender="+gender 
							+ ",grade="+grade
							+ ",password="+password);
					boolean genderBool = gender.equals("女")?false:true;//默认为"男"，只要不是"女"
					
					
					//TODO: change for web?
					//搜索班级，班级名如果存在，就用搜到的第一个重名的作为该学生班级，否则新建一个
					List<Group> gradeList=gradeDao.findByName(grade);
					Group theGrade=null;
					if(gradeList!=null && gradeList.size()>0){
						theGrade = gradeList.get(0);//用的是重名的班级列表中的第一个。
					}else{
						theGrade = new Group(grade);
						gradeDao.save(theGrade);
					}
					User student = new User(name,regNo,genderBool,password,theGrade);
					sessionFactory.getCurrentSession().save(student);
					cnt++;
				}else{
					logger.debug("该行“"+line+"” 的学生信息字段数少于说明中的字段数："
										+Arrays.toString(fieldNames));
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("共读入了"+cnt+"个学生信息！");
		return cnt;
	}

}
