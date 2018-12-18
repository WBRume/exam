package com.inspur.train.exam.actions;

import javax.annotation.Resource;

import com.inspur.train.exam.dao.ExamStrategyDao;
import com.inspur.train.exam.entity.ExamStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Component("updateStrategyName")
@Scope("prototype")
public class UpdateStrategyNameAction extends ActionSupport{
	private static final long serialVersionUID = 3877971759903674637L;
	private final static Logger logger = LoggerFactory.getLogger(UpdateStrategyNameAction.class);
	@Resource
	private ExamStrategyDao examStrategyDao;
	private String strategyName;
	private int strategyId;
	private ExamStrategy updatedStrategy;
	
	public String getStrategyName() {
		return strategyName;
	}
	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}
	public int getStrategyId() {
		return strategyId;
	}
	public void setStrategyId(int strategyId) {
		this.strategyId = strategyId;
	}
	public ExamStrategy getUpdatedStrategy() {
		return updatedStrategy;
	}
	public void setUpdatedStrategy(ExamStrategy updatedStrategy) {
		this.updatedStrategy = updatedStrategy;
	}
	@Override
	public String execute(){
		logger.info("更新考试策略名称");
		updatedStrategy = examStrategyDao.findById(strategyId);
		updatedStrategy.setName(strategyName);
		examStrategyDao.update(updatedStrategy);
		return SUCCESS;
	}
}
