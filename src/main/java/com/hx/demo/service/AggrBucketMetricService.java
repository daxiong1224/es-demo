package com.hx.demo.service;

import com.hx.demo.util.QueryUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedTopHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class AggrBucketMetricService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 按年龄分组，组内按id排序
     *
     * @throws IOException
     */
    public void aggrTopHits() throws IOException {
        AggregationBuilder toHits = AggregationBuilders.topHits("tohits_id")
                .size(3)
                .sort("_id", SortOrder.DESC);
        AggregationBuilder salaryBucket = AggregationBuilders.terms("bucket_age")
                .field("age")
                .size(20);
        salaryBucket.subAggregation(toHits);

        Aggregations aggregations = QueryUtil.sendRequestForAggr(restHighLevelClient, salaryBucket, 10);

        // 输出内容
        if (aggregations != null) {
            // 分桶
            Terms byCompanyAggregation = aggregations.get("bucket_age");
            List<? extends Terms.Bucket> buckets = byCompanyAggregation.getBuckets();
            // 输出各个桶的内容
            log.info("-------------------------------------------");
            log.info("聚合信息:");
            for (Terms.Bucket bucket : buckets) {
                log.info("桶名：{}", bucket.getKeyAsString());
                ParsedTopHits topHits = bucket.getAggregations().get("tohits_id");
                for (SearchHit hit : topHits.getHits()) {
                    log.info(hit.getSourceAsString());
                }
            }
            log.info("-------------------------------------------");
        }
    }
}
