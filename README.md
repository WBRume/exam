# java-exam
Java实现的包含题库编辑、抽题组卷、试题分析、在线考试等模块的Web教育系统。

已经实现的主要功能有：
- 在线考试（包含限定时间设置），支持选择题、填空题、判断题三种题型，自动判分
- 选择题、填空题、判断题及用户信息的文本文件数据的Web导入
- 用户注册、登录、修改密码、基本信息管理
- 按照一定给分策略进行抽题和组卷，支持“固定组卷” 和“随机组卷”两种方式
- 按照内容、知识点、答案等搜索题库，题目及分数的统计
-适配手机端，手机APP嵌入自动登录d

目前项目基于以下平台（框架、库）:
- JDK 1.8
- Tomcat 8.0 (with WebSocket)
- Hibernate 5.1 
- Struts 2.5
- Spring 4.3
- JFreeChart 1.0.19
- Maven
- Materialize v0.97.6 (CSS)
- Font Awesome 5.1.1

项目主要在IDEA  下开发。


