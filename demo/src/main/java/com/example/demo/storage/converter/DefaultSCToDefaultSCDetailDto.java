package com.example.demo.storage.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.combine.converter.CombineToCombineDto;
import com.example.demo.storage.dto.DefaultStorageCombineDto;
import com.example.demo.storage.entity.DefaultStorageCombine;

@Component
public class DefaultSCToDefaultSCDetailDto implements Converter<DefaultStorageCombine, DefaultStorageCombineDto> {

    private final CombineToCombineDto combineToCombineDto;

    public DefaultSCToDefaultSCDetailDto(CombineToCombineDto combineToCombineDto) {
        this.combineToCombineDto = combineToCombineDto;
    }

    @Override
    public DefaultStorageCombineDto convert(DefaultStorageCombine source) {
        DefaultStorageCombineDto data = new DefaultStorageCombineDto(
                source.getStorageId(),
                source.getCombine_id(),
                source.getCombine() == null
                        ? null
                        : this.combineToCombineDto.convert(source.getCombine()));

        return data;
    }

}
