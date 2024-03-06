package com.example.demo.category.exception;

public class CategoryNotFoundException extends RuntimeException {
    public  CategoryNotFoundException (String category_ascii) {
        super("Could not found category with category_ascii: " + category_ascii);
    }
}  
