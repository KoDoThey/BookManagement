package vcc.mlbigdata.intern.bookmanagement.util.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResultResponse<T> {
    private List<T> data = new ArrayList<>();
    private Integer limit;
    private Integer page;
    private Long total;

    public static <T> ResultResponse<T> empty() {
        return new ResultResponse<T>();
    }

}