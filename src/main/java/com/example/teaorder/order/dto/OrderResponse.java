package com.example.teaorder.order.dto;

import com.example.teaorder.order.Order;

public record OrderResponse(
        // 주문 조회용 DTO, 응답

        Long orderId,
        Long productId,
        String productName,     // 주문 조회시 상품명이 함께 나와야 함
        Integer quantity
) {

    // Order 엔티티를 주문 조회 응답 DTO로 변환
    public static OrderResponse from(Order order){
        return new OrderResponse(
                order.getId(),
                order.getProduct().getId(),
                order.getProduct().getName(),
                order.getQuantity()
        );
    }
}
