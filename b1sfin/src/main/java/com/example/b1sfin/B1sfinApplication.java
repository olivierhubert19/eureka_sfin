package com.example.b1sfin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class B1sfinApplication {

    public static void main(String[] args) {
        SpringApplication.run(B1sfinApplication.class, args);
    }

}
