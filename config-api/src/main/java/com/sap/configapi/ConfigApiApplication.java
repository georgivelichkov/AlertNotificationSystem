package com.sap.configapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.sap.*"})
@EntityScan("com.sap.persistence.entities")
@EnableJpaRepositories("com.sap.persistence.repositories")
@EnableMongoRepositories("com.sap.persistence.repositories")
public class ConfigApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApiApplication.class, args);
    }

}
