package com.example.demo.price_range;

import org.springframework.stereotype.Service;

import com.example.demo.price_range.converter.PriceRangeDtoToPriceRange;
import com.example.demo.price_range.dto.PriceRangeDto;
import com.example.demo.price_range.entity.PriceRange;
import com.example.demo.system.exception.ObjectNotFoundException;

@Service
public class PriceRangeService {

    private final PriceRangeRepository priceRangeRepository;

    private final PriceRangeDtoToPriceRange priceRangeDtoToPriceRange;

    public PriceRangeService(
            PriceRangeDtoToPriceRange priceRangeDtoToPriceRange,
            PriceRangeRepository priceRangeRepository) {
        this.priceRangeRepository = priceRangeRepository;
        this.priceRangeDtoToPriceRange = priceRangeDtoToPriceRange;
    }

    public PriceRange create(PriceRangeDto priceRangeDto) {
        PriceRange priceRange = this.priceRangeDtoToPriceRange.convert(priceRangeDto);

        return this.priceRangeRepository.save(priceRange);
    }

    public PriceRange update(PriceRangeDto priceRangeDto, Long id) {
        PriceRange oldPriceRange = this.priceRangeRepository
                .findById(id).orElseThrow(() -> new ObjectNotFoundException(null));

        oldPriceRange.setLabel(priceRangeDto.label());
        oldPriceRange.setFrom_price(priceRangeDto.from_price());
        oldPriceRange.setTo_price(priceRangeDto.to_price());

        return this.priceRangeRepository.save(oldPriceRange);
    }

    public void delete(Long id) {
        PriceRange target = this.priceRangeRepository
                .findById(id).orElseThrow(() -> new ObjectNotFoundException(null));

        this.priceRangeRepository.deleteById(target.getId());
    }
}
