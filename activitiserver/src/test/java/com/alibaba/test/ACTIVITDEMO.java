package com.alibaba.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;



public class ACTIVITDEMO {
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
                .name("出差申请流程")
                .addClasspathResource("process/mydemo.bpmn20.png")
                .addClasspathResource("process/mydemo.bpmn20.xml")
                .deploy();
        //4、输出部署信息
        System.out.println("流程部署id = "+deploy.getId());
        System.out.println("流程部署名字 = "+deploy.getName());
    }
    /**
    * @Author: zb
    * @Description: 启动流程实例
    * @Date: 2022/12/5 18:29
    */
    @Test
    public void testStartPorcess(){
        //1、创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取RunTimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //3、根据流程定义的id启动流程
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("myEvection");
        //4、输出内容
        System.out.println("流程定义id： "+instance.getProcessDefinitionId());
        System.out.println("流程实例id： "+instance.getId());
        System.out.println("当前活动id："+instance.getActivityId());
    }



}
