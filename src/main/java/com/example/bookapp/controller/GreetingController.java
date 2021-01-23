package com.example.bookapp.controller;

import com.example.bookapp.models.Book;
import com.example.bookapp.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Controller
public class GreetingController {
    
    @Autowired
    BookRepository bookRepository;
    
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
//        model.addAttribute("name", name);
        return "greeting";
    }
    
    @GetMapping("/shop")
    public String shop(@RequestParam(value = "search", defaultValue = "") String search,
                       @RequestParam(value = "sort", defaultValue = "bookId") String sort,
                       Model model) {
        List<Book> books;
        try {
            // By default the books are sorted by bookId
            Sort by = Sort.by(Sort.Direction.ASC, sort);
            
            // By default, 
            Specification<Book> titleSpec = (root, query, builder) -> builder.like(root.get("title"), "%" + search + "%");
            
            books = bookRepository.findAll(where(titleSpec), by);
        } catch (Exception e) {
            e.printStackTrace();
            books = Collections.emptyList();
        }
        model.addAttribute("books", books);
        return "shop";
    }
    
    
}
