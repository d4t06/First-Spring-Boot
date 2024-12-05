package com.example.demo.category.dto;

import java.util.List;

public record JsonCategoryDto(

   String name,
   List<String> brands,
   List<String> attributes
) {}
