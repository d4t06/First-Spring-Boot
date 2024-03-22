package com.example.demo.product;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public ProductResponse findAll(
            int page,
            int pageSize,
            Integer categoryID,
            List<String> brandID,
            String column,
            String type,
            List<String> price) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Product> productPage;

        if (categoryID == null)
            productPage = this.productRepository.findAll(pageable);
        else if (brandID != null)
            productPage = this.productRepository.findAllWithCategoryAndBrand(pageable, categoryID, brandID);
        else
            productPage = this.productRepository.findAllWithCategory(pageable, categoryID);

        List<Product> listOfProducts = productPage.getContent();

        List<ProductDTO> productsDTO = new ArrayList<>();
        for (Product product : listOfProducts) {
            ProductDTO productDTO = this.productToProductDto.convert(product);
            productsDTO.add(productDTO);
        }

        ProductResponse res = new ProductResponse();

        res.setProducts(productsDTO);
        res.setPage(page);
        res.setPageSize(pageSize);
        res.setCount(productPage.getTotalElements());

        res.setBrandID(brandID);
        res.setCategoryID(categoryID);
        res.setIsLast(productPage.isLast());
        res.setColumn(column);
        res.setType(type);
        res.setPrice(price);

        return res;

    }

    public ProductResponse search(String keyword,
            int page,
            int pageSize,
            String column,
            String type) {
        Pageable pageable = PageRequest.of(page, 2);
        Page<Product> productPage = this.productRepository.findByKeyword(pageable, keyword);

        List<Product> products = productPage.getContent();

        List<ProductDTO> productsDTO = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = this.productToProductDto.convert(product);
            productsDTO.add(productDTO);
        }

        ProductResponse res = new ProductResponse();
        res.setProducts(productsDTO);
        res.setPage(page);
        res.setPageSize(pageSize);
        res.setCount(productPage.getTotalElements());

        res.setBrandID(new ArrayList<>());
        res.setCategoryID(null);
        res.setIsLast(productPage.isLast());
        res.setColumn(column);
        res.setType(type);

        return res;

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
