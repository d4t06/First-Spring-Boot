package com.example.demo.product;

import java.util.List;

public record ProductFilter (
    List<String> brand_id,
    String category_id
) {}
