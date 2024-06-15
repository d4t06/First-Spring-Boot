package com.example.demo.category_attribute;

import org.springframework.stereotype.Service;

import com.example.demo.category_attribute.converter.CategoryAttDToCategoryAtt;
import com.example.demo.category_attribute.converter.CategoryAttToCategoryAttDto;
import com.example.demo.category_attribute.dto.CategoryAttributeDto;
import com.example.demo.category_attribute.entity.CategoryAttribute;
import com.example.demo.system.MyResponse;
import com.example.demo.system.exception.ObjectNotFoundException;

@Service
public class CategoryAttributeService {

    private final CategoryAttributerRepository categoryAttributerRepository;

    private final CategoryAttDToCategoryAtt categoryAttDToCategoryAtt;;
    private final CategoryAttToCategoryAttDto categoryAttToCategoryAttDto;

    public CategoryAttributeService(
            CategoryAttributerRepository categoryAttributerRepository,
            CategoryAttToCategoryAttDto categoryAttToCategoryAttDto,
            CategoryAttDToCategoryAtt categoryAttDToCategoryAtt) {
        this.categoryAttributerRepository = categoryAttributerRepository;
        this.categoryAttDToCategoryAtt = categoryAttDToCategoryAtt;
        this.categoryAttToCategoryAttDto = categoryAttToCategoryAttDto;
    }

    public MyResponse create(CategoryAttributeDto categoryAttributeDto) {
        CategoryAttribute categoryAttribute = this.categoryAttDToCategoryAtt.convert(categoryAttributeDto);

        CategoryAttribute newCategoryAttribute = this.categoryAttributerRepository.save(categoryAttribute);
        CategoryAttributeDto neCategoryAttributeDto = this.categoryAttToCategoryAttDto.convert(newCategoryAttribute);

        return new MyResponse(true, "create category attribute successful", 200, neCategoryAttributeDto);
    }

    public MyResponse update(Long id, CategoryAttributeDto categoryAttributeDto) {

        CategoryAttribute updated = this.categoryAttributerRepository.findById(id)
                .map(oldCategoryAtt -> {
                    oldCategoryAtt.setAttribute(categoryAttributeDto.attribute());
                    oldCategoryAtt.setAttribute_ascii(categoryAttributeDto.attribute_ascii());
                    oldCategoryAtt.setCategory_id(categoryAttributeDto.category_id());

                    CategoryAttribute updatedCategoryAttribute = this.categoryAttributerRepository.save(oldCategoryAtt);
                    return updatedCategoryAttribute;
                })
                .orElseThrow(() -> new ObjectNotFoundException("Category attribute not found"));

        return new MyResponse(true, "update category attribute successful", 200,
                this.categoryAttToCategoryAttDto.convert(updated));
    }

    public MyResponse delete(Long id) {
        this.categoryAttributerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Brand not found"));

        this.categoryAttributerRepository.deleteById(id);

        return new MyResponse(true, "delete category attribute successful", 200);
    }

}
