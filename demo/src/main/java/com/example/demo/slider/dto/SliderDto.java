package com.example.demo.slider.dto;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;

public record SliderDto(
      Long id,

      @NotEmpty(message = "slider name is required") 
      String name,

      List<SliderImageDto> slider_images

) {}
