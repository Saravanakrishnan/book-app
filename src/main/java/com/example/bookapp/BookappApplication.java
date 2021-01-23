package com.example.bookapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication//(scanBasePackages = "com.example.bookapp.*")
//@ComponentScan("com.example.bookapp")
//@EnableJpaRepositories("com.example.bookapp.repo")
public class BookappApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(BookappApplication.class, args);
    }
    
    // Setting default timezone to IST
//    @PostConstruct
//    public void init() {
//        TimeZone.setDefault(TimeZone.getTimeZone("IST"));
//        System.out.println("Spring boot application running in IST timezone :" + new Date()); // It will print IST timezone
//    }
}
