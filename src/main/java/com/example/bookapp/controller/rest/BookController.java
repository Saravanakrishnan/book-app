package com.example.bookapp.controller.rest;

import com.example.bookapp.models.Book;
import com.example.bookapp.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.data.jpa.domain.Specification.where;

@RestController
@RequestMapping("/rest/book")
public class BookController {
    
    public BookController() {
        System.out.println("BookController initialized");
    }
    
    public static final Logger logger = Logger.getLogger(BookController.class.getName());
    
    @Autowired
    BookRepository bookRepository;
    
    /**
     * @param search
     * @param sort   sort-column-name, default to bookId
     * @return
     */
    @GetMapping({"", "/"})
    public List<Book> getBooks(@RequestParam(value = "search", defaultValue = "") String search,
                               @RequestParam(value = "sort", defaultValue = "bookId") String sort) {
        List<Book> results;
        try {
            // By default the books are sorted by bookId
            Sort by = Sort.by(Sort.Direction.ASC, sort);
            
            // By default, 
            Specification<Book> titleSpec = (root, query, builder) -> builder.like(root.get("title"), "%" + search + "%");
            
            results = bookRepository.findAll(where(titleSpec), by);
        } catch (Exception e) {
            e.printStackTrace();
            results = Collections.emptyList();
        }
        return results;
    }
}
