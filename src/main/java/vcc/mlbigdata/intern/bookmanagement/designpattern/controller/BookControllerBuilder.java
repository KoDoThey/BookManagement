package vcc.mlbigdata.intern.bookmanagement.designpattern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vcc.mlbigdata.intern.bookmanagement.designpattern.application.model.BookBuilder;
import vcc.mlbigdata.intern.bookmanagement.designpattern.application.service.impl.ServiceBuilder;
import vcc.mlbigdata.intern.bookmanagement.layer.application.model.Book;
import vcc.mlbigdata.intern.bookmanagement.util.response.MyResponseStatus;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookControllerBuilder {
    @Autowired
    ServiceBuilder serviceBuilder;

//    @GetMapping("/list")
//    public MyResponseStatus getBook(@RequestParam ("value") String value) throws IOException{
//        List<Book> books = serviceBuilder.getByBookName(value);
//
//    }
}
