package com.example.demo.default_storage.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.default_storage.dto.DefaultStorageDto;
import com.example.demo.default_storage.entity.DefaultStorage;
// import com.example.demo.storage.converter.StorageToStorageDetailDto;

@Component
public class DefaultStorageToDefaultStorageDto implements Converter<DefaultStorage, DefaultStorageDto> {


    // public DefaultStorageToDefaultStorageDto(StorageToStorageDetailDto storageToStorageDetailDto) {
    //     this.storageToStorageDetailDto = storageToStorageDetailDto;
    // }

    @Override
    public DefaultStorageDto convert(DefaultStorage source) {
        DefaultStorageDto dto = new DefaultStorageDto(
                source.getProductAscii(),
                source.getStorage_id());

        return dto;
    }

}
