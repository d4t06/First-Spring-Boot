package com.example.demo.product.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.example.demo.category.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(name = "product_unique", columnNames = "product_ascii")
})
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 100)
    private Long id;

    @Column(nullable = false)
    private String product_name;

    @Column(nullable = false, name = "product_ascii")
    private String productAscii;

    @Column(nullable = false)
    private String brand_ascii;

    @Column(nullable = false)
    private String category_ascii;

    @ManyToOne()
    @JoinColumn(name = "category_ascii",
     insertable = false,
     updatable = false,
     referencedColumnName = "category_ascii")
    private Category category;

    private String image_url;   
    private String old_price;
    private int cur_price;
    private String installment;

    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;
}