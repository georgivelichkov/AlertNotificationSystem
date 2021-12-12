package com.sap.configapi.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.configapi.ConfigApiApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.io.IOException;


@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = { ConfigApiApplication.class },
        loader = AnnotationConfigWebContextLoader.class)
@Transactional
//@WebAppConfiguration
@SpringBootTest(
        properties = {"spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.h2.console.enabled=false",
        "spring.jpa.show-sql=true",
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.jpa.properties.hibernate.format_sql=true",
        "spring.liquibase.change-log = classpath:/db/liquibase-outputChangeLog.xml",
        "spring.liquibase.enabled=true",
        "spring.liquibase.url=jdbc:h2:mem:testdb",
        "spring.liquibase.user=sa",
        "spring.liquibase.password="})
@EnableConfigurationProperties
public abstract class AbstractTest {

    private WebApplicationContext webApplicationContext;
    protected MockMvc mvc;

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }


}
