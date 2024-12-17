package com.example.demo.brand;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.brand.dto.BrandDto;
import com.example.demo.brand.entity.Brand;
import com.example.demo.system.MyResponse;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController()
@RequestMapping("/brands")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class BrandController {

   private final BrandService brandService;

   public BrandController(BrandService brandService) {
      this.brandService = brandService;
   }

   @PostMapping("")
   public MyResponse create(@Valid @RequestBody BrandDto brandDto) {
      Brand brand = this.brandService.create(brandDto);
      return new MyResponse(true, "Add brand successful", 200, brand);
   }

   @PutMapping("/{id}")
   public MyResponse replace(
         @PathVariable Long id,
         @RequestBody BrandDto brandDto) {

      Brand brand = this.brandService.update(id, brandDto);
      return new MyResponse(true, "Update brand successful", 200, brand);

   }

   @DeleteMapping("/{id}")
   public MyResponse delete(
         @PathVariable Long id) {
      this.brandService.delete(id);
      return new MyResponse(true, "Delete brand successful", 200);

   }

}
