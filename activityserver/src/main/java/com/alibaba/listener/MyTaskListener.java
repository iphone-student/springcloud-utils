package com.alibaba.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
/**
* @Author: zb
* @Description: 监听器
* @Date: 2022/12/6 15:06
*/
public class MyTaskListener implements TaskListener {
    /**
    * @Author: zb
    * @Description: 指定负责人
    * @Date: 2022/12/6 15:06
    */
    @Override
    public void notify(DelegateTask delegateTask){
        //判断当前的任务是 创建申请 并且是 create 事件
        if("创建申请".equals(delegateTask.getName()) && "create".equals(delegateTask
                .getEventName())){
            delegateTask.setAssignee("张三");
        }
    }
}
