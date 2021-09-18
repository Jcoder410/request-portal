package com.jcoder.request.set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Qiu
 */
@ComponentScan(basePackages = {"com.jcoder.request.*"})
@SpringBootApplication
public class RequestSetApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequestSetApplication.class, args);
    }

}
