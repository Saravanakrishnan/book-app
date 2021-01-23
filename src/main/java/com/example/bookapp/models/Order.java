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
@Table(name = "order")
@Data
public class Order implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "userId")
    private Long userId;
    
    @Column(name = "sessionId", nullable = false)
    private String sessionId;

    
    @Column(name = "token", nullable = false)
    private String token;
    
    @Column(name = "status", nullable = false)
    private Integer status = 0;
    
    @Column(name = "total", nullable = false)
    private Float total = 0F;
    
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
    

    
    
    // Enhancement
//    @Column(name = "grandTotal", nullable = false)
//    private Float grandTotal = 0F;
//    
//    @Column(name = "subTotal", nullable = false)
//    private Float subTotal = 0F;
//    
//    @Column(name = "tax", nullable = false)
//    private Float tax = 0F;
//    
//    @Column(name = "shipping", nullable = false)
//    private Float shipping = 0F;
    
    
    
}
