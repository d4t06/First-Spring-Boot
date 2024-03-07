package com.example.demo.brand;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.brand.dto.BrandDto;
import com.example.demo.brand.entity.Brand;
import com.example.demo.system.MyResponse;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
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
   public MyResponse create(@Valid @RequestBody BrandDto brandDto) {
      try {
         Brand brand = this.brandService.create(brandDto);
         return new MyResponse(true, "Add brand successful", 200, brand);
      } catch (Exception e) {
         return new MyResponse(false, e.getMessage(), 500);
      }
   }

   @PutMapping("/{id}")
   public MyResponse replace(
         @PathVariable Long id,
         @RequestBody BrandDto brandDto) {

      try {
         Brand brand = this.brandService.update(id, brandDto);
         return new MyResponse(true, "Update brand successful", 200, brand);
      } catch (Exception e) {
         return new MyResponse(false, e.getMessage(), 500);
      }
   }

   @DeleteMapping("/{id}")
   public MyResponse delete(
         @PathVariable Long id) {
      try {
         this.brandService.delete(id);
         return new MyResponse(true, "Delete brand successful", 200);
      } catch (Exception e) {
         return new MyResponse(false, e.getMessage(), null);
      }
   }

}
