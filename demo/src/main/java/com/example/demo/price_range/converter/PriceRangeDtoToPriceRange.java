package com.example.demo.price_range.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.price_range.dto.PriceRangeDto;
import com.example.demo.price_range.entity.PriceRange;


@Component
public class PriceRangeDtoToPriceRange implements Converter<PriceRangeDto, PriceRange> {

    @Override
    public PriceRange convert(PriceRangeDto source) {

        PriceRange priceRange = new PriceRange();
        priceRange.setFrom_price(source.from_price());
        priceRange.setTo_price(source.to_price());
        priceRange.setLabel(source.label());
        priceRange.setCategory_id(source.category_id());

        return priceRange;
    }

}
