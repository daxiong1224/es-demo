package com.hx.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AggrBucketMetricServiceTest {
    @Autowired
    private AggrBucketMetricService aggrBucketMetricService;

    @Test
    void aggrTopHits() throws IOException {
        aggrBucketMetricService.aggrTopHits();
    }
}