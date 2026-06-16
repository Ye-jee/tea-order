package com.example.teaorder.product;

import com.example.teaorder.product.dto.ProductCreateRequest;
import com.example.teaorder.product.dto.ProductResponse;
import com.example.teaorder.product.dto.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {       // 상품 CRUD 로직, Controller에서 받은 요청 처리

    private final ProductRepository productRepository;

    // 생성(Create)
    public ProductResponse createProduct(ProductCreateRequest request){
        Product product = Product.builder()
                .name(request.name())
                .price(request.price())
                .stock(request.stock())
                .build();

        Product savedPrdocut = productRepository.save(product);

        return ProductResponse.from(savedPrdocut);
    }

    // 조회(Read) 1 - 단건
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long productId){
        Product product = findProduct(productId);       // productRepository 활용해서 상품을 탐색

        return ProductResponse.from(product);
    }

    // 조회(Read) 2 - 목록
    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts(){
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    // 수정(Update)
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request){
        Product product = findProduct(productId);

        product.update(
                request.name(),
                request.price(),
                request.stock()
        );

        return ProductResponse.from(product);
    }

    // 삭제(Delete)
    public void deleteProduct(Long productId) {
        Product product = findProduct(productId);

        productRepository.delete(product);
    }


    // 공통 메서드 - 상품 ID로 상품을 찾고 없으며 예외 발생
    private Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }
}
