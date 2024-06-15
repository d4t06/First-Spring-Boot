package com.example.demo.storage.dto;

import com.example.demo.combine.dto.CombineDto;

public record DefaultStorageCombineDto(
                Long storage_id,
                Long combine_id,
                CombineDto combine) {
}
