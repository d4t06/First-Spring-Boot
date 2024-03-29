package com.example.demo.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.product.converter.ProductDtoToProduct;
import com.example.demo.product.converter.ProductToProductDto;
import com.example.demo.product.dto.ProductDTO;
import com.example.demo.product.dto.ProductResponse;
import com.example.demo.product.entity.Product;
import com.example.demo.system.exception.ObjectNotFoundException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductDtoToProduct productDtoToProduct;

    private final ProductToProductDto productToProductDto;

    public ProductService(
            ProductToProductDto productToProductDto,
            ProductRepository productRepository,
            ProductDtoToProduct productDtoToProduct) {
        this.productRepository = productRepository;
        this.productDtoToProduct = productDtoToProduct;
        this.productToProductDto = productToProductDto;
    }

    public ProductResponse findAllByCriteria(
            Pageable pageable,
            ProductFilter filter) {

        Specification<Product> spec = Specification.where(null);
        ProductResponse res = new ProductResponse();

        if (StringUtils.hasLength(filter.category_id())) {
            spec = spec.and(ProductSpecs.hasCategoryID(filter.category_id()));
        }

        if (filter.brand_id() != null) {
            spec = spec.and(ProductSpecs.hasBrandIDs(filter.brand_id()));
        }

        if (filter.price() != null) {
            spec = spec.and(ProductSpecs.betweenPrice(Integer.parseInt(filter.price().get(0)) * (int)Math.pow(10, 6),
                    Integer.parseInt(filter.price().get(1)) * (int)Math.pow(10, 6)));
        }

        Page<Product> productPage = this.productRepository.findAll(spec, pageable);

        List<Product> products = productPage.getContent();

        List<ProductDTO> productsDTO = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = this.productToProductDto.convert(product);
            productsDTO.add(productDTO);
        }

        res.setProducts(productsDTO);
        res.setPage(pageable.getPageNumber());
        res.setSize(2);
        res.setCount(productPage.getTotalElements());

        res.setBrand_id(filter.brand_id());
        res.setCategory_id(filter.category_id());
        res.setIs_last(productPage.isLast());
        res.setPrice(filter.price());

        if (pageable.getSort().isSorted()) {
            Order order = pageable.getSort().toList().get(0);

            res.setColumn(order.getProperty());
            res.setType(order.getDirection().name());
        }

        return res;
    }

    public List<ProductDTO> search(String key) {

        Specification<Product> spec = Specification.where(ProductSpecs.containName(key));

        List<Product> products = this.productRepository.findAll(spec);
        List<ProductDTO> productDTOs = products.stream().map(p -> this.productToProductDto.convert(p)).toList();

        return productDTOs;
    }

    public Product findOne(String product_ascii) {
        List<Product> products = this.productRepository.findByProductAscii(product_ascii);

        if (products.size() != 1)
            throw new ObjectNotFoundException("Product not found");
        return products.get(0);

    }

    public Product create(ProductDTO createProductDTO) {
        Product product = this.productDtoToProduct.convert(createProductDTO);
        return this.productRepository.save(product);
    }

    public Product update(Long id, ProductDTO updateDto) {
        return this.productRepository.findById(id)
                .map(oldProduct -> {
                    oldProduct.setBrandId(updateDto.brand_id());
                    oldProduct.setCategoryId(updateDto.category_id());
                    oldProduct.setProductAscii(updateDto.product_ascii());
                    oldProduct.setProduct_name(updateDto.product_name());
                    oldProduct.setImage_url(updateDto.image_url());
                    oldProduct.setPrice(updateDto.price());

                    Product product = this.productRepository.save(oldProduct);
                    return product;
                })
                .orElseThrow(() -> new ObjectNotFoundException("Product not found"));

    }

    public void delete(Long id) {
        this.productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product not found"));

        this.productRepository.deleteById(id);
    }

}
