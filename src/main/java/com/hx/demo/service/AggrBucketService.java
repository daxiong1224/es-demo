package com.hx.demo.service;

import com.hx.demo.util.QueryUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 聚合Bucket
 */
@Slf4j
@Service
public class AggrBucketService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 按年龄分组
     *
     * @throws IOException
     */
    public void aggrBucketTerms() throws IOException {
        AggregationBuilder aggr = AggregationBuilders.terms("first_bucket").field("age");
        Aggregations aggregations = QueryUtil.sendRequestForAggr(restHighLevelClient, aggr, 10);

        // 输出内容
        if (aggregations != null) {
            // 分桶
            Terms byCompanyAggregation = aggregations.get("first_bucket");
            List<? extends Terms.Bucket> buckets = byCompanyAggregation.getBuckets();
            // 输出各个桶的内容
            log.info("-------------------------------------------");
            log.info("聚合信息:");
            for (Terms.Bucket bucket : buckets) {
                log.info("桶名：{} | 总数：{}", bucket.getKeyAsString(), bucket.getDocCount());
            }
            log.info("-------------------------------------------");
        }
    }

    /**
     * 自定义范围分组
     *
     * @throws IOException
     */
    public void aggrBucketRange() throws IOException {
        AggregationBuilder aggr = AggregationBuilders.range("range_bucket").field("age")
                .addUnboundedTo("小于23", 23)
                .addRange("23-46", 23, 46)
                .addUnboundedFrom("大于46", 46);
        Aggregations aggregations = QueryUtil.sendRequestForAggr(restHighLevelClient, aggr, 10);

        // 输出内容
        if (aggregations != null) {
            // 分桶
            Range byCompanyAggregation = aggregations.get("range_bucket");
            List<? extends Range.Bucket> buckets = byCompanyAggregation.getBuckets();
            // 输出各个桶的内容
            log.info("-------------------------------------------");
            log.info("聚合信息:");
            for (Range.Bucket bucket : buckets) {
                log.info("桶名：{} | 总数：{}", bucket.getKeyAsString(), bucket.getDocCount());
            }
            log.info("-------------------------------------------");
        }
    }

    /**
     * Histogram聚合
     *
     * @throws IOException
     */
    public void aggrBucketHistogram() throws IOException {
        AggregationBuilder aggr = AggregationBuilders.histogram("his_bucket").field("age")
                .extendedBounds(10, 100)
                .interval(5);//设置间距
        Aggregations aggregations = QueryUtil.sendRequestForAggr(restHighLevelClient, aggr, 10);

        // 输出内容
        if (aggregations != null) {
            // 分桶
            Histogram byCompanyAggregation = aggregations.get("his_bucket");
            List<? extends Histogram.Bucket> buckets = byCompanyAggregation.getBuckets();
            // 输出各个桶的内容
            log.info("-------------------------------------------");
            log.info("聚合信息:");
            for (Histogram.Bucket bucket : buckets) {
                log.info("桶名：{} | 总数：{}", bucket.getKeyAsString(), bucket.getDocCount());
            }
            log.info("-------------------------------------------");
        }
    }
}
