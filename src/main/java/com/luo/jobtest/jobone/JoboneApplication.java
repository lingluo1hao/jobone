package com.luo.jobtest.jobone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JoboneApplication {

    public static void main(String[] args) {
        SpringApplication.run(JoboneApplication.class, args);
    }
}
