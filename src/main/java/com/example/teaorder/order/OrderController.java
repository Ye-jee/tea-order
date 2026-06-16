package com.example.teaorder.order;

import com.example.teaorder.order.dto.OrderCreateRequest;
import com.example.teaorder.order.dto.OrderResponse;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    // 주문 생성
    @PostMapping
    public OrderResponse createOrder(
            @Valid @RequestBody OrderCreateRequest request
    ){
        return orderService.createOrder(request);
    }

    // 주문 단건 조회
    @GetMapping("/{orderId}")
    public OrderResponse getOrder(
            @PathVariable Long orderId
    ){
        return orderService.getOrder(orderId);
    }

    // 주문 목록 조회
    @GetMapping
    public Page<OrderResponse> getOrders(Pageable pageable) {
        return orderService.getOrders(pageable);
    }
}
