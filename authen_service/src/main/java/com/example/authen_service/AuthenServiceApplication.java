package com.example.authen_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthenServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenServiceApplication.class, args);
    }

}
