package com.example.demo.image.dto;

import jakarta.validation.constraints.NotEmpty;

public record ImageDto(
      long id,
      @NotEmpty(message = "url not empty")
      String image_url,

      @NotEmpty(message = "name not empty")
      String name,

      @NotEmpty(message = "public id not empty")
      String public_id,

      @NotEmpty(message = "size not empty")
      int size) {
}
