package com.hx.demo.util;

import com.alibaba.fastjson.JSON;
import com.hx.demo.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

@Slf4j
public class QueryUtil {

    public static void queryUtil(RestHighLevelClient restHighLevelClient, SearchSourceBuilder searchSourceBuilder) throws IOException {
        SearchRequest searchRequest = new SearchRequest("name");
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        if (RestStatus.OK.equals(searchResponse.status()) && searchResponse.getHits().getHits().length > 0) {
            SearchHits hits = searchResponse.getHits();
            for (SearchHit hit : hits) {
                User user = JSON.parseObject(hit.getSourceAsString(), User.class);
                log.info(user.toString());
            }
        }
    }


    /**
     * 聚合查询发送请求
     * @param restHighLevelClient
     * @param aggr
     * @return
     * @throws IOException
     */
    public static Aggregations sendRequestForAggr(RestHighLevelClient restHighLevelClient, AggregationBuilder aggr
                    , int size) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(aggr);
        searchSourceBuilder.size(size);//设置查询结果返回数

        //发送请求
        SearchRequest searchRequest = new SearchRequest("name");
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        
        if (RestStatus.OK.equals(searchResponse.status()) && searchResponse.getHits().getHits().length > 0) {
            SearchHits hits = searchResponse.getHits();
            for (SearchHit hit : hits) {
                User user = JSON.parseObject(hit.getSourceAsString(), User.class);
                log.info(user.toString());
            }
        }
        
        Aggregations aggregations = searchResponse.getAggregations();//获取响应中的聚合信息
        
        return aggregations;
    }
}
