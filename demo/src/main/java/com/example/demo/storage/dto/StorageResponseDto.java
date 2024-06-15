package com.example.demo.storage.dto;

import java.util.List;

import com.example.demo.combine.dto.CombineDto;

public record StorageResponseDto(
        StorageDto storage,
        List<CombineDto> combines) {
}
