package com.example.demo.storage.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.storage.dto.StorageDto;
import com.example.demo.storage.entity.Storage;

@Component
public class StorageToStorageDetailDto implements Converter<Storage, StorageDto> {

    private final DefaultSCToDefaultSCDetailDto defaultSCToDefaultSCDetailDto;

    public StorageToStorageDetailDto(DefaultSCToDefaultSCDetailDto defaultSCToDefaultSCDetailDto) {
        this.defaultSCToDefaultSCDetailDto = defaultSCToDefaultSCDetailDto;
    }

    @Override
    public StorageDto convert(Storage source) {
        StorageDto storageDto = new StorageDto(
                source.getId(),
                source.getStorage(),
                source.getStorage_ascii(),
                source.getProductAscii(),
                source.getDefaultStorageCombine() != null
                        ? this.defaultSCToDefaultSCDetailDto.convert(source.getDefaultStorageCombine())
                        : null);
        return storageDto;
    }

}
