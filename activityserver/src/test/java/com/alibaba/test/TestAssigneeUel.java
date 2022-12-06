package com.alibaba.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
* @Author: zb
* @Description: 用uel表达式完成
* @Date: 2022/12/6 14:01
*/
public class TestAssigneeUel {

    /**
     * @Author: zb
     * @Description: 测试流程部署
     * @Date: 2022/12/5 18:27
     */
    @Test
    public void testDeployment(){
        //1、创建processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //使用service进行流程的部署，定义一个流程的名字，把bpmn和png部署到数据中
        Deployment deploy = repositoryService.createDeployment()
                .name("出差申请流程uel")
                .addClasspathResource("process/mydemo.bpmn20.png")
                .addClasspathResource("process/mydemo.bpmn20.xml")
                .deploy();
        //4、输出部署信息
        System.out.println("流程部署id = "+deploy.getId());
        System.out.println("流程部署名字 = "+deploy.getName());
    }
    /**
    * @Author: zb
    * @Description: 使用uel进行分配
    * @Date: 2022/12/6 14:10
    */
    @Test
    public voidstartAssigneeUel() {
        //获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取RunTimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //设定assignee的值，用来替换uel表达式
        Map<String, Object> assigneeMap = new HashMap<>();
        assigneeMap.put("assignee0", "张三");
        assigneeMap.put("assignee1", "李经理");
        assigneeMap.put("assignee2", "王总经理");
        assigneeMap.put("assignee3", "赵财务");
        //启动流程实例
        runtimeService.startProcessInstanceByKey("", assigneeMap);
    }

}
