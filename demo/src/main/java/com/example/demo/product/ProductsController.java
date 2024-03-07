package com.example.demo.product;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.demo.product.dto.ProductDTO;
import com.example.demo.product.entity.Product;
import com.example.demo.system.MyResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public MyResponse findAll() {
        List<Product> products = this.productService.findAll();

        return new MyResponse(true, "Get all product successful", 200, products);
    }

    @GetMapping("/{product_ascii}")
    public MyResponse findOne(@PathVariable String product_ascii) {
        Product product = this.productService.findOne(product_ascii);

        return new MyResponse(true, "Get one product successful", 200, product);
    }

    @PostMapping()
    public MyResponse create(@Valid @RequestBody ProductDTO createProductDTO) {
        Product product = this.productService.create(createProductDTO);

        return new MyResponse(true, "Add product successful", HttpStatus.OK.value(), product);
    }

    @PutMapping("/{id}")
    public MyResponse update(
            @PathVariable Long id,
            @RequestBody ProductDTO updateDto) {

        this.productService.update(id, updateDto);

        return new MyResponse(true, "Update product successful", 200);
    }

    @DeleteMapping("/{id}")
    public MyResponse delete(
            @PathVariable Long id) {

        this.productService.delete(id);
        return new MyResponse(true, "Delete product successful", 200);

    }
}
