package com.example.bookapp.repo;

import com.example.bookapp.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {
    
    Cart findBySessionId(String sessionId);
    
    @Query(value = "select (coalesce(sum(ci.quantity * b.price), 0)) as totalPrice from cart c left join cart_item ci on c.id = ci.cartId left join book b on ci.bookId = b.book_id where ci.active = 1 and c.id = :cartId group by cartId", nativeQuery = true)
    Float findCartTotalByCartId(Long cartId);
}