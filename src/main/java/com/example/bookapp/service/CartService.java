package com.example.bookapp.service;

import com.example.bookapp.models.Book;
import com.example.bookapp.models.Cart;
import com.example.bookapp.models.CartItem;
import com.example.bookapp.repo.BookRepository;
import com.example.bookapp.repo.CartItemRepository;
import com.example.bookapp.repo.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public class CartService {
    
    @Autowired
    BookRepository bookRepository;
    
    @Autowired
    CartItemRepository cartItemRepository;
    
    @Autowired
    CartRepository cartRepository;
    
    public Map<Long, Map<String, String>> getCartItemWithPriceDetails(Long cartId) {
        
        List<CartItem> cartItemcartItemList;
        Map<Long, Book> books;
        Map<Long, Map<String, String>> cartItemsMap = new HashMap<>();
        try {
            cartItemcartItemList = cartItemRepository.findAllByCartIdAndActive(cartId, 1);
            books = cartItemcartItemList.stream().map(cartItem -> bookRepository.findByBookId(cartItem.getBookId())).collect(Collectors.toMap(Book::getBookId, book -> book));
            
            cartItemcartItemList.forEach(cartItem -> {
                Optional<Book> optionalBook = ofNullable(books.get(cartItem.getBookId()));
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
        }
        return cartItemsMap;
    }
    
    /**
     * this should be called only from webhook call back
     *
     * @param cart
     */
    public void completeCheckout(Cart cart) {
        
        cart.setStatus(1); // order completed
        //cart.setToken(""); // TODO: Update from webhook call back
        cartRepository.save(cart);
    }
}
