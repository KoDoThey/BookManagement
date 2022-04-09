package vcc.mlbigdata.intern.bookmanagement.layer.application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Book {
    @JsonProperty("book_id")
    private String id;

    @JsonProperty("book_name")
    private String bookName;

    @JsonProperty("book_type")
    private String bookType;

    @JsonProperty("book_description")
    private String bookDescription;

    private String publisher;

    @JsonProperty("publish_year")
    private Integer publishYear;

    private String author;

    @JsonProperty("page_number")
    private int pageNumber;

    private int price;
}
