package com.example.demo.product;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.product.converter.ProductToProductDetailDto;
import com.example.demo.product.converter.ProductToProductDto;
import com.example.demo.product.dto.JsonProductDto;
import com.example.demo.product.dto.ProductDTO;
import com.example.demo.product.dto.ProductDetailDto;
import com.example.demo.product.dto.ProductResponse;
import com.example.demo.product.entity.Product;
import com.example.demo.system.MyResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/products")
public class ProductsController {

   private final ProductService productService;

   private final ProductToProductDetailDto productToProductDetailDto;

   private final ProductToProductDto productToProductDto;

   private final ImportProductService importProductService;

   public ProductsController(
         ProductService productService,
         ProductToProductDto productToProductDto,
         ImportProductService importProductService,
         ProductToProductDetailDto productToProductDetailDto) {
      this.productService = productService;
      // this.productToProductDto = productToProductDto;
      this.productToProductDetailDto = productToProductDetailDto;
      this.importProductService = importProductService;
      this.productToProductDto = productToProductDto;
   }

   @PostMapping("/search")
   public MyResponse findAllWithCriteria(
         @RequestBody ProductFilter filter,
         Pageable pageable) {

      ProductResponse res = this.productService.findAllByCriteria(pageable, filter);

      return new MyResponse(true, "Get all product successful", 200, res);
   }

   @GetMapping("/search")
   public MyResponse getMethodName(@RequestParam(name = "q", required = true) String q) {
      return this.productService.search(q);
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
   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public MyResponse delete(
         @PathVariable Long productId) {

      this.productService.delete(productId);
      return new MyResponse(true, "Delete product successful", 200);

   }

   @PostMapping("/json-import")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public MyResponse importJson(@RequestBody JsonProductDto json) {

      Product newProduct = this.importProductService.jsonImport(json);
      ProductDTO newProductDTO = this.productToProductDto.convert(newProduct);

      return new MyResponse(true, "Import product successful", 200, newProductDTO);
   }
}
