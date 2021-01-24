package com.example.bookapp.controller;

import com.example.bookapp.models.Book;
import com.example.bookapp.repo.BookRepository;
import com.example.bookapp.repo.CartItemRepository;
import com.example.bookapp.repo.CartRepository;
import com.example.bookapp.service.CartService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static java.lang.Integer.MAX_VALUE;
import static java.util.Collections.emptyMap;
import static org.springframework.data.jpa.domain.Specification.where;

@Controller
public class PageController {
    
    @Autowired
    BookRepository bookRepository;
    
    @Autowired
    CartItemRepository cartItemRepository;
    
    @Autowired
    CartRepository cartRepository;
    
    @Autowired
    CartService cartService;
    
    @GetMapping(value = {"", "/", "/shop", "/index"})
    public String shop(@RequestParam(value = "search", defaultValue = "") String search,
                       @RequestParam(value = "sort", defaultValue = "bookId") String sort,
                       @RequestParam(value = "limit", defaultValue = "10") String limitStr,
                       Model model) {
        Page<Book> books;
        Integer limit;
        
        model.addAttribute("options_sort", getSortMap());
        model.addAttribute("options_page", getPageSizeMap());
        if (NumberUtils.isParsable(limitStr)) {
            limit = Integer.parseInt(limitStr);
        } else {
            limit = MAX_VALUE;
        }
        try {
            // By default the books are sorted by bookId
            Sort by = Sort.by(Sort.Direction.ASC, sort);
            
            Specification<Book> bookSpec = (root, query, builder) -> builder.like(root.get("title"), "%" + search + "%");
            Pageable pageable = PageRequest.of(0, limit, by);
            
            books = bookRepository.findAll(where(bookSpec), pageable);
        } catch (Exception e) {
            e.printStackTrace();
            books = null;
        }
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("limit", limit.compareTo(MAX_VALUE) == 0 ? "all" : limit.toString());
        model.addAttribute("books", books);
        
        return "index";
    }
    
    @GetMapping("/cart")
    public String cart(Model model, HttpSession session) {
        
        try {
            model.addAttribute("totalCost", 0);
            model.addAttribute("cartItems", emptyMap());
            Optional.ofNullable(cartRepository.findBySessionIdAndStatus(session.getId(), 0))
                    .ifPresent(cart -> {
                        model.addAttribute("totalCost", cartRepository.findCartTotalByCartId(cart.getId()));
                        model.addAttribute("cartItems", cartService.getCartItemWithPriceDetails(cart.getId()));
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "cart";
    }
    
    @GetMapping("/success")
    public String success(Model model, HttpSession session) {
        Optional.ofNullable(cartRepository.findBySessionIdAndStatus(session.getId(), 0))
                .ifPresent(cart -> cartService.completeCheckout(cart));
        
        return "success";
    }
    
    @GetMapping("/failed")
    public String failed(Model model) {
        return "failed";
    }
    
    private Map<String, String> getPageSizeMap() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("10", "10");
        map.put("20", "20");
        map.put("50", "50");
        map.put("all", "all");
        return map;
    }
    
    private Map<String, String> getSortMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("bookId", "Book");
        map.put("title", "Title");
        map.put("authors", "Authors");
        map.put("price", "Price");
        map.put("averageRating", "Rating");
        return map;
    }
    
}
