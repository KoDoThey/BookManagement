package vcc.mlbigdata.intern.bookmanagement.util.response;

import org.json.JSONObject;
import vcc.mlbigdata.intern.bookmanagement.util.exception.ClientException;

import java.util.List;

public class ResponseFactory {

    public static MyResponseStatus getSuccessResponse(String message, List entities) {
        return new MyResponseStatus.ResponseBuilders()
                .setCode(MyResponseStatus.OK_CODE)
                .setStatus(MyResponseStatus.OK_STATUS)
                .setMessage(message)
                .setData(entities)
                .build();
    }

    public static MyResponseStatus getSuccessResponse(String message, Object entity) {
        return new MyResponseStatus.ResponseBuilders()
                .setCode(MyResponseStatus.OK_CODE)
                .setStatus(MyResponseStatus.OK_STATUS)
                .setMessage(message)
                .setData(entity)
                .build();
    }

    public static MyResponseStatus getSuccessResponse(String message, JSONObject entity) {
        return new MyResponseStatus.ResponseBuilders()
                .setCode(MyResponseStatus.OK_CODE)
                .setStatus(MyResponseStatus.OK_STATUS)
                .setMessage(message)
                .setData(entity)
                .build();
    }

    public static MyResponseStatus getSuccessResponse(String message) {
        return new MyResponseStatus.ResponseBuilders()
                .setCode(MyResponseStatus.OK_CODE)
                .setStatus(MyResponseStatus.OK_STATUS)
                .setMessage(message)
                .setData(ResultResponse.empty())
                .build();
    }

    @Deprecated
    public static MyResponseStatus getServerErrorResponse(String message, Object entities) {
        return new MyResponseStatus.ResponseBuilders()
                .setCode(MyResponseStatus.INTERNAL_ERROR_CODE)
                .setStatus(MyResponseStatus.ERROR_STATUS)
                .setMessage(message)
                .setData(entities)
                .build();
    }

    public static MyResponseStatus getServerErrorResponse(ClientException.Message message) {
        return new MyResponseStatus.ResponseBuilders<ResultResponse>()
                .setCode(message.code)
                .setStatus(message.status)
                .setMessage(message.message)
                .setData(ResultResponse.empty())
                .build();
    }

    public static MyResponseStatus getClientErrorResponse(String message) {
        return new MyResponseStatus.ResponseBuilders().
                setCode(MyResponseStatus.CLIENT_ERROR_CODE)
                .setStatus(MyResponseStatus.ERROR_STATUS)
                .setMessage(message)
                .setData(ResultResponse.empty())
                .build();
    }

    public static MyResponseStatus getClientErrorResponse(ClientException.Message message) {
        return new MyResponseStatus.ResponseBuilders<ResultResponse>()
                .setCode(message.code)
                .setStatus(message.status)
                .setMessage(message.message)
                .setData(ResultResponse.empty())
                .build();
    }

    public static MyResponseStatus getInfoNotFound(String message) {
        MyResponseStatus response = new MyResponseStatus();
        response.setCode(MyResponseStatus.NOT_FOUND_INFO);
        response.setStatus(MyResponseStatus.ERROR_STATUS);
        response.setMessage(message);
        response.data = new MyResponseStatus<>();
        return response;
    }
}