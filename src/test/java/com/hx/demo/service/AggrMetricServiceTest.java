package com.hx.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@Slf4j
@SpringBootTest
class AggrMetricServiceTest {
    @Autowired
    private AggrMetricService aggregationService;

    @Test
    void metricQuery() throws IOException {
        aggregationService.metricQuery();
    }

    @Test
    void minQuery() throws IOException {
        aggregationService.minQuery();
    }

    @Test
    void sumQuery() throws IOException {
        aggregationService.sumQuery();
    }
}