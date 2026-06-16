package com.example.teaorder.order;

import com.example.teaorder.order.dto.OrderCreateRequest;
import com.example.teaorder.order.dto.OrderResponse;
import com.example.teaorder.product.Product;
import com.example.teaorder.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {     // 주문 관련 비즈니스 로직 처리

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    // 주문 생성
    public OrderResponse createOrder(OrderCreateRequest request){
        // 주문을 생성하기 전에 상품이 존재하는지 확인
        Product product = productRepository.findById(request.productId())
                .orElseThrow(()-> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // 상품 재고 차감
        product.decreaseStock(request.quantity());

        // 그 다음에 Order 엔티티 생성
        Order order = Order.builder()
                .product(product)
                .quantity(request.quantity())
                .build();

        // 주문 저장
        Order savedOrder = orderRepository.save(order);

        // 응답 DTO 변환
        return OrderResponse.from(savedOrder);
    }


    // 주문 단건 조회
    public OrderResponse getOrder(Long orderId){
        // 주문을 찾고
        Order order = findOrder(orderId);

        // 응답 DTO로 변환해 반환
        return OrderResponse.from(order);
    }


    // 주문 목록 조회
    public Page<OrderResponse> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)    // Order와 Product 함께 조회 -> N+1 발생하지 않음
                .map(OrderResponse::from);          // OrderResponse로 변환
    }


    // 공통 메서드 - 주문 id로 주문을 찾고 없으면 예외 발생
    private Order findOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
    }
}
