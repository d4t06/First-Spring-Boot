package com.example.demo.price_range.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.price_range.dto.PriceRangeDto;
import com.example.demo.price_range.entity.PriceRange;

@Component
public class PriceRangeToPriceRangeDto implements Converter<PriceRange, PriceRangeDto> {

    @Override
    public PriceRangeDto convert(PriceRange source) {
        PriceRangeDto priceRangeDto = new PriceRangeDto(
                source.getId(),
                source.getCategory_id(),
                source.getFrom_price(),
                source.getTo_price(),
                source.getLabel());

        return priceRangeDto;
    }

}
