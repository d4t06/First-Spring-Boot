package com.example.demo.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.default_storage.DefaultStorageRepository;
import com.example.demo.default_storage.entity.DefaultStorage;
import com.example.demo.description.ProductDescService;
import com.example.demo.description.dto.ProductDescDto;
import com.example.demo.product.converter.ProductDtoToProduct;
import com.example.demo.product.converter.ProductToSearchProductDto;
import com.example.demo.product.converter.ProductToSearchProductDtoLess;
import com.example.demo.product.dto.ProductDTO;
import com.example.demo.product.dto.SearchProductDto;
import com.example.demo.product.entity.Product;
import com.example.demo.system.MyResponse;
import com.example.demo.system.exception.ObjectNotFoundException;

@Service
public class ProductService {

   private final ProductRepository productRepository;

   private final ProductDtoToProduct productDtoToProduct;

   private final ProductToSearchProductDto productToSearchProductDto;

   private final ProductDescService productDescService;

   private final DefaultStorageRepository defaultStorageRepository;

   public ProductService(
         ProductRepository productRepository,
         ProductDescService productDescService,
         ProductToSearchProductDto productToSearchProductDto,
         DefaultStorageRepository defaultStorageRepository,
         ProductToSearchProductDtoLess productToSearchProductDtoLess,
         ProductDtoToProduct productDtoToProduct) {
      this.productRepository = productRepository;
      this.productDtoToProduct = productDtoToProduct;
      this.productDescService = productDescService;
      this.defaultStorageRepository = defaultStorageRepository;
      this.productToSearchProductDto = productToSearchProductDto;
   }

   public Page<Product> findAllByCriteria(
         Pageable pageable,
         ProductFilter filter) {

      Specification<Product> spec = Specification.where(null);

      if (StringUtils.hasLength(filter.category_id())) {
         spec = spec.and(ProductSpecs.hasCategoryID(filter.category_id()));
      }

      if (filter.brand_id() != null) {
         spec = spec.and(ProductSpecs.hasBrandIDs(filter.brand_id()));
      }

      if (filter.price() != null) {
         spec = spec.and(ProductSpecs.betweenPrice(Integer.parseInt(filter.price().get(0)) * (int) Math.pow(10, 6),
               Integer.parseInt(filter.price().get(1)) * (int) Math.pow(10, 6)));
      }

      Sort sort = pageable.getSort().isSorted() ? Sort.by(
            pageable.getSort().get()
                  .map(order -> order.getProperty().equals("price")
                        ? Sort.Order
                              .by("defaultStorage.storage.defaultStorageCombine.combine.price")
                              .with(order.getDirection())
                        : order)
                  .collect(Collectors.toList()))
            : Sort.by(Sort.Direction.DESC, "id");

      Page<Product> productPage = this.productRepository.findAll(spec, PageRequest.of(
            pageable.getPageNumber(), pageable.getPageSize(), sort));

      return productPage;

   }

   public MyResponse search(String key) {

      Specification<Product> spec = Specification.where(ProductSpecs.containName(key));

      List<Product> products = this.productRepository.findAll(spec);
      List<SearchProductDto> dto = products.stream().map(p -> this.productToSearchProductDto.convert(p)).toList();

      return new MyResponse(true, "search product successful", 200, dto);

   }

   public Product findOne(Long product_id) {
      return this.productRepository.findById(product_id).orElseThrow(
            () -> new ObjectNotFoundException(""));

   }

   public Product create(ProductDTO createProductDTO) {
      Product product = this.productDtoToProduct.convert(createProductDTO);

      Product newProduct = this.productRepository.save(product);

      // add description
      ProductDescDto descDto = new ProductDescDto(newProduct.getId(), newProduct.getProduct_name());
      this.productDescService.add(descDto);

      // add default storage
      DefaultStorage defaultStorage = new DefaultStorage();
      defaultStorage.setProductId(newProduct.getId());
      this.defaultStorageRepository.save(defaultStorage);

      return newProduct;
   }

   public Product update(Long productId, ProductDTO updateDto) {
      return this.productRepository.findById(productId)
            .map(oldProduct -> {
               oldProduct.setBrandId(updateDto.brand_id());
               oldProduct.setCategoryId(updateDto.category_id());
               oldProduct.setProduct_name(updateDto.product_name());
               oldProduct.setImage_url(updateDto.image_url());

               Product product = this.productRepository.save(oldProduct);
               return product;
            })
            .orElseThrow(() -> new ObjectNotFoundException("Product not found"));

   }

   public void delete(Long productId) {

      this.productRepository.findById(productId)
            .orElseThrow(() -> new ObjectNotFoundException("Product not found"));

      this.productRepository.deleteById(productId);
   }

}
