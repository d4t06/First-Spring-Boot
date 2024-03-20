package com.example.demo.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.image.entity.Image;
import java.util.List;


@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
   List<Image> findByPublicID(String publicID);
}
