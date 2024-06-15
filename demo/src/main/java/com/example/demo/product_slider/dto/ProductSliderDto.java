package com.example.demo.product_slider.dto;

import com.example.demo.slider.dto.SliderDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProductSliderDto(
                Long id,

                @NotEmpty(message = "category_id is required") Long color_id,

                @NotNull(message = "slider_id is required") Long slider_id,

                SliderDto slider) {
}
