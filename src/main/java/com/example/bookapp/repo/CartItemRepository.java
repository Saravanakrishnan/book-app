package com.example.bookapp.repo;

import com.example.bookapp.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long>, JpaSpecificationExecutor<CartItem> {
    
    List<CartItem> findAllByCartId(Long cartId);
    
    CartItem findByCartIdAndBookId(Long bookId, Long id);
    
    List<CartItem> findAllByCartIdAndActive(Long id, int active);
}