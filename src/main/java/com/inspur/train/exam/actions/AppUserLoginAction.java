package com.inspur.train.exam.actions;

import com.inspur.train.exam.dao.UserDao;
import com.inspur.train.exam.entity.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component("appUserLogin")
@Scope("prototype")
public class AppUserLoginAction extends ActionSupport {
    private static final long serialVersionUID = 5090548832375146478L;
    private final static Logger logger = LoggerFactory.getLogger(UserLoginAction.class);

    //TODO 需确定传过来的USERID 参数名
    private String registerNo;
    private String username;
    @Resource
    private UserDao userDao;

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String execute() throws Exception {
        logger.info("APP用户登陆");


        //TODO 需确认传过来的参数名  填充到registerNo
        List<User> userList = userDao.findByRegNo(registerNo);
        if(!userList.isEmpty() && userList.size() == 1){
            User stu = userList.get(0);
            username=stu.getName();
            ActionContext ctx=ActionContext.getContext();
            ctx.getSession().put("USER_INFO", stu);
        }else{
            this.addActionError("学号或密码错误，请重新输入！");
        }
        return SUCCESS;
    }
}
