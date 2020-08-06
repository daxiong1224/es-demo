package com.hx.demo.service;

import com.hx.demo.util.QueryUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class QueryService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 精确查询
     * 查询条件不分词，但是查询内容可能会分词导致查不到
     * 例如查“王五”，查不到“王五"，查“王”，能查到“王五”
     *
     * @throws IOException
     */
    public void termQuery() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("name", "王"));

        QueryUtil.queryUtil(restHighLevelClient, searchSourceBuilder);
    }

    /**
     * 精确查询一个字段多个值
     *
     * @throws IOException
     */
    public void termsQuery() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termsQuery("name", "王", "四"));

        QueryUtil.queryUtil(restHighLevelClient, searchSourceBuilder);
    }


    /**
     * 模糊查询，错误自动修正。
     *
     * @throws IOException
     */
    public void fuzzyQuery() throws IOException {
        //todo 无效问题？
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.fuzzyQuery("name", "HELLA").fuzziness(Fuzziness.TWO));

        QueryUtil.queryUtil(restHighLevelClient, searchSourceBuilder);
    }

    /**
     * 范围查询
     *
     * @throws IOException
     */
    public void rangeQuery() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.rangeQuery("age").gte(23));

        QueryUtil.queryUtil(restHighLevelClient, searchSourceBuilder);
    }

    /**
     * 查所有
     *
     * @throws IOException
     */
    public void matchAllQuery() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        QueryUtil.queryUtil(restHighLevelClient, searchSourceBuilder);
    }

    /**
     * 匹配查询
     * 输入内容会分词（王、五），然后再和结果进行匹配
     *
     * @throws IOException
     */
    public void matchQuery() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("name", "王五"));

        QueryUtil.queryUtil(restHighLevelClient, searchSourceBuilder);
    }


    /**
     * 词语查询匹配
     * 对输入进行分词，要求查询结果包含所有分词且顺序不变
     *
     * @throws IOException
     */
    public void matchPhyraseQuery() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("name", "王五"));

        QueryUtil.queryUtil(restHighLevelClient, searchSourceBuilder);
    }

    /**
     * 多字段匹配查询
     *
     * @throws IOException
     */
    public void matchMultiQuery() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("22", "id", "age"));

        QueryUtil.queryUtil(restHighLevelClient, searchSourceBuilder);
    }

    /**
     * 通配符查询
     * *：表示多个字符
     *
     * @throws IOException
     */
    public void wildcardQuery() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.wildcardQuery("name", "王*"));

        QueryUtil.queryUtil(restHighLevelClient, searchSourceBuilder);
    }

    /**
     * 布尔查询
     * 设置多个条件
     *
     * @throws IOException
     */
    public void boolQueryPre() throws IOException {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.filter(QueryBuilders.termQuery("name", "王"));
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("age").gte(35));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);

        QueryUtil.queryUtil(restHighLevelClient, searchSourceBuilder);
    }
}
