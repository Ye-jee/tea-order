package com.example.teaorder.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 페이지네이션, N+1 방지
    @EntityGraph(attributePaths = "product")
    Page<Order> findAll(Pageable pageable);
}
