package com.example.teaorder.order.dto;

public record OrderResponse(
        // 주문 조회용 DTO, 응답

        Long orderId,
        Long productId,
        String productName,     // 주문 조회시 상품명이 함께 나와야 함
        Integer quantity
) {
}
