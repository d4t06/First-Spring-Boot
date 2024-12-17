package com.example.demo.default_storage.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.default_storage.dto.DefaultStorageDto;
import com.example.demo.default_storage.entity.DefaultStorage;
import com.example.demo.storage.converter.StorageToStorageDetailDto;

@Component
public class DStorageToDStorageDetailDto implements Converter<DefaultStorage, DefaultStorageDto> {

    private final StorageToStorageDetailDto storageToStorageDetailDto;

    public DStorageToDStorageDetailDto(StorageToStorageDetailDto storageToStorageDetailDto) {
        this.storageToStorageDetailDto = storageToStorageDetailDto;
    }

    @Override
    public DefaultStorageDto convert(DefaultStorage source) {
        DefaultStorageDto dto = new DefaultStorageDto(
                source.getProductId(),
                source.getStorage_id(),
                this.storageToStorageDetailDto.convert(source.getStorage()));

        return dto;
    }

}
