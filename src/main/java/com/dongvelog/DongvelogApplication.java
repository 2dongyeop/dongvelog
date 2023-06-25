package com.dongvelog;

import com.dongvelog.global.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class DongvelogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DongvelogApplication.class, args);
    }

}
