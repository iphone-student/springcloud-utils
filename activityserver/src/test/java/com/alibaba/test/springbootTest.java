package com.alibaba.test;

import com.alibaba.config.SecurityUtil;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class springbootTest {
    @Autowired
    SecurityUtil securityUtil;
    @Autowired
    ProcessRuntime processRuntime;
    @Autowired
    TaskRuntime taskRuntime;

    /** springboot自动部署
    * @Author: zb
    * @Description: 查看流程定义内容
    * @Date: 2022/12/6 18:36
    */
    @Test
    public void findProcess(){
        securityUtil.logInAs("jack");
        //流程定义的分页对象
        processRuntime.processDefinitions(Pageable.of(0,10);
        log.info("可用的流程定义总数：",definitionPage.getTotalItems()));
        for (ProcessDefinition peroProcessDefinition:definitionPage.getContent()){
            log.info("流程定义内容：{}",peroProcessDefinition);
        }
    }
    /**
    * @Author: zb
    * @Description: 启动流程
    * @Date: 2022/12/6 18:44
    */
    @Test
    public void startProcess() {
        //设置登录用户
        securityUtil.logInAs("system");
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder.start().withProcessDefinitionKey("mydemo").build());
    }
    /**
    * @Author: zb
    * @Description: 执行任务
    * @Date: 2022/12/6 18:48
    */
    @Test
    public void doTask() {
        //设置登录用户
        securityUtil.logInAs("other");
        //查询任务
        Page<Task> taskPage = taskRuntime.tasks(Pageable.of(0,10));
        if (taskPage !=null && taskPage.getTotalItems() > 0){
            for (Task task : taskPage.getContent()) {
                //拾取任务
                taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
                log.info("任务内容，{}",task);
                //完成任务
                taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
            }
        }



    }
}
