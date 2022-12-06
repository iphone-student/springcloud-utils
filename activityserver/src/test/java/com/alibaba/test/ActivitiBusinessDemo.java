package com.alibaba.test;

import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

public class ActivitiBusinessDemo {
    /**
    * @Author: zb
    * @Description: 添加业务key，到activiti表
    * @Date: 2022/12/6 10:44
    */
    @Test
    public void addBusinessKey(){
        //获取引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取RunTimeservice
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //启动流程的过程中,添加businesskey,第一个参数流程key第二个参数businesskey
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("", "");
        //输出
        System.out.println(instance.getBusinessKey());
    }
    /**
    * @Author: zb
    * @Description: 全部流程实例的挂起和激活
    * @Date: 2022/12/6 13:12
    */
    @Test
    public void  suspendAllProcessInstance(){
        //获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取Repositoryservice
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //查询流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("").singleResult();
        //获取当前流程定义的实例是否都是挂起状态
        boolean suspended = processDefinition.isSuspended();
        //获取流程定义的id
        String definitionId = processDefinition.getId();
        //如果是挂起状态改为激活状态
        if (suspended){
            repositoryService.activateProcessDefinitionById(definitionId,true,null);
        }else {
            //如果是激活状态，改为挂起状态
        repositoryService.suspendProcessDefinitionById(definitionId, true, null);
        }
    }
    /**
    * @Author: zb
    * @Description: 单个流程实例挂起和激活
    * @Date: 2022/12/6 13:28
    */
    @Test
    public void suspendSingleProcessInstance() {
        //获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //Runtimeservice
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //通过Runtimeservice获取流程实例对象
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId("").singleResult();
        //得到当前流程实例的暂停状态
        boolean suspended = processInstance.isSuspended();
        //获取流程实例id
        String instanceId = processInstance.getId();
        //判断是否已经暂停，如果已经暂停，就执行激活操作
        if (suspended) {
            runtimeService.activateProcessInstanceById(instanceId);
        }else{
        //如果是激活状态，就执行暂停操作
        runtimeService.suspendProcessInstanceById(instanceId);
        }
    }
    /**
    * @Author: zb
    * @Description: 完成个人任务
    * @Date: 2022/12/6 13:45
    */
    @Test
    public void completTask() {
        //获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取TaskService
        TaskService taskService = processEngine.getTaskService();
        //使用taskservice获取任务
        Task task = taskService.createTaskQuery().processInstanceId("").taskAssignee("").singleResult();
        //根据任务id完成任务
        taskService.complete(task.getId());
    }
}
