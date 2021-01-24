package com.example.bookapp.controller;

import com.example.bookapp.models.Book;
import com.example.bookapp.models.Cart;
import com.example.bookapp.models.CartItem;
import com.example.bookapp.repo.BookRepository;
import com.example.bookapp.repo.CartItemRepository;
import com.example.bookapp.repo.CartRepository;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.lang.Integer.MAX_VALUE;
import static java.util.Optional.ofNullable;
import static org.springframework.data.jpa.domain.Specification.where;

@Controller
public class GreetingController {
    
    @Autowired
    BookRepository bookRepository;
    
    @Autowired
    CartItemRepository cartItemRepository;
    
    @Autowired
    CartRepository cartRepository;
    
    @GetMapping("/shop")
    public String shop(@RequestParam(value = "search", defaultValue = "") String search,
                       @RequestParam(value = "sort", defaultValue = "bookId") String sort,
                       @RequestParam(value = "limit", defaultValue = "10") String limitStr,
                       Model model,
                       HttpSession session) {
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
        
        return "shop";
    }
    
    @GetMapping("/cart")
    public String cart(Model model, HttpSession session) {
        
        List<CartItem> cartItemcartItemList = Collections.emptyList();
        Map<Long, Book> books;
        Map<Long, Map<String, String>> cartItemsMap = new HashMap<>();
        Cart cart = null;
        float totalCost = 0;
        try {
            cart = cartRepository.findBySessionId(session.getId());
            cartItemcartItemList = cartItemRepository.findAllByCartIdAndActive(cart.getId(), 1);
            totalCost = cartRepository.findCartTotalByCartId(cart.getId());
            books = cartItemcartItemList.stream().map(cartItem -> bookRepository.findByBookId(cartItem.getBookId())).collect(Collectors.toMap(Book::getBookId, book -> book));
            
            final Map<Long, Book> finalBooks = books;
            cartItemcartItemList.forEach(cartItem -> {
                Optional<Book> optionalBook = ofNullable(finalBooks.get(cartItem.getBookId()));
                if (optionalBook.isPresent()) {
                    Book book = optionalBook.get();
                    Map<String, String> map = new HashMap<>();
                    map.put("title", book.getTitle());
                    map.put("authors", book.getAuthors());
                    map.put("bookId", book.getBookId().toString());
                    map.put("price", book.getPrice().toString());
                    map.put("total", String.valueOf(book.getPrice() * cartItem.getQuantity()));
                    cartItemsMap.put(book.getBookId(), map);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            books = new TreeMap<>();
        }
        
        model.addAttribute("cart", cart);
        model.addAttribute("cartItems", cartItemsMap);
        model.addAttribute("books", books);
        model.addAttribute("totalCost", totalCost);
        return "cart";
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
