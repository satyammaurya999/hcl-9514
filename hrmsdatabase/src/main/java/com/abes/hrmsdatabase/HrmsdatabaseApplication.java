package com.abes.hrmsdatabase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication

public class HrmsdatabaseApplication {

    public static void main(String[] args) {

        SpringApplication.run(HrmsdatabaseApplication.class, args);
        System.out.println("Welcome to springboot development");
    }

}
