package com.example.demo.category;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.category.converter.CategoryDtoToCategory;
import com.example.demo.category.converter.CategorySliderDtoToCategorySlider;
import com.example.demo.category.dto.CategoryDto;
import com.example.demo.category.dto.CategorySliderDto;
import com.example.demo.category.entity.Category;
import com.example.demo.category.entity.CategorySlider;
import com.example.demo.slider.entity.Slider;
import com.example.demo.slider.repository.SliderRepository;
import com.example.demo.system.exception.ObjectNotFoundException;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategorySliderRepository categorySliderRepository;

    private final SliderRepository sliderRepository;

    private final CategoryDtoToCategory categoryDtoToCategory;

    private final CategorySliderDtoToCategorySlider categorySliderDtoToCategorySlider;

    public CategoryService(
            CategoryRepository categoryRepository,
            CategorySliderRepository categorySliderRepository,
            CategoryDtoToCategory categoryDtoToCategory,
            SliderRepository sliderRepository,
            CategorySliderDtoToCategorySlider categorySliderDtoToCategorySlider) {

        this.categoryRepository = categoryRepository;
        this.categorySliderRepository = categorySliderRepository;
        this.categoryDtoToCategory = categoryDtoToCategory;
        this.categorySliderDtoToCategorySlider = categorySliderDtoToCategorySlider;
        this.sliderRepository = sliderRepository;
    }

    public List<Category> findAll() {
        List<Category> categories = this.categoryRepository.findAll();
        // if (categories.isEmpty())
        // throw new ObjectNotFoundException("Category not found");

        return categories;
    }

    public Category findOne(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Category not found"));

    }

    // add category
    // add slider
    // add category slider
    public Category create(CategoryDto createDto) {
        Category category = this.categoryDtoToCategory.convert(createDto);

        Category newCategory = this.categoryRepository.save(category);

        Slider slider = new Slider();
        slider.setName("slider for " + createDto.category_name());
        Slider newSlider = this.sliderRepository.save(slider);

        CategorySlider categorySlider = new CategorySlider();
        categorySlider.setCategory_id(category.getId());
        categorySlider.setSlider_id(newSlider.getId());

        CategorySlider newCategorySlider = this.categorySliderRepository.save(categorySlider);

        newCategorySlider.setSlider(newSlider);
        newCategory.setCategorySlider(newCategorySlider);

        return newCategory;
    }

    public void createCategorySlider(CategorySliderDto categorySliderDto) {
        CategorySlider categorySlider = this.categorySliderDtoToCategorySlider.convert(categorySliderDto);

        this.categorySliderRepository.save(categorySlider);
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
