package com.example.demo.color.dto;

import java.util.List;
import com.example.demo.combine.dto.CombineDto;

public record ColorResponseDto(
        ColorDto color,
        List<CombineDto> combines) {
}
