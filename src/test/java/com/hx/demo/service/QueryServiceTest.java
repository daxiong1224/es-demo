package com.hx.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


@Slf4j
@SpringBootTest
class QueryServiceTest {
    
    @Autowired
    private QueryService queryService;
    
    @Test
    void termQuery() throws IOException {
        queryService.termQuery();
    }
    
    @Test
    void termsQuery() throws IOException {
        queryService.termsQuery();
    }
    
    @Test
    void rangeQuery() throws IOException {
        queryService.rangeQuery();
    }

    @Test
    void fuzzyQuery() throws IOException {
        queryService.fuzzyQuery();
    }
    
    @Test
    void matchAllQuery() throws IOException {
        queryService.matchAllQuery();
    }
    
    @Test
    void matchQuery() throws IOException {
        queryService.matchQuery();
    }
    

    @Test
    void matchPhyraseQuery() throws IOException {
        queryService.matchPhyraseQuery();
    }
    
    @Test
    void matchMultiQuery() throws IOException {
        queryService.matchMultiQuery();
    }
    
    @Test
    void wildcardQuery() throws IOException {
        queryService.wildcardQuery();
    }
    
    @Test
    void boolQueryPre() throws IOException {
        queryService.boolQueryPre();
    }
}