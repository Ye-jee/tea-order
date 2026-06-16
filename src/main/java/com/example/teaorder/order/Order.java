package com.example.teaorder.order;

import com.example.teaorder.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order {        // 주문

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 주문한 상품
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)  // 주문은 상품 없이 생성될 수 없음
    private Product product;

    // 주문 수량
    private Integer quantity;

    @Builder
    public Order(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
