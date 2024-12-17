package com.example.demo.combine.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.combine.dto.CombineDto;
import com.example.demo.combine.entity.Combine;

@Component
public class CombineDtoToCombine implements Converter<CombineDto, Combine> {

    @Override
    public Combine convert(CombineDto source) {
        Combine combine = new Combine();

        combine.setColor_id(source.color_id());
        combine.setStorage_id(source.storage_id());
        combine.setProductId(source.product_id());
        combine.setPrice(source.price());
        combine.setQuantity(source.quantity());

        return combine;
    }

}
