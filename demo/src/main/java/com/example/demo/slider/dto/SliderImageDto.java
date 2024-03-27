package com.example.demo.slider.dto;

import com.example.demo.image.dto.ImageDto;
import jakarta.validation.constraints.NotNull;

public record SliderImageDto (
   Long id,

   @NotNull(message = "slider_id is required")
   Long slider_id,

   @NotNull(message = "image_id is required")
   Long image_id,

   String link_to,

   ImageDto image
) {}
