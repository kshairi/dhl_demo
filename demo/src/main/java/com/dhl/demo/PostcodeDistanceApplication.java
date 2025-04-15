package com.dhl.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.dhl.demo" })
@EnableJpaRepositories
@EnableAspectJAutoProxy
@EnableEurekaClient
public class PostcodeDistanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostcodeDistanceApplication.class, args);
    }

}
