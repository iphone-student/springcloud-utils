package com.alibaba.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.jupiter.api.Test;

public class TestCreate {
    /**
    * @Author: zb
    * @Description: 使用activiti提供的默认方式创建mysql表
    * @Date: 2022/12/5 11:51
    */
    @Test
    public void testCreateDbTable(){
        //需要使用activiti提供的工具类processEngines的使用方法getDefaultProcessEngine默认从resources下读取
        // 名字为activist.cfg.xml文件
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine);
    }
}
