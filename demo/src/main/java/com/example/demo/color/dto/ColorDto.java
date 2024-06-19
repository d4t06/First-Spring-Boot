package com.example.demo.color.dto;

import com.example.demo.product_slider.dto.ProductSliderDto;

import jakarta.validation.constraints.NotEmpty;

public record ColorDto(
        Long id,

        @NotEmpty(message = "color is required") String color_name,

        @NotEmpty(message = "color_ascii is required") String color_name_ascii,

        @NotEmpty(message = "product_ascii is required") Long product_id,

        ProductSliderDto product_slider
        ) {
}
