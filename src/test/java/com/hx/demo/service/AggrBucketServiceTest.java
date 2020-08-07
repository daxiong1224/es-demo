package com.hx.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AggrBucketServiceTest {
    @Autowired
    private AggrBucketService aggrBucketService;

    @Test
    void aggrBucketTerms() throws IOException {
        aggrBucketService.aggrBucketTerms();
    }

    @Test
    void aggrBucketRange() throws IOException {
        aggrBucketService.aggrBucketRange();
    }

    @Test
    void aggrBucketHistogram() throws IOException {
        aggrBucketService.aggrBucketHistogram();
    }
}