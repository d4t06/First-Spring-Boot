package com.example.demo.category.dto;

import com.example.demo.slider.dto.SliderDto;

import jakarta.validation.constraints.NotNull;

public record CategorySliderDto (
    Long id,
    
    @NotNull (message = "category_id is required")
    Long category_id,

    @NotNull (message = "slider_id is required")
    Long slider_id,

    SliderDto slider
) {}
