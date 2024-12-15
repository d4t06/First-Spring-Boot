package com.example.demo.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.category.converter.CategoryToCategoryDto;
import com.example.demo.category.converter.CategoryToCategoryDtoLess;
import com.example.demo.category.dto.CategoryDto;
import com.example.demo.category.dto.CategoryDtoLess;
import com.example.demo.category.dto.JsonCategoryDto;
import com.example.demo.category.entity.Category;
import com.example.demo.category_attribute.CategoryAttributeService;
import com.example.demo.category_attribute.dto.CategoryAttributeDto;
import com.example.demo.system.MyResponse;

@RestController
@RequestMapping("/categories")
public class CategoryController {

   private final CategoryService categoryService;
   private final CategoryToCategoryDto categoryToCategoryDto;
   private final CategoryAttributeService categoryAttributeService;
   private final CategoryToCategoryDtoLess categoryToCategoryDtoLess;

   public CategoryController(
         CategoryService categoryService,
         CategoryAttributeService categoryAttributeService,
         CategoryToCategoryDtoLess categoryToCategoryDtoLess,
         CategoryToCategoryDto categoryToCategoryDto) {

      this.categoryService = categoryService;
      this.categoryToCategoryDto = categoryToCategoryDto;
      this.categoryToCategoryDtoLess = categoryToCategoryDtoLess;
      this.categoryAttributeService = categoryAttributeService;
   }

   @GetMapping()
   public MyResponse findAll() {
      List<Category> categories = this.categoryService.findAll();

      List<CategoryDto> categoryDtos = new ArrayList<>();
      for (Category category : categories) {
         CategoryDto categoryDto = this.categoryToCategoryDto.convert(category);
         categoryDtos.add(categoryDto);
      }
      return new MyResponse(true, "Get all category successful", 200, categoryDtos);
   }

   @GetMapping("less")
   public MyResponse findAllLess() {
      List<Category> categories = this.categoryService.findAll();
      List<CategoryDtoLess> data = new ArrayList<CategoryDtoLess>();

      for (Category cat : categories) {
         if (cat.getIs_show() != 0) {
            data.add(this.categoryToCategoryDtoLess.convert(cat));
         }
      }

      return new MyResponse(true, "Get all category successful", 200, data);
   }

   @GetMapping("/{id}")
   public MyResponse find(@PathVariable Long id) {
      Category category = this.categoryService.findOne(id);

      return new MyResponse(true, "Get category successful", 200, category);
   }

   @PostMapping("")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public MyResponse create(@RequestBody CategoryDto dto) {
      Category category = this.categoryService.create(dto);
      CategoryDto categoryDto = this.categoryToCategoryDto.convert(category);

      return new MyResponse(true, "Add successful", 200, categoryDto);
   }

   @PutMapping("/{id}")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public MyResponse update(
         @RequestBody CategoryDto updateDto,
         @PathVariable Long id) {

      Category category = this.categoryService.update(id, updateDto);

      return new MyResponse(true, "Update successful", 200, category);
   }

   @DeleteMapping("/{id}")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public MyResponse delete(
         @PathVariable Long id) {

      this.categoryService.delete(id);
      return new MyResponse(true, "Delete successful", 200);
   }

   // >>>>>>>>>>>>>>>
   @PostMapping("/attributes")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public MyResponse addAttribute(@RequestBody CategoryAttributeDto body) {
      return this.categoryAttributeService.create(body);
   }

   @PutMapping("/attributes/{id}")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public MyResponse updateAttribute(@PathVariable Long id, @RequestBody CategoryAttributeDto body) {
      return this.categoryAttributeService.update(id, body);
   }

   @DeleteMapping("/attributes/{id}")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public MyResponse deleteAttribute(@PathVariable Long id) {
      return this.categoryAttributeService.delete(id);
   }

   @PostMapping("/json-import")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public MyResponse json(@RequestBody List<JsonCategoryDto> dto) {
      List<Category> categories = this.categoryService.jsonImport(dto);

      List<CategoryDto> data = new ArrayList<CategoryDto>();

      categories.stream().forEach(c -> {
         if (c != null)
            data.add(this.categoryToCategoryDto.convert(c));
      });

      return new MyResponse(true, "Import successful", 200, data);
   }
}