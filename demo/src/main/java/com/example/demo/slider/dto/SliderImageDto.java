package com.example.demo.slider.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SliderImageDto (
   @NotNull(message = "slider_id is required")
   Long slider_id,

   @NotEmpty(message = "image_url is required")
   String image_url,

   String link_to
) {}
