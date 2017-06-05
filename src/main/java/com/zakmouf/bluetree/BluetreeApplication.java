package com.zakmouf.bluetree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class BluetreeApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BluetreeApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BluetreeApplication.class);
    }

}
