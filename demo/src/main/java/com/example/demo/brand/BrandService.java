package com.example.demo.brand;

import org.springframework.stereotype.Service;
import com.example.demo.brand.converter.BrandDtoToBrand;
import com.example.demo.brand.dto.BrandDto;
import com.example.demo.brand.entity.Brand;
import com.example.demo.system.exception.ObjectNotFoundException;

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

   public Brand update(Long id, BrandDto brandDto) {

      return this.brandRepository.findById(id)
            .map(oldBrand -> {
              oldBrand.setBrand_ascii(brandDto.brand_ascii());
              oldBrand.setBrand_name(brandDto.brand_name());
              oldBrand.setCategory_id(brandDto.category_id());

               Brand updatedBrand = this.brandRepository.save(oldBrand);
               return updatedBrand;
            })
            .orElseThrow(() -> new ObjectNotFoundException("Brand not found"));
   }

   public void delete(Long id) {
      this.brandRepository.findById(id)
              .orElseThrow(() -> new ObjectNotFoundException("Brand not found"));

      this.brandRepository.deleteById(id);
  }

}
