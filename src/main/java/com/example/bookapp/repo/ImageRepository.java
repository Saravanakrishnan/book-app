package com.example.bookapp.repo;

import com.example.bookapp.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long>, JpaSpecificationExecutor<Image> {
    
    @Query(value = "SELECT i.image_url as image_url FROM image i where i.is_deleted = false ORDER BY rand() limit 1", nativeQuery = true)
    Object findImage();
    
}