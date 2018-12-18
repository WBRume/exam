package com.inspur.train.exam.actions;

import com.inspur.train.exam.entity.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("pdImport")
@Scope("prototype")
public class ImportAction extends ActionSupport {
    @Override
    public String execute() throws Exception {

        return SUCCESS;
    }
}
