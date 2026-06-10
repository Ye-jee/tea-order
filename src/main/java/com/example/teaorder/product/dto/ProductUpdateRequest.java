package com.example.teaorder.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductUpdateRequest(
        // 상품 수정용 DTO, 사용자 요청

        @NotBlank       // 상품명 -> 빈 문자열 금지
        String name,

        @Positive       // 가격 -> 1 이상의 값 허용
        Integer price,

        @PositiveOrZero     // 재고 -> 0 이상 허용
        Integer stock
) {
}
