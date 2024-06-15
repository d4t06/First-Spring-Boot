package com.example.demo.color.dto;

import com.example.demo.product_slider.dto.ProductSliderDto;

import jakarta.validation.constraints.NotEmpty;

public record ColorDto(
        Long id,

        @NotEmpty(message = "color is required") String color,

        @NotEmpty(message = "color_ascii is required") String color_ascii,

        @NotEmpty(message = "product_ascii is required") String product_ascii,

        ProductSliderDto product_slider
        ) {
}
