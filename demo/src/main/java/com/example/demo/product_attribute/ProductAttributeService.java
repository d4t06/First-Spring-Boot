package com.example.demo.product_attribute;

import org.springframework.stereotype.Service;

import com.example.demo.product_attribute.converter.ProductAttributeDtoToProductAttribute;
import com.example.demo.product_attribute.converter.ProductAttributeToProductAttributeDto;
import com.example.demo.product_attribute.dto.ProductAttributeDto;
import com.example.demo.product_attribute.entity.ProductAttribute;
import com.example.demo.system.MyResponse;
import com.example.demo.system.exception.ObjectNotFoundException;

@Service
public class ProductAttributeService {
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductAttributeDtoToProductAttribute productAttrDtoToProductAttr;
    private final ProductAttributeToProductAttributeDto productAttrToProductAttrDto;

    public ProductAttributeService(
            ProductAttributeRepository productAttributeRepository,
            ProductAttributeToProductAttributeDto productAttrToProductAttrDto,
            ProductAttributeDtoToProductAttribute productAttrDtoToProductAttr) {
        this.productAttributeRepository = productAttributeRepository;
        this.productAttrDtoToProductAttr = productAttrDtoToProductAttr;
        this.productAttrToProductAttrDto = productAttrToProductAttrDto;
    }

    public MyResponse create(ProductAttributeDto productAttributeDto) {
        ProductAttribute productAttribute = this.productAttrDtoToProductAttr.convert(productAttributeDto);

        ProductAttribute newProductAttribute = this.productAttributeRepository.save(productAttribute);

        return new MyResponse(true, "create product attribute successful", 200,
                this.productAttrToProductAttrDto.convert(newProductAttribute));
    }

    public MyResponse update(Long id, ProductAttributeDto productAttrDto) {

        ProductAttribute newProductAttribute = this.productAttributeRepository.findById(id)
                .map(oldAttr -> {
                    oldAttr.setValue(productAttrDto.value());

                    ProductAttribute updatedBrand = this.productAttributeRepository.save(oldAttr);
                    return updatedBrand;
                })
                .orElseThrow(() -> new ObjectNotFoundException("Brand not found"));

        return new MyResponse(true, "update product attribute successful", 200,
                this.productAttrToProductAttrDto.convert(newProductAttribute));
    }

}
