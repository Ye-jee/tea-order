package com.example.teaorder.product.dto;

public record ProductResponse(
        // 상품 조회용 DTO, 응답

        Long id,
        String name,
        Integer price,
        Integer stock

) {
}
