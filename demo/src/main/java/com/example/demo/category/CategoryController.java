package com.example.demo.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.category.dto.CategoryDto;
import com.example.demo.category.entity.Category;
import com.example.demo.system.MyResponse;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {

        this.categoryService = categoryService;
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        try {
            // TODO Implement Your Logic To Get Data From Service Layer Or Directly From
            // Repository Layer
            // return new ResponseEntity<>("GetAll Results", HttpStatus.OK);
            List<Category> categories = this.categoryService.findAll();

            if (categories.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(categories, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{category_ascii}")
    public ResponseEntity<?> find(@PathVariable String category_ascii) {
        try {
            // TODO Implement Your Logic To Get Data From Service Layer Or Directly From
            // Repository Layer
            return this.categoryService.findOne(category_ascii);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public MyResponse create(@RequestBody CategoryDto dto) {
        try {
            // TODO Implement Your Logic To Save Data And Return Result Through
            // ResponseEntity
            Category category = this.categoryService.create(dto);
            return new MyResponse(true,"Add successfull", 200, category);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new MyResponse(false, e.getMessage(), 500);
        }
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody CategoryDto dto) {
        try {
            // TODO Implement Your Logic To Update Data And Return Result Through
            // ResponseEntity
            return new ResponseEntity<>("Update Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            // TODO Implement Your Logic To Destroy Data And Return Result Through
            // ResponseEntity
            return new ResponseEntity<>("Destroy Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
