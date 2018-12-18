package com.inspur.train.exam.common;

public class Constants {
	//以下常量需与src/main/resources/mysetting.properties中的键名对应，否则将无法对该设置文件进行读写
	public static final String DEFAULT_EXAM_SCHEDULED_TIME = "exam.scheduledTime.default"; //默认（创建的）考试的时长 100分钟（即6000秒）
	public static final String EXAM_DETAIL_ALLOWED = "exam.detail.allowed"; //在学生试卷列表页面（studentExamList.jsp）中是否允许查看试卷详细
	public static final String EXAM_TIMEOUT_AUTOSUBMIT = "exam.timeout.autosubmit"; //考试超时，是否强制自动提交。
}
