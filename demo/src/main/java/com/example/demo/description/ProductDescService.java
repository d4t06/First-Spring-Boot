package com.example.demo.description;

import org.springframework.stereotype.Service;

import com.example.demo.description.converter.ProductDescDtoToProductDesc;
import com.example.demo.description.converter.ProductDescToProductDescDto;
import com.example.demo.description.dto.ProductDescDto;
import com.example.demo.description.entity.ProductDesc;
import com.example.demo.system.MyResponse;
import com.example.demo.system.exception.ObjectNotFoundException;

@Service
public class ProductDescService {

    private final ProductDescRepository productDescRepository;
    private final ProductDescDtoToProductDesc productDescDtoToProductDesc;
    private final ProductDescToProductDescDto productDescToProductDescDto;

    public ProductDescService(ProductDescRepository productDescRepository,
            ProductDescDtoToProductDesc productDescDtoToProductDesc,
            ProductDescToProductDescDto productDescToProductDescDto) {
        this.productDescDtoToProductDesc = productDescDtoToProductDesc;
        this.productDescRepository = productDescRepository;
        this.productDescToProductDescDto = productDescToProductDescDto;
    }

    public ProductDescDto add(ProductDescDto detailDto) {
        ProductDesc desc = this.productDescDtoToProductDesc.convert(detailDto);
        ProductDesc newDesc = this.productDescRepository.save(desc);

        return this.productDescToProductDescDto.convert(newDesc);
    }

    public MyResponse update(ProductDescDto detailDto) {
        this.productDescRepository.findById(detailDto.product_ascii()).map(oldDetail -> {
            oldDetail.setContent(detailDto.content());
            ProductDesc desc = this.productDescRepository.save(oldDetail);

            return desc;
        }).orElseThrow(() -> new ObjectNotFoundException(""));

        return new MyResponse(true, "update product content successful", 200);
    }

}
