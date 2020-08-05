package com.hx.demo.config;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class EsConfig {

    //协议
    @Value("${elasticsearch.schema}")
    private String schema;

    //地址，集群用,号间隔
    @Value("${elasticsearch.address}")
    private String address;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        List<HttpHost> hostList = new ArrayList<>();
        String[] hostArry = address.split(",");
        for (String addr : hostArry) {
            String host = addr.split(":")[0];
            String port = addr.split(":")[1];
            hostList.add(new HttpHost(host, Integer.parseInt(port), schema));
        }
        HttpHost[] httpHosts = hostList.toArray(new HttpHost[]{});
        RestClientBuilder builder = RestClient.builder(httpHosts);
        return new RestHighLevelClient(builder);
    }
}
