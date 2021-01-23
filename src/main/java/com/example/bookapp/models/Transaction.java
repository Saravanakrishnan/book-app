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
@Data
@Table(name = "transaction")
public class Transaction implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "code", nullable = false)
    private String code;
    
    @Column(name = "content")
    private String content;
    
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "mode", nullable = false)
    private Integer mode = 0;
    
    @Column(name = "orderId", nullable = false)
    private Long orderId;
    
    @Column(name = "status", nullable = false)
    private Integer status = 0;
    
    @Column(name = "type", nullable = false)
    private Integer type = 0;
    
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
    
    @Column(name = "userId", nullable = false)
    private Long userId;
    
}
