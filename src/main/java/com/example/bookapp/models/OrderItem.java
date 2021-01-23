package com.example.bookapp.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
@Data
public class OrderItem implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "bookId", nullable = false)
    private Long bookId;
    
    @Column(name = "orderId", nullable = false)
    private Long orderId;
    
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "price", nullable = false)
    private Float price = 0F;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;
    
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
    
    // Enhancement
//    @Column(name = "discount", nullable = false)
//    private Float discount = 0F;
    
}
