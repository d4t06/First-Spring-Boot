package com.example.demo.storage.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.storage.dto.DefaultStorageCombineDto;
import com.example.demo.storage.entity.DefaultStorageCombine;

@Component
public class DefaultSCToDefaultSCDto implements Converter<DefaultStorageCombine, DefaultStorageCombineDto> {

    @Override
    public DefaultStorageCombineDto convert(DefaultStorageCombine source) {
        DefaultStorageCombineDto data = new DefaultStorageCombineDto(
                source.getStorageId(),
                source.getCombine_id(),
                null);

        return data;
    }

}
