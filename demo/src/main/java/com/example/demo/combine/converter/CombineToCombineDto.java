package com.example.demo.combine.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.combine.dto.CombineDto;
import com.example.demo.combine.entity.Combine;

@Component
public class CombineToCombineDto implements Converter<Combine, CombineDto> {

    @Override
    public CombineDto convert(Combine source) {
        CombineDto combineDto = new CombineDto(
                source.getId(),
                source.getColor_id(),
                source.getStorage_id(),
                source.getProductId(),
                source.getPrice(),
                source.getQuantity());
        return combineDto;
    }

}
