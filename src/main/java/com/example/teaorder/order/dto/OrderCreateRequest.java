package com.example.teaorder.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderCreateRequest(
        // 주문 생성용 DTO, 사용자 요청

        @NotNull
        Long productId,     // 상품 id로 상품 내용을 가져올 예정

        @Positive
        Integer quantity    // 수량 -> 1 이상의 값

) {
}
