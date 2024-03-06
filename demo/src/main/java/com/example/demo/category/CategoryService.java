package com.example.demo.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.category.converter.CategoryDtoToCategory;
import com.example.demo.category.dto.CategoryDto;
import com.example.demo.category.entity.Category;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDtoToCategory categoryDtoToCategory;

    public CategoryService(
            CategoryRepository categoryRepository,
            CategoryDtoToCategory categoryDtoToCategory) {

        this.categoryRepository = categoryRepository;
        this.categoryDtoToCategory = categoryDtoToCategory;
    }

    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    public ResponseEntity<?> findOne(String category_ascii) {
        return new ResponseEntity<>("Get category " + category_ascii, HttpStatus.OK);
    }

    public Category create(CategoryDto createDto) {
        Category category = this.categoryDtoToCategory.convert(createDto);
        return this.categoryRepository.save(category);
    }

}
