package com.hx.demo.service;

import com.hx.demo.util.QueryUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.ParsedMin;
import org.elasticsearch.search.aggregations.metrics.ParsedStats;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class AggrMetricService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 聚合Metric
     * 统计聚合信息
     *
     * @throws IOException
     */
    public void metricQuery() throws IOException {
        AggregationBuilder aggr = AggregationBuilders.stats("age_stats").field("age");
        Aggregations aggregations = QueryUtil.sendRequestForAggr(restHighLevelClient, aggr, 0);

        if (aggregations != null) {
            //转换为state对象
            ParsedStats aggregation = aggregations.get("age_stats");
            log.info("----------------聚合信息------------------------");
            log.info("count:{}", aggregation.getCount());
            log.info("avg:{}", aggregation.getAvg());
            log.info("max:{}", aggregation.getMax());
            log.info("min:{}", aggregation.getMin());
            log.info("sum:{}", aggregation.getSum());
            log.info("-----------------------------------------------");
        }
    }

    /**
     * 取最小
     *
     * @throws IOException
     */
    public void minQuery() throws IOException {
        AggregationBuilder aggr = AggregationBuilders.min("age_min").field("age");
        Aggregations aggregations = QueryUtil.sendRequestForAggr(restHighLevelClient, aggr, 0);

        if (aggregations != null) {
            //转换为min对象
            ParsedMin aggregation = aggregations.get("age_min");
            log.info("----------------聚合信息------------------------");
            log.info("min:{}", aggregation.getValue());
            log.info("-----------------------------------------------");
        }
    }

    /**
     * 取和
     *
     * @throws IOException
     */
    public void sumQuery() throws IOException {
        AggregationBuilder aggr = AggregationBuilders.sum("age_sum").field("age");
        Aggregations aggregations = QueryUtil.sendRequestForAggr(restHighLevelClient, aggr, 0);

        if (aggregations != null) {
            //转换为sum对象
            ParsedSum aggregation = aggregations.get("age_sum");
            log.info("----------------聚合信息------------------------");
            log.info("sum:{}", aggregation.getValue());
            log.info("-----------------------------------------------");
        }
    }
}
