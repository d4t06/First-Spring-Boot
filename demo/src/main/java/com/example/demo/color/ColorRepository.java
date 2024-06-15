package com.example.demo.color;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.color.entity.Color;

public interface ColorRepository extends JpaRepository<Color, Long> {
    List<Color> findByProductAscii(String productAscii);
}
