package com.inspur.train.exam.actions;

import com.inspur.train.exam.dao.ExamDao;
import com.inspur.train.exam.dao.StudentExamScoreDao;
import com.inspur.train.exam.entity.Exam;
import com.inspur.train.exam.entity.StudentExamScore;
import com.inspur.train.exam.entity.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

@Component("studentScoreList")
@Scope("prototype")
public class StudentScoreListAction extends ActionSupport {
    private static final long serialVersionUID = 1581291725411924695L;
    private static final Logger logger = LoggerFactory.getLogger(StudentScoreListAction.class);

    private List<StudentExamScore> studentExamScoreList;
    private List<Exam> examLists;

    //分页相关
    private int totalPage;
    private int pageIndex;
    private static final int PAGE_SIZE = 10;



    @Resource
    private StudentExamScoreDao studentExamScoreDao;
    @Resource
    private ExamDao examDao;


    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public List<StudentExamScore> getStudentExamScoreList() {
        return studentExamScoreList;
    }

    public void setStudentExamScoreList(List<StudentExamScore> studentExamScoreList) {
        this.studentExamScoreList = studentExamScoreList;
    }

    public List<Exam> getExamLists() {
        return examLists;
    }

    public void setExamLists(List<Exam> examLists) {
        this.examLists = examLists;
    }

    @Override
    public String execute() throws Exception{
        ActionContext ctx = ActionContext.getContext();
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");*/
        logger.info("职工当前考试成绩");
        if(ctx.getSession().containsKey("USER_INFO")){
            User user = (User) ctx.getSession().get("USER_INFO");
           /* studentExamScoreList = studentExamScoreDao.findByStudent(user.getId());*/
            int totalCnt =  studentExamScoreDao.findByStudent(user.getId()).size();
            totalPage = (totalCnt%PAGE_SIZE > 0)?(totalCnt/PAGE_SIZE + 1):(totalCnt/PAGE_SIZE);
            studentExamScoreList = studentExamScoreDao.findByStudentWithPage(user.getId(),pageIndex,PAGE_SIZE);
            if(studentExamScoreList.isEmpty())
            {
                this.addActionError("您还没有考试信息，请考试之后再查看积分");
                return ERROR;
            }

            return SUCCESS;
        }else{
            this.addActionError("您还没有登录，请登录后再点击进入");
            return ERROR;
        }
    }
}
