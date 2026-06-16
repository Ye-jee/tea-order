package com.example.teaorder.product;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Product(String name, Integer price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void update(String name, Integer price, Integer stock){
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // 재고 차감 메서드
    public void decreaseStock(Integer quantity) {
        if(this.stock < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }

        this.stock -= quantity;
    }
}
