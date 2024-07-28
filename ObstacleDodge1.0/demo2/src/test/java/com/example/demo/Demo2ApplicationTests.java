package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@AutoConfigureMockMvc
class Demo2ApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(Demo2ApplicationTests.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        logger.info("Context loads test executed");
        assertThat(true).isTrue(); // Simple assertion to ensure the test framework is working
    }

    @Test
    void testHomeController() throws Exception {
        logger.info("Testing HomeController");
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
        logger.info("HomeController test passed");
    }
}