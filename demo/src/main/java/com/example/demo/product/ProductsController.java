package com.example.demo.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.product.converter.GetProductResponse;
import com.example.demo.product.converter.ProductToProductDetailDto;
import com.example.demo.product.converter.ProductToProductDto;
import com.example.demo.product.converter.ProductToSearchProductDtoLess;
import com.example.demo.product.dto.JsonProductDto;
import com.example.demo.product.dto.ProductDTO;
import com.example.demo.product.dto.ProductDetailDto;
import com.example.demo.product.dto.ProductResponse;
import com.example.demo.product.dto.SearchProductDto;
import com.example.demo.product.entity.Product;
import com.example.demo.system.MyResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {

   private final ProductService productService;

   private final ProductToProductDetailDto productToProductDetailDto;

   private final ProductToProductDto productToProductDto;

   private final ProductManagementService productManagementService;

   private final ProductToSearchProductDtoLess productToSearchProductDtoLess;

   public ProductsController(
         ProductService productService,
         ProductToProductDto productToProductDto,
         ProductManagementService productManagementService,
         ProductToSearchProductDtoLess productToSearchProductDtoLess,
         ProductToProductDetailDto productToProductDetailDto) {
      this.productService = productService;
      this.productToProductDetailDto = productToProductDetailDto;
      this.productManagementService = productManagementService;
      this.productToProductDto = productToProductDto;
      this.productToSearchProductDtoLess = productToSearchProductDtoLess;
   }

   @PostMapping("/search")
   public MyResponse findAllWithCriteria(
         @RequestBody ProductFilter filter,
         Pageable pageable) {

      Page<Product> productPage = this.productService.findAllByCriteria(pageable, filter);
      List<Product> products = productPage.getContent();

      List<ProductDTO> data = products.stream().map(p -> this.productToProductDto.convert(p)).toList();
      ProductResponse<ProductDTO> res = new GetProductResponse<ProductDTO>().create(productPage, pageable, filter,
            data);

      return new MyResponse(true, "Get all product successful", 200, res);
   }

   @GetMapping("/search")
   public MyResponse search(@RequestParam(name = "q", required = true) String q) {
      return this.productService.search(q);
   }

   @GetMapping("/search/less")
   public MyResponse searchLess(@RequestParam(name = "q", required = true) String q) {
      return this.productManagementService.searchLess(q);
   }

   @GetMapping("/{productId}")
   public MyResponse findOne(@PathVariable Long productId) {
      Product product = this.productService.findOne(productId);
      ProductDetailDto productDetailDto = this.productToProductDetailDto.convert(product);

      return new MyResponse(true, "Get one product successful", 200, productDetailDto);
   }

   @PostMapping("")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public MyResponse create(@Valid @RequestBody ProductDTO createProductDTO) {
      Product product = this.productService.create(createProductDTO);

      return new MyResponse(true, "Add product successful", HttpStatus.OK.value(), product);
   }

   @PutMapping("/{productId}")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public MyResponse update(
         @PathVariable Long productId,
         @RequestBody ProductDTO updateDto) {

      this.productService.update(productId, updateDto);

      return new MyResponse(true, "Update product successful", 200);
   }

   @DeleteMapping("/{productId}")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public MyResponse delete(
         @PathVariable Long productId) {

      this.productService.delete(productId);
      return new MyResponse(true, "Delete product successful", 200);

   }

   @PostMapping("/search/less")
   public MyResponse findAllLess(
         @RequestBody ProductFilter filter,
         Pageable pageable) {

      Page<Product> productPage = this.productService.findAllByCriteria(pageable, filter);
      List<Product> products = productPage.getContent();

      List<SearchProductDto> searchProductDtos = products.stream()
            .map(p -> this.productToSearchProductDtoLess.convert(p)).toList();
      ProductResponse<SearchProductDto> res = new GetProductResponse<SearchProductDto>().create(productPage, pageable,
            filter, searchProductDtos);

      return new MyResponse(true, "Get products successful", 200, res);

   }

   @PostMapping("/json-import")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public MyResponse importJson(@RequestBody JsonProductDto json) {

      Product newProduct = this.productManagementService.jsonImport(json);
      if (newProduct != null) {
         ProductDTO newProductDTO = this.productToProductDto.convert(newProduct);
         return new MyResponse(true, "Import product successful", 200, newProductDTO);
      }

      return new MyResponse(true, "Import product successful", 200);

   }
}
