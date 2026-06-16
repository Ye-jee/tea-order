package com.example.teaorder.product.dto;

import com.example.teaorder.product.Product;

public record ProductResponse(
        // 상품 조회용 DTO, 응답

        Long id,
        String name,
        Integer price,
        Integer stock

) {

    // Product 엔티티를 상품 조회 응답 DTO로 변환
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }
}
