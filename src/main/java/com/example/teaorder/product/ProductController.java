package com.example.teaorder.product;

import com.example.teaorder.product.dto.ProductCreateRequest;
import com.example.teaorder.product.dto.ProductResponse;
import com.example.teaorder.product.dto.ProductUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {    // 요청을 받아서 service로 넘기고, 결과를 응답으로 반환

    private final ProductService productService;

    // 상품 등록
    @PostMapping
    public ProductResponse createProduct(
            @Valid @RequestBody /* JSON을 Java 객체로 변환 */
            ProductCreateRequest request
    ){
        return productService.createProduct(request);
    }


    // 상품 단건 조회
    @GetMapping("/{productId}")
    public ProductResponse getProduct(
            @PathVariable Long productId
    ){
        return productService.getProduct(productId);
    }

    // 상품 목록 조회
    @GetMapping
    public List<ProductResponse> getProducts(){
        return productService.getProducts();
    }

    // 상품 수정
    @PatchMapping("/{productId}")
    public ProductResponse updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductUpdateRequest request
    ) {
        return productService.updateProduct(productId, request);
    }

    // 상품 삭제
    @DeleteMapping("/{productId}")
    public void deleteProduct(
            @PathVariable Long productId
    ){
        productService.deleteProduct(productId);
    }

}
