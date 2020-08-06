package com.hx.demo;

import com.alibaba.fastjson.JSON;
import com.hx.demo.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@SpringBootTest
class EsDemoApplicationTests {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    void contextLoads() {
    }

    /**
     * 创建索引
     *
     * @throws IOException
     */
    @Test
    public void createIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("name");
        CreateIndexResponse response = restHighLevelClient.indices()
                .create(createIndexRequest, RequestOptions.DEFAULT);
        log.info(String.valueOf(response));
    }

    @Test
    public void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("indexjava2");
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        log.info("索引存在:" + exists);
    }

    @Test
    public void deleteIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("indexjava");
        AcknowledgedResponse delete = restHighLevelClient.indices()
                .delete(deleteIndexRequest, RequestOptions.DEFAULT);
        log.info("索引删除:" + delete.isAcknowledged());
    }

    /**
     * 创建文档
     *
     * @throws IOException
     */
    @Test
    public void createDocument() throws IOException {
        User user = new User("王", 34);
        IndexRequest request = new IndexRequest("name");
        request.id("6");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.source(JSON.toJSONString(user), XContentType.JSON);
        IndexResponse index = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        log.info(index.toString());
        log.info(String.valueOf(index.status()));
    }

    @Test
    public void testIsExist() throws IOException {
        GetRequest getRequest = new GetRequest("name", "1");
        //不获取上下文
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        log.info("文档是否存在：" + exists);
    }

    /**
     * 获取文档信息
     *
     * @throws IOException
     */
    @Test
    public void testGetDocument() throws IOException {
        GetRequest getRequest = new GetRequest("name", "1");
        GetResponse response = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

        log.info("文档信息：" + response.getSourceAsString());
        log.info(String.valueOf(response));
    }

    @Test
    public void testDeleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest("name", "1");
        request.timeout("10s");
        DeleteResponse del = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        log.info("删除文档操作返回：" + del.status());
    }

    /**
     * 批量插入文档
     *
     * @throws IOException
     */
    @Test
    public void testBulkRequest() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("张三", 12));
        users.add(new User("李四", 22));
        users.add(new User("王五", 45));

        for (int i = 0; i < users.size(); i++) {
            bulkRequest.add(new IndexRequest("name")
                    .id(i + 1 + "")
                    .source(JSON.toJSONString(users.get(i)), XContentType.JSON));

        }

        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        log.info(String.valueOf(bulk));
    }

}
