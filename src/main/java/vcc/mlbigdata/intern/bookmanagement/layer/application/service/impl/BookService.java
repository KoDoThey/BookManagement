package vcc.mlbigdata.intern.bookmanagement.layer.application.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vcc.mlbigdata.intern.bookmanagement.layer.application.model.Book;
import vcc.mlbigdata.intern.bookmanagement.layer.application.service.IBookService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
public class BookService implements IBookService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RestHighLevelClient client;

    ElasticsearchClient elasticsearchClient;

//    @Autowired
//    ElasticsearchOperations elasticsearchOperations;


    public String addBook(Book book) throws Exception {
        IdGenerator idGenerator = new IdGenerator(1);
        book.setId(String.valueOf(idGenerator.nextId()));

        Map<String, Object> jsonMap = objectMapper.convertValue(book, new TypeReference<Map<String, Object>>() {
        });
        System.out.println(book.getId());
        IndexRequest indexRequest = new IndexRequest("book_es").id(book.getId()).source(jsonMap);
//        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        client.index(indexRequest, RequestOptions.DEFAULT);
        return "Add!";
    }

    public Long countBook(String book_id) throws IOException {
        CountRequest countRequest = new CountRequest("book_es");
        countRequest.query(matchQuery("book_id", book_id));
        CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
        return countResponse.getCount();
    }

    public Long countBook() throws IOException {
        CountRequest countRequest = new CountRequest("book_es");
        countRequest.query(matchAllQuery());
        CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
        return countResponse.getCount();
    }

    public Boolean checkExist(String id) throws IOException {
        GetRequest getRequest = new GetRequest("book_es", id);
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        boolean exist = client.exists(getRequest, RequestOptions.DEFAULT);
        return exist;
    }


    /**
     * term query
     */
//    public List<Book> findByBookName(String bookName) throws IOException {
//        List<Book> result = new ArrayList<>();
//        SearchRequest searchRequest = new SearchRequest("book_es");
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
////        searchSourceBuilder.query(QueryBuilders.matchAllQuery()); //match all
//        sourceBuilder.query(QueryBuilders.termQuery("book_name", bookName));
//        searchRequest.source(sourceBuilder);
//        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
//        ObjectMapper objectMapper = new ObjectMapper();
//        for (SearchHit hit : response.getHits().getHits()) {
//            Map<String, Object> data = hit.getSourceAsMap();
//            result.add(objectMapper.convertValue(data, Book.class)); //convertValue tra 1 instance Book
//        }
//        return result;
//    }


    /**
     * Range query
     */
    public List<Book> findByRangeYear(Integer startYear, Integer stopYear) throws Exception {
        List<Book> result = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("book_es");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.rangeQuery("publish_year").from(startYear).to(stopYear));
        sourceBuilder.size(20);
        searchRequest.source(sourceBuilder);

//        RangeQueryBuilder queryBuilder = new RangeQueryBuilder(""); //de lam gi
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> data = hit.getSourceAsMap();
            result.add(objectMapper.convertValue(data, Book.class));
        }
        return result;
    }


    /**
     * search scroll
     */
//    public void getBookByBookType(String bookType) throws IOException {
//        SearchRequest searchRequest = new SearchRequest("book_es");
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.query(matchQuery("book_type", bookType));
//        QueryBuilder queryBuilder = matchQuery("book_type", bookType);
////        sourceBuilder.query(matchQuery("book_type", bookType));
////        QueryBuilder queryBuilder = QueryBuilders.matchQuery("book_type", bookType);
//        sourceBuilder.size(3);
//        searchRequest.source(sourceBuilder);
//        searchRequest.scroll(TimeValue.timeValueSeconds(10L));
//
//        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//        String scrollId = searchResponse.getScrollId();
//        SearchHits hits = searchResponse.getHits();
//
//        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
//        scrollRequest.scroll(TimeValue.timeValueSeconds(10));
//        SearchResponse searchScrollResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
//        scrollId = searchScrollResponse.getScrollId();
//        hits = searchScrollResponse.getHits();
//    }

    /**
     * list all
     */
    public List<Book> getAll() throws IOException {
        List<Book> result = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("book_es");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        sourceBuilder.size(20);
        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> data = hit.getSourceAsMap();
            result.add(objectMapper.convertValue(data, Book.class)); //convertValue tra 1 instance Book
        }
        return result;
    }


    /**
     * findAllField
     */
    public List<Book> getAllField1(String value) throws IOException {
        List<Book> result = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("book_es");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.queryStringQuery(value));
        sourceBuilder.size(20);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> data = hit.getSourceAsMap();
            result.add(objectMapper.convertValue(data, Book.class));
        }
        return result;
    }

    public List<Book> getAllField(String value) throws IOException {
        SearchRequest searchRequest = new SearchRequest("book_es");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryStringQuery(value));
        sourceBuilder.size(20);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        return getSearchResult(searchResponse);
    }



    /**
     * match phrase
     */
    public List<Book> findByAny(String value) throws IOException {
        List<Book> result = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("book_es");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.query(QueryBuilders.matchAllQuery()); //match all
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("book_name", value));
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("book_type", value));
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("book_description", value));
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("publisher", value));
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("author", value));
        searchRequest.source(sourceBuilder);
        sourceBuilder.size(20);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> data = hit.getSourceAsMap();
            result.add(objectMapper.convertValue(data, Book.class)); //convertValue tra 1 instance Book
        }
        return result;
    }


    /**
     * delete document
     */
    public void deleteBook(String bookId) throws IOException {
        DeleteRequest request = new DeleteRequest("book_es", bookId);
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
    }


    private List<Book> getSearchResult(SearchResponse response) {
        SearchHit[] searchHit = response.getHits().getHits();
        List<Book> personDocuments = new ArrayList<>();
        for (SearchHit hit : searchHit) {
            personDocuments.add(objectMapper.convertValue(hit.getSourceAsMap(), Book.class));
        }

        return personDocuments;
    }

    @Override
    public void close() throws IOException {

    }
}
// readValue: String JSON
//convertValue: Map