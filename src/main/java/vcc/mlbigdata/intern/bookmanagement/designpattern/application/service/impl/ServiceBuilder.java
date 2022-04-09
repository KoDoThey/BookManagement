package vcc.mlbigdata.intern.bookmanagement.designpattern.application.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vcc.mlbigdata.intern.bookmanagement.designpattern.application.model.BookBuilder;
import vcc.mlbigdata.intern.bookmanagement.designpattern.application.service.IServiceBuilder;
import vcc.mlbigdata.intern.bookmanagement.layer.application.model.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ServiceBuilder implements IServiceBuilder {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RestHighLevelClient client;


    public List<Book> getByBookName(String value) throws IOException {
        List<Book> result = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("book_es");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("book_name", value));
        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> data = hit.getSourceAsMap();
            result.add(objectMapper.convertValue(data, Book.class));
        }
        return result;
    }


    public List<Book> getByDescription(String value) throws IOException {
        List<Book> result = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("book_es");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("description", value));
        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> data = hit.getSourceAsMap();
            result.add(objectMapper.convertValue(data, Book.class));
        }
        return result;
    }


    public List<Book> getByAuthor(String value) throws IOException {
        List<Book> result = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("book_es");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("author", value));
        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> data = hit.getSourceAsMap();
            result.add(objectMapper.convertValue(data, Book.class));
        }
        return result;
    }

    public List<Book> getByBookType(String value) throws IOException {
        List<Book> result = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("book_es");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("book_type", value));
        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> data = hit.getSourceAsMap();
            result.add(objectMapper.convertValue(data, Book.class));
        }
        return result;
    }


}
