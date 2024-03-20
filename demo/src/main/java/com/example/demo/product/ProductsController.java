package com.example.demo.product;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.product.converter.ProductToProductDto;
import com.example.demo.product.dto.ProductDTO;
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
    public MyResponse findAll() {
        List<Product> products = this.productService.findAll();

        List<ProductDTO> productsDTO = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = this.productToProductDto.convert(product);
            productsDTO.add(productDTO);
        }
        return new MyResponse(true, "Get all product successful", 200, productsDTO);
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
