package com.example.demo.brand;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.brand.dto.BrandDto;
import com.example.demo.brand.entity.Brand;
import com.example.demo.system.MyResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController()
@RequestMapping("/brands")
public class BrandController {

   private final BrandService brandService;

   public BrandController(BrandService brandService) {
      this.brandService = brandService;
   }


   @PostMapping("")
   public MyResponse create(@RequestBody BrandDto brandDto) {

      try {         
         Brand brand = this.brandService.create(brandDto);
         return new MyResponse(true, "Add brand successful", 200, brand);
      } catch (Exception e) {
         // TODO: handle exception
         return new MyResponse(false, e.getMessage(), 400);
      }
   }

   @PutMapping("/{brand_ascii}")
   public MyResponse replace(
      @PathVariable String brand_ascii,
      @RequestBody BrandDto brandDto) {

      try {         
         Brand brand = this.brandService.update(brand_ascii, brandDto);
         return new MyResponse(true, "Add brand successful", 200, brand);
      } catch (Exception e) {
         // TODO: handle exception
         return new MyResponse(false, e.getMessage(), 400);
      }
   }
   

}
