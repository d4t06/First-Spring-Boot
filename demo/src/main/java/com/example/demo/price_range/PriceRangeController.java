package com.example.demo.price_range;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.price_range.converter.PriceRangeToPriceRangeDto;
import com.example.demo.price_range.dto.PriceRangeDto;
import com.example.demo.price_range.entity.PriceRange;
import com.example.demo.system.MyResponse;

@RestController
@RequestMapping("/price_ranges")
public class PriceRangeController {

    private final PriceRangeService priceRangeService;

    private final PriceRangeToPriceRangeDto priceRangeToPriceRangeDto;

    public PriceRangeController(
            PriceRangeToPriceRangeDto priceRangeToPriceRangeDto,
            PriceRangeService priceRangeService) {
        this.priceRangeService = priceRangeService;
        this.priceRangeToPriceRangeDto = priceRangeToPriceRangeDto;
    }

    @PostMapping("")
    public MyResponse create(
            @RequestBody PriceRangeDto priceRangeDto) {
        PriceRange priceRange = this.priceRangeService.create(priceRangeDto);
        PriceRangeDto newPriceRangeDto = this.priceRangeToPriceRangeDto.convert(priceRange);

        return new MyResponse(true, "add price range successful", 200, newPriceRangeDto);
    }

    @PutMapping("/{id}")
    public MyResponse update(
            @RequestBody PriceRangeDto priceRangeDto,
            @PathVariable Long id) {

        PriceRange priceRange = this.priceRangeService.update(priceRangeDto, id);
        PriceRangeDto newPriceRangeDto = this.priceRangeToPriceRangeDto.convert(priceRange);

        return new MyResponse(true, "update price range successful", 200, newPriceRangeDto);
    }

    @DeleteMapping("/{id}")
    public MyResponse delete(
            @PathVariable Long id) {

        this.priceRangeService.delete(id);
        
        return new MyResponse(true, "delete price range successful", 200);
    }

}
