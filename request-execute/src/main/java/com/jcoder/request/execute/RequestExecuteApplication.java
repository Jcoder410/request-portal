package com.jcoder.request.execute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Qiu
 */
@SpringBootApplication(scanBasePackages = {"com.jcoder.request"})
public class RequestExecuteApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequestExecuteApplication.class, args);
    }

}
