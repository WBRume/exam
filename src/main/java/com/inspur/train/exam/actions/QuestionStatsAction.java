package com.inspur.train.exam.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Component("questionStats")
@Scope("prototype")
public class QuestionStatsAction extends ActionSupport {
	private static final long serialVersionUID = -6315314962483922015L;
	private final static Logger logger = LoggerFactory.getLogger(QuestionStatsAction.class);
	
	@Override
	public String execute() throws Exception {
		ActionContext ctx =ActionContext.getContext();
		logger.info("问题统计");
		if(ctx.getSession().containsKey("USER_INFO")){
			return SUCCESS;
		}else{
			this.addActionError("您还没有登录，请登录后再点击进入");
			return ERROR;
		}
	}

}
