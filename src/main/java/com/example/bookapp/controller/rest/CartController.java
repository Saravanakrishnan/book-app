package com.example.bookapp.controller.rest;

import com.example.bookapp.models.Cart;
import com.example.bookapp.models.CartItem;
import com.example.bookapp.repo.BookRepository;
import com.example.bookapp.repo.CartItemRepository;
import com.example.bookapp.repo.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.example.bookapp.util.Constants.SESSION_CART_KEY;

@RestController
@RequestMapping("/rest/cart")
public class CartController {
    
    @Autowired
    CartRepository cartRepository;
    
    @Autowired
    CartItemRepository cartItemRepository;
    
    @Autowired
    BookRepository bookRepository;
    
    @GetMapping({"", "/"})
    public Cart getCart(HttpSession session) {
        
        Cart cart = null;
        try {
            String cartId = (String) session.getAttribute(SESSION_CART_KEY);
            cart = cartRepository.findBySessionId(cartId);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cart;
    }
    
    @PutMapping({"", "/"})
    public void addToCart(@RequestBody Long bookId, HttpSession session) {
        
        try {
            
            Optional<String> sessionId = Optional.ofNullable((String) session.getAttribute(SESSION_CART_KEY));
            System.out.println(bookId);
            
            Optional.ofNullable(bookRepository.findByBookId(bookId))
                    .ifPresent(book -> {
                        Cart cart = getSavedCart(session);
                        System.out.println(cart);
                        
                        
                        Optional<CartItem> cartItemOptional = Optional.ofNullable(cartItemRepository.findByCartIdAndBookId(cart.getId(), bookId));
                        CartItem cartItem;
                        if (cartItemOptional.isPresent()) {
                            cartItem = cartItemOptional.get();
                            
                            if (cartItem.getActive().compareTo(1) == 0) {
                                cartItem.setActive(1);
                                cartItem.setQuantity(1);
                            }
                            cartItem.setActive(1);
                            // increase the quantity
                            cartItem.setQuantity(cartItem.getQuantity() + 1);
                        } else {
                            // Add the book to cart
                            cartItem = new CartItem();
                            cartItem.setActive(1);
                            cartItem.setBookId(bookId);
                            cartItem.setCartId(cart.getId());
                            cartItem.setQuantity(1); // TODO: Check out of stock
                            cartItem.setPrice(book.getPrice());
                        }
                        cartItem = cartItemRepository.save(cartItem);
                        System.out.println("Book added to cart");
                    });
            
            // Add item to cart
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @DeleteMapping({"", "/"})
    public void deleteFromCart(@RequestBody Long bookId, HttpSession session) {
        try {
            Optional.ofNullable(bookRepository.findByBookId(bookId))
                    .ifPresent(book -> {
                        Cart cart = getSavedCart(session);
                        
                        Optional<CartItem> cartItemOptional = Optional.ofNullable(cartItemRepository.findByCartIdAndBookId(cart.getId(), bookId));
                        CartItem cartItem;
                        if (cartItemOptional.isPresent()) {
                            // just increase the quantity
                            cartItem = cartItemOptional.get();
                            cartItem.setActive(0);
                            cartItemRepository.save(cartItem);
                        } else {
                            System.out.println("Book not in cart");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private Cart getSavedCart(HttpSession session) {
        return Optional.ofNullable(cartRepository.findBySessionId(session.getId()))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setEmail("");
                    cart.setMobile("");
                    cart.setToken("");
                    cart.setStatus(1);
                    
                    cart.setSessionId(session.getId());
                    return cartRepository.save(cart);
                });
    }
    
}
