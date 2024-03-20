package com.example.demo.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.image.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
   
}
