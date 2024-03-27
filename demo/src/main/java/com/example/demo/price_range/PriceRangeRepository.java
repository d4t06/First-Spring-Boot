package com.example.demo.price_range;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.price_range.entity.PriceRange;


@Repository
public interface PriceRangeRepository extends JpaRepository<PriceRange,Long> {

}
