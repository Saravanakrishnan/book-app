package com.example.bookapp.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "book")
@Data
public class Book implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false)
    private Long bookId;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "authors", nullable = false)
    private String authors;
    
    @Column(name = "average_rating", nullable = false)
    private String averageRating;
    
    @Column(name = "language_code", nullable = false)
    private String languageCode;
    
    @Column(name = "rating_count", nullable = false)
    private String ratingCount;
    
    @Column(name = "price", nullable = false)
    private Float price = 0F;
}
