package com.example.demo.product;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.product.converter.ProductToProductDto;
import com.example.demo.product.dto.ProductDTO;
import com.example.demo.product.dto.ProductResponse;
import com.example.demo.product.entity.Product;
import com.example.demo.system.MyResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;
    private final ProductToProductDto productToProductDto;

    public ProductsController(
            ProductService productService,
            ProductToProductDto productToProductDto) {
        this.productService = productService;
        this.productToProductDto = productToProductDto;
    }

    @GetMapping()
    public MyResponse findAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "brandID", required = false) List<String> brandID,
            @RequestParam(value = "pageSize", required = false, defaultValue = "2") int pageSize,
            @RequestParam(value = "categoryID", required = false) Integer categoryID) {

        System.out.println(">>> check params brandID: " + brandID + ", categoryID: " + categoryID);

        ProductResponse productResponse = this.productService.findAll(page, pageSize, categoryID, brandID);

        return new MyResponse(true, "Get all product successful", 200, productResponse);
    }

    @GetMapping("/search")
    public MyResponse getMethodName(
            @RequestParam(value = "q", required = true) String q,
            @RequestParam(value = "pageSize", required = false, defaultValue = "2") int pageSize,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page) {

        ProductResponse productResponse = this.productService.search(q, page, pageSize);
        return new MyResponse(true, "Get all product successful", 200, productResponse);
    }

    @GetMapping("/{product_ascii}")
    public MyResponse findOne(@PathVariable String product_ascii) {
        Product product = this.productService.findOne(product_ascii);
        ProductDTO productDTO = this.productToProductDto.convert(product);

        return new MyResponse(true, "Get one product successful", 200, productDTO);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public MyResponse create(@Valid @RequestBody ProductDTO createProductDTO) {
        Product product = this.productService.create(createProductDTO);

        return new MyResponse(true, "Add product successful", HttpStatus.OK.value(), product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public MyResponse update(
            @PathVariable Long id,
            @RequestBody ProductDTO updateDto) {

        this.productService.update(id, updateDto);

        return new MyResponse(true, "Update product successful", 200);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public MyResponse delete(
            @PathVariable Long id) {

        this.productService.delete(id);
        return new MyResponse(true, "Delete product successful", 200);

    }
}
