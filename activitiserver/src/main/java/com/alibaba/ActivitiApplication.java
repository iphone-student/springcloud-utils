package com.alibaba;

import com.alibaba.config.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@SpringBootApplication()
public class ActivitiApplication implements CommandLineRunner {

    @Autowired
    private SecurityUtil securityUtil;

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        securityUtil.logInAs("system");
    }

    @Bean
    public DataSource dataSource() {
        DataSource dataSource = new DriverManagerDataSource("jdbc:mysql:///activity?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true", "root", "1234");
        return dataSource;
    }
}
