package com.example.demo.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.brand.BrandRepository;
import com.example.demo.brand.entity.Brand;
import com.example.demo.category.converter.CategoryDtoToCategory;
import com.example.demo.category.converter.CategorySliderDtoToCategorySlider;
import com.example.demo.category.dto.CategoryDto;
import com.example.demo.category.dto.CategorySliderDto;
import com.example.demo.category.dto.JsonCategoryDto;
import com.example.demo.category.entity.Category;
import com.example.demo.category.entity.CategorySlider;
import com.example.demo.category_attribute.CategoryAttributerRepository;
import com.example.demo.category_attribute.entity.CategoryAttribute;
import com.example.demo.slider.entity.Slider;
import com.example.demo.slider.repository.SliderRepository;
import com.example.demo.system.ConvertEng;
import com.example.demo.system.exception.ObjectNotFoundException;

@Service
@Transactional
public class CategoryService {

   private final CategoryRepository categoryRepository;

   private final CategorySliderRepository categorySliderRepository;

   private final SliderRepository sliderRepository;

   private final CategoryAttributerRepository categoryAttributerRepository;

   private final CategoryDtoToCategory categoryDtoToCategory;

   private final BrandRepository brandRepository;

   private final CategorySliderDtoToCategorySlider categorySliderDtoToCategorySlider;

   public CategoryService(
         CategoryRepository categoryRepository,
         CategorySliderRepository categorySliderRepository,
         CategoryDtoToCategory categoryDtoToCategory,
         SliderRepository sliderRepository,
         BrandRepository brandRepository,
         CategoryAttributerRepository categoryAttributerRepository,
         CategorySliderDtoToCategorySlider categorySliderDtoToCategorySlider) {

      this.categoryRepository = categoryRepository;
      this.categorySliderRepository = categorySliderRepository;
      this.categoryDtoToCategory = categoryDtoToCategory;
      this.categorySliderDtoToCategorySlider = categorySliderDtoToCategorySlider;
      this.sliderRepository = sliderRepository;
      this.categoryAttributerRepository = categoryAttributerRepository;
      this.brandRepository = brandRepository;
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
               oldCategory.setCategory_name_ascii(updateDto.category_name_ascii());
               oldCategory.setCategory_name(updateDto.category_name());
               oldCategory.setAttribute_order(updateDto.attribute_order());

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

   public List<Category> jsonImport(List<JsonCategoryDto> json) {

      ConvertEng convertEng = new ConvertEng();

      List<Category> newCategories = json.stream().map(catJson -> {
         CategoryDto dto = new CategoryDto(null, convertEng.convert(catJson.name()), catJson.name(), "", 1,
               new ArrayList<>(),
               new ArrayList<>(), new ArrayList<>(), null);
         Category newCategory = this.create(dto);

         // save attribute
         List<CategoryAttribute> attributes = catJson.attributes().stream().map(att -> {

            CategoryAttribute entityCategoryAttribute = new CategoryAttribute();
            entityCategoryAttribute.setAttribute_name(att);
            entityCategoryAttribute.setAttribute_name_ascii(convertEng.convert(att));
            entityCategoryAttribute.setCategory_id(newCategory.getId());

            CategoryAttribute newCategoryAttribute = this.categoryAttributerRepository.save(entityCategoryAttribute);

            return newCategoryAttribute;

         }).toList();



         // update category attribute order
         String attributeOrder = String.join("_", attributes.stream().map(att -> att.getId().toString()).toList());
         newCategory.setAttribute_order(attributeOrder);
         this.categoryRepository.save(newCategory);
         

         // save brand
         List<Brand> brands = catJson.brands().stream().map(b -> {

            Brand entity = new Brand();
            entity.setBrand_name(b);
            entity.setBrand_name_ascii(convertEng.convert(b));
            entity.setCategory_id(newCategory.getId());

            Brand newBrand = this.brandRepository.save(entity);

            return newBrand;

         }).toList();

         newCategory.setCategoryAttributes(attributes);
         newCategory.setBrands(brands);

         return newCategory;
      }).toList();

      return newCategories;
   }
}
