package com.example.demo.category;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.category.converter.CategoryDtoToCategory;
import com.example.demo.category.dto.CategoryDto;
import com.example.demo.category.entity.Category;
import com.example.demo.system.exception.ObjectNotFoundException;

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
        List<Category> categories = this.categoryRepository.findAll();
        // if (categories.isEmpty())
        //     throw new ObjectNotFoundException("Category not found");

        return categories;
    }

    public Category findOne(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Category not found"));

    }

    public Category create(CategoryDto createDto) {
        Category category = this.categoryDtoToCategory.convert(createDto);

        return this.categoryRepository.save(category);
    }

    public Category update(Long id, CategoryDto updateDto) {
        return this.categoryRepository.findById(id)
                .map(oldCategory -> {
                    oldCategory.setCategory_ascii(updateDto.category_ascii());
                    oldCategory.setCategory_name(updateDto.category_name());

                    Category updatedCategory = this.categoryRepository.save(oldCategory);
                    return updatedCategory;
                })
                .orElseThrow(() -> new ObjectNotFoundException("Category not found"));
    }

    public void delete(Long id) {
        this.categoryRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Category not found"));

        this.categoryRepository.deleteById(id);
    }

}
