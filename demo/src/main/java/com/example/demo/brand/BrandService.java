package com.example.demo.brand;

import org.springframework.stereotype.Service;

import com.example.demo.brand.converter.BrandDtoToBrand;
import com.example.demo.brand.dto.BrandDto;
import com.example.demo.brand.dto.UpdateBrandDto;
import com.example.demo.brand.entity.Brand;

@Service
public class BrandService {

   private final BrandRepository brandRepository;

   private final BrandDtoToBrand brandDtoToBrand;

   public BrandService(
         BrandRepository brandRepository,
         BrandDtoToBrand brandDtoToBrand) {
      this.brandRepository = brandRepository;
      this.brandDtoToBrand = brandDtoToBrand;
   }

   public Brand create(BrandDto brandDto) {
      Brand brand = this.brandDtoToBrand.convert(brandDto);

      return this.brandRepository.save(brand);
   }

   public Brand update(String brand_ascii, BrandDto brandDto) {
      Brand newBrand = this.brandDtoToBrand.convert(brandDto);
      Brand oldBrand = this.brandRepository.findByBrandAscii(brand_ascii).get(0);

      oldBrand.setBrand_ascii(newBrand.getBrand_ascii());
      oldBrand.setBrand_name(newBrand.getBrand_name());
      oldBrand.setCategoryAscii(newBrand.getCategoryAscii());

      return this.brandRepository.save(oldBrand);
   }

}
