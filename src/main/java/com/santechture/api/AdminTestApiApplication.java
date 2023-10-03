package com.santechture.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AdminTestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminTestApiApplication.class, args);
        System.out.println("Password: "+new BCryptPasswordEncoder().encode("p@ssw0rd"));
    }


}
