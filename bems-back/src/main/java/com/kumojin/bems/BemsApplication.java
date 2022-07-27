package com.kumojin.bems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class BemsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BemsApplication.class, args);
    }

}
