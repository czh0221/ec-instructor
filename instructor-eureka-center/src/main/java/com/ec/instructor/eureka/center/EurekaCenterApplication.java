package com.ec.instructor.eureka.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Colin.Z.Chen
 * @description
 * @time 2017/12/4.
 */
@EnableEurekaServer  //启动一个服务注册中心提供给其他应用进行对话
@SpringBootApplication
public class EurekaCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaCenterApplication.class, args);
    }
}
