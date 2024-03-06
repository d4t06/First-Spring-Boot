package com.example.demo.product;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.category.exception.CategoryNotFoundException;
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
        try {
            // return new ResponseEntity<>("GetAll Results", HttpStatus.OK);
            List<Product> products = this.productService.findAll();

            return new MyResponse(true, "Get all product successful", 200, products);

        } catch (Exception e) {
            return new MyResponse(false, "Get all product error", 500);
        }
    }

    @GetMapping("/{product_ascii}")
    public MyResponse findOne(@PathVariable String product_ascii) {
        try {
            Product product = this.productService.findOne(product_ascii);

            if (product == null)
                throw new CategoryNotFoundException("SKLDJFJSKD");
            return new MyResponse(true, "Get one product successful", 200, product);
        } catch (Exception e) {
            return new MyResponse(false, e.getMessage(), 500);
        }
    }

    @PostMapping()
    public MyResponse create(@Valid @RequestBody ProductDTO createProductDTO) {
        try {
            
            Product product = this.productService.create(createProductDTO);
            return new MyResponse(true, "Add product successful", HttpStatus.OK.value(), product);
        } catch (Exception e) {
            return new MyResponse(false, e.getMessage(), 500);
        }
    }

    @PutMapping("/{product_ascii}")
    public MyResponse update(
            @RequestParam String product_ascii) {
        try {
            this.productService.update(product_ascii);
            return new MyResponse(true, "Update product successful", 200) ;
        } catch (Exception e) {
            return new MyResponse(false, "Update product error", null);
        }
    }

    @DeleteMapping("/{product_ascii}")
    public MyResponse delete(@PathVariable String product_ascii) {
        try {
            return new MyResponse(true, "Update product successful", 200) ;
        } catch (Exception e) {
            return new MyResponse(false, "Delete product error", null);
        }
    }
}
