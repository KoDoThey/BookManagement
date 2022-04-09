package vcc.mlbigdata.intern.bookmanagement.layer.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vcc.mlbigdata.intern.bookmanagement.layer.application.model.Book;
import vcc.mlbigdata.intern.bookmanagement.layer.application.service.impl.BookService;
import vcc.mlbigdata.intern.bookmanagement.util.response.MyResponseStatus;
import vcc.mlbigdata.intern.bookmanagement.util.response.ResponseFactory;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/book/")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping("/add")
    public MyResponseStatus addBook(@RequestBody Book book) throws Exception {
        bookService.addBook(book);
        return ResponseFactory.getSuccessResponse("Add!");
    }

    @PostMapping("/update")
    public MyResponseStatus updateBook(@RequestBody Book book) throws Exception {
        Boolean checkExist = bookService.checkExist(book.getId());
        System.out.println(book.getId());
        if (checkExist) {
            bookService.addBook(book);
            return ResponseFactory.getSuccessResponse("Update");
        } else return ResponseFactory.getClientErrorResponse("ID is not exist");
    }

    @GetMapping("/get/exist")
    public MyResponseStatus checkExist(@RequestParam String id) throws IOException {
        Boolean exist = bookService.checkExist(id);
        return ResponseFactory.getSuccessResponse("OK", exist);
    }

    @GetMapping("/count")
    public MyResponseStatus count() throws IOException {
        Long bookNum = bookService.countBook();
        return ResponseFactory.getSuccessResponse("OK", bookNum);
    }

    @GetMapping("/get")
    public MyResponseStatus getByBookName(@RequestParam(required = false) String book_name,
                                          @RequestParam(required = false) String book_type,
                                          @RequestParam(required = false) String book_description,
                                          @RequestParam(required = false) String publisher,
                                          @RequestParam(required = false) String author,
                                          @RequestParam(required = false) String search) throws Exception {
        if (book_name != null && book_type == null && book_description == null && publisher == null && author == null) {
            List<Book> books = bookService.findByAny(book_name);
            return ResponseFactory.getSuccessResponse("OK", books);
        }
        if (book_name == null && book_type != null && book_description == null && publisher == null && author == null) {
            List<Book> books = bookService.findByAny(book_type);
            return ResponseFactory.getSuccessResponse("OK", books);
        }
        if (book_type == null && book_name != null && book_description != null && publisher == null && author == null) {
            List<Book> books = bookService.findByAny(book_description);
            return ResponseFactory.getSuccessResponse("OK", books);
        }
        if (book_type == null && book_name == null && book_description == null && publisher != null && author == null) {
            List<Book> books = bookService.findByAny(publisher);
            return ResponseFactory.getSuccessResponse("OK", books);
        }
        if (book_type == null && book_name == null && book_description == null && publisher == null && author != null) {
            List<Book> books = bookService.findByAny(author);
            return ResponseFactory.getSuccessResponse("OK", books);
        }
        if (search != null){
            List<Book> books = bookService.getAllField(search);
            return ResponseFactory.getSuccessResponse("OK", books);
        }
        else return ResponseFactory.getClientErrorResponse("Only 1 field!");
    }

    @GetMapping("/get/range-year")
    public MyResponseStatus findByRangeYear(@RequestParam("start_year") Integer start_year,
                                            @RequestParam("stop_year") Integer stop_year) throws Exception {
        List<Book> books = bookService.findByRangeYear(start_year, stop_year);
        return ResponseFactory.getSuccessResponse("OK", books);
    }

    @GetMapping("/get-all")
    public MyResponseStatus getAll() throws IOException {
        List<Book> books = bookService.getAll();
        return ResponseFactory.getSuccessResponse("OK", books);
    }

    @DeleteMapping("/delete")
    public MyResponseStatus deleteBook(@RequestParam("book_id") String bookId) throws IOException {
        bookService.deleteBook(bookId);
        return ResponseFactory.getSuccessResponse("OK");
    }


}