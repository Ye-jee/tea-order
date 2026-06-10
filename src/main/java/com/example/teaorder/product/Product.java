package com.example.teaorder.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {          // 상품

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 상품명
    private String name;

    // 상품 가격
    private Integer price;

    // 상품 재고
    private Integer stock;

}
