package com.inspur.train.exam.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.inspur.train.exam.dao.GroupDao;
import com.inspur.train.exam.dao.UserDao;
import com.inspur.train.exam.entity.Group;
import com.inspur.train.exam.entity.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentImport1 {

	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = null;
		BufferedReader br= null;
		int cnt = 0;
		try{
			//TODO: change for web?
			context =new ClassPathXmlApplicationContext("applicationContext.xml");
			
			br = new BufferedReader(new FileReader(new File("c:\\学生import.txt")));
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
				System.out.println("fieldNameLine=["+fieldNameLine+"]");
				if(fieldNames.length>1) break;
			}while(true);
			
			System.out.println("fieldNames="+Arrays.toString(fieldNames));
			
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
						System.out.println("非法的字段名,请使用"
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
					System.out.println("regNo="+regNo
							+ ",name="+name 
							+ ",gender="+gender 
							+ ",grade="+grade
							+ ",password="+password);
					boolean genderBool = gender.equals("女")?false:true;//默认为"男"，只要不是"女"
					
					
					//TODO: change for web?
					//搜索班级，班级名如果存在，就用搜到的第一个重名的作为该学生班级，否则新建一个
					GroupDao gradeDao = (GroupDao) context.getBean("gradeDao");
					List<Group> gradeList=gradeDao.findByName(grade);
					Group theGrade=null;
					if(gradeList!=null && gradeList.size()>0){
						theGrade = gradeList.get(0);//用的是重名的班级列表中的第一个。
					}else{
						theGrade = new Group(grade);
						gradeDao.save(theGrade);
					}
					UserDao studentDao = (UserDao)context.getBean("userDao");
					User student = new User(name,regNo,genderBool,password,theGrade);
					studentDao.save(student);
					cnt++;
				}else{
					System.out.println("该行“"+line+"” 的学生信息字段数少于说明中的字段数："
										+Arrays.toString(fieldNames));
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			context.close();
		}
		System.out.println("共读入了"+cnt+"个学生信息！");
	}
	
}


