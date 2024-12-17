package com.example.demo.default_storage.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.default_storage.dto.DefaultStorageDto;
import com.example.demo.default_storage.entity.DefaultStorage;

@Component
public class DefaultStorageToDefaultStorageDto implements Converter<DefaultStorage, DefaultStorageDto> {

    @Override
    public DefaultStorageDto convert(DefaultStorage source) {
        DefaultStorageDto dto = new DefaultStorageDto(
                source.getProductId(),
                source.getStorage_id(),
                null);

        return dto;
    }

}
