package com.example.demo.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.category.converter.CategoryDtoToCategory;
import com.example.demo.category.converter.CategoryToCategoryDto;
import com.example.demo.category.dto.CategoryDto;
import com.example.demo.category.entity.Category;
import com.example.demo.system.MyResponse;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryToCategoryDto categoryToCategoryDto;

    public CategoryController(
            CategoryService categoryService,
            CategoryToCategoryDto categoryToCategoryDto) {

        this.categoryService = categoryService;
        this.categoryToCategoryDto = categoryToCategoryDto;
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

    @GetMapping("/{id}")
    public MyResponse find(@PathVariable Long id) {
        Category category = this.categoryService.findOne(id);

        return new MyResponse(true, "Get category successful", 200, category);
    }

    @PostMapping()
    public MyResponse create(@RequestBody CategoryDto dto) {
        Category category = this.categoryService.create(dto);

        return new MyResponse(true, "Add successful", 200, category);
    }

    @PutMapping()
    public MyResponse update(
            @RequestBody CategoryDto updateDto,
            @PathVariable Long id) {

        Category category = this.categoryService.update(id, updateDto);

        return new MyResponse(true, "Update successful", 200, category);
    }

    @DeleteMapping("/{id}")
    public MyResponse delete(
            @PathVariable Long id) {

        this.categoryService.delete(id);
        return new MyResponse(true, "Delete successful", 200);
    }
}
