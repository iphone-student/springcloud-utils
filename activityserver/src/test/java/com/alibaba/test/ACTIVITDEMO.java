package com.alibaba.test;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.List;
import java.util.zip.ZipInputStream;

@SpringBootTest
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
    /**
    * @Author: zb
    * @Description: 个人任务查询
    * @Date: 2022/12/5 18:41
    */
    @Test
    public void tastFindPersonalTaskList(){
        //1、获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取taskservice
        TaskService taskService = processEngine.getTaskService();
        //3、根据流程key和任务的负责人 查询任务
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey("")  //流程key
                .taskAssignee("")     //要查询的负责人
                .list();
        //4、输出
        for (Task task : taskList) {
            System.out.println("流程定例id： "+task.getProcessDefinitionId());
            System.out.println("任务id： "+task.getId());
            System.out.println("任务负责人："+task.getAssignee());
            System.out.println("任务名称："+task.getName());
        }

    }
    /**
    * @Author: zb
    * @Description: 完成个人任务
    * @Date: 2022/12/5 18:51
    */
    @Test
    public void completTask() {
        //获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取service
        TaskService taskService = processEngine.getTaskService();
        ////根据任务id完成任务
        //taskService.complete("");
        //完成下一个任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("")
                .taskAssignee("")
                .singleResult();//单个人的任务，如果有多个需要进行list去判断
        //完成任务
        taskService.complete(task.getId());
    }

    /**
    * @Author: zb
    * @Description: 使用zip包进行批量部署
    * @Date: 2022/12/6 8:32
    */
    @Test
    public void deployProcessByZip(){
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("mydemo.bpmn20.zip");
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        //获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取RepositoryServer
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //流程部署
        repositoryService.createDeployment().addZipInputStream(zipInputStream).deploy();
    }
    /**
    * @Author: zb
    * @Description: 查询流程定义
    * @Date: 2022/12/6 8:45
    */
    public void queryProcessDefinition(){
        //获取引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取Repositoryservice
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //获取ProcessDifinitionQuery对象
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        //查询当前所有的流程定义,返回流程定义信息的集合
        List<ProcessDefinition> definitionList = processDefinitionQuery.processDefinitionKey("").orderByProcessDefinitionKey().desc().list();
        //输出信息
        for (ProcessDefinition processDefinition : definitionList) {
            System.out.println("流程定义ID"+processDefinition.getId());
            System.out.println("流程定义名称"+processDefinition.getName());
            System.out.println("流程定义Key"+processDefinition.getKey());
            System.out.println("流程定义版本"+processDefinition.getVersion());
            System.out.println("流程部署id"+processDefinition.getDeploymentId());
        }
    }
    /**
    * @Author: zb
    * @Description: 删除流程部署信息
     * 如果当前流程没有完成，进行级联删除
    * @Date: 2022/12/6 9:19
    */
    @Test
    public void deleteProcessDefinition() {
        //获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //通过引擎来获取RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //通过部署id来删除流程部署信息
        String deploymentId = "1";
        repositoryService.deleteDeployment(deploymentId);
        //级联删除
        repositoryService.deleteDeployment(deploymentId,true);
    }
    /**
    * @Author: zb
    * @Description: 下载资源文件
     * （推荐）1、使用activiti提供的api，来下载资源文件
     * 2、自己写代码从数据下载文件，使用jdbc对blob类型，读取保存文件目录使用connons-io。jar
    * @Date: 2022/12/6 9:46
    */
    @Test
    public void getDeployment() throws IOException {
        //得到引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取api，repsoitoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //获取查询对象ProcessDefinitionQuery，查询流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("").singleResult();
        //通过流程定义信息，获取部署ID
        String deploymentId = processDefinition.getDeploymentId();
        //通过 RepositoryService，传递部署id参数，读取资源信息（png和bpmn）
        //从流程定义表中，，获取图片的目录和名字
        String pngName = processDefinition.getDiagramResourceName();
        InputStream pngInput = repositoryService.getResourceAsStream(deploymentId,pngName);
        String bpmnName = processDefinition.getResourceName();
        InputStream bpmnInput = repositoryService.getResourceAsStream(deploymentId, bpmnName);
        //构造outputStream流
        File pngFile = new File("");
        File bpmnFile = new File("");
        FileOutputStream pngOutputStream = new FileOutputStream(pngFile);
        FileOutputStream bpmnOutputStream = new FileOutputStream(bpmnFile);
        //输入流，输出流的转换
        IOUtils.copy(pngInput,pngOutputStream);
        IOUtils.copy(bpmnInput,bpmnOutputStream);
        //关闭流
        pngOutputStream.close();
        bpmnOutputStream.close();
        pngInput.close();
        bpmnInput.close();
    }
    /**
    * @Author: zb
    * @Description: 查看历史信息
    * @Date: 2022/12/6 10:18
    */
    public void findHistoryInfo(){
        //获取引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取historyService
        HistoryService historyService = processEngine.getHistoryService();
        //查询actinst表
        HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();
        //增加排序操作
        instanceQuery.orderByHistoricActivityInstanceStartTime().asc();
        //查询所有内容
        List<HistoricActivityInstance> activityInstanceList = instanceQuery.list();
        //输出
        for (HistoricActivityInstance hi : activityInstanceList) {
            System.out.println(hi.getActivityId());
            System.out.println(hi.getActivityName());
            System.out.println(hi.getProcessDefinitionId());
            System.out.println(hi.getProcessInstanceId());
        }

    }

}
