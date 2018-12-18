package com.inspur.train.exam.actions;

import javax.annotation.Resource;

import com.inspur.train.exam.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import com.inspur.train.exam.dao.UserDao;

@Component("userChangePassWord")
@Scope("prototype")
public class UserChangePasswordAction extends ActionSupport {
	private static final long serialVersionUID = -3797081971252346673L;
	private final static Logger logger = LoggerFactory.getLogger(UserChangePasswordAction.class);
	
	private String oldPassword;
	private String newPassword;
	private String newPassword2;
	@Resource
	private UserDao studentDao;
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPassword2() {
		return newPassword2;
	}

	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}
	
	@Override
	public void validate() {
		ActionContext ctx=ActionContext. getContext();
		User stu = (User) ctx.getSession().get("USER_INFO");
		if(oldPassword!=null && stu.getPassword()!=null && oldPassword.equals(stu.getPassword())==false){
			this.addFieldError("oldPassword", "老密码跟数据库中的不一致，请重新输入!");
			return;
		}
		if(newPassword==null || newPassword.trim().equals("")){
			this.addFieldError("newPassword", "新密码不能为空！");
			return;
		}
		if(newPassword2==null || newPassword2.trim().equals("")){
			this.addFieldError("newPassword2", "新密码不能为空！");
			return;
		}
		if(false == newPassword.equals(newPassword2)){
			this.addFieldError("newPassword2", "两次输入的新密码必须相同！");
			return;
		}
	}

	@Override
	public String execute() throws Exception {
		//所有action如果直接访问，未登录则进入main.jsp
		ActionContext ctx=ActionContext. getContext();
		logger.info("用户更改密码");
		if(ctx.getSession().containsKey("USER_INFO")==false){
			this.addActionError("您还没有登录，请登录后重新进入");
			return ERROR;
		}else{
			User stu = (User) ctx.getSession().get("USER_INFO");
			stu.setPassword(newPassword);
			studentDao.update(stu);
			this.addActionMessage("修改密码成功，请下次登录请使用新密码！");
			return SUCCESS;
		}
	}
	
}
