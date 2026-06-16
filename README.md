# 🍵tea-order
Spring Boot와 JPA를 활용하여 티 상품(Product)과 주문(Order)을 관리하는 주문 관리 서비스

## 구현 기능

### 상품 관리 API

#### 상품 등록 예시

요청

```json
{
  "name": "제주순수녹차",
  "price": 12000,
  "stock": 20
}
```

응답

```json
{
  "id": 1,
  "name": "제주순수녹차",
  "price": 12000,
  "stock": 20
}
```

* 상품 등록
* 상품 단건 조회
* 상품 목록 조회
* 상품 수정
* 상품 삭제


### 주문 API

#### 주문 생성 예시

**Request**

```json
{
  "productId": 1,
  "quantity": 12
}
```

**Response**

```json
{
  "id": 1,
  "productId": 1,
  "productName": "제주순수녹차",
  "quantity": 12
}
```

- 주문 생성
- 주문 단건 조회
- 주문 목록 조회


---

## 재고 관리 기능

주문 생성 시 상품 재고가 주문 수량만큼 차감되도록 구현했습니다.

### 주문 생성 전 상품 재고 확인 
- 재고가 충분한 경우 주문 생성 및 재고 차감
- 재고가 부족한 경우 예외 발생

### 처리 방식

1. 주문 생성 요청 수신 (`createOrder`)

   * 클라이언트로부터 상품 ID와 주문 수량을 전달

2. 상품 조회 (`productRepository.findById`)

   * 주문 대상 상품을 조회
   * 존재하지 않는 상품인 경우 `예외를 발생`

3. 재고 확인 (`product.getStock()`)

   * 현재 재고와 주문 수량을 비교
   * 주문 수량이 재고보다 많은 경우 `예외를 발생`시켜 주문을 차단

4. 재고 차감 (`product.decreaseStock()`)

   * 재고가 충분한 경우 주문 수량만큼 재고를 차감

5. 주문 생성 및 저장 (`orderRepository.save`)

   * 주문 정보를 기반으로 주문 엔티티를 생성하고 데이터베이스에 저장

6. 트랜잭션 처리 (`@Transactional`)

   * 주문 생성과 재고 차감을 하나의 트랜잭션으로 처리
   * 처리 중 예외가 발생하면 모든 작업이 롤백되어 데이터 정합성을 보장

---

## 주문 목록 조회 및 페이지네이션

Spring Data JPA의 `Pageable`을 활용하여 주문 목록을 페이지 단위로 조회할 수 있도록 구현했습니다.

### 예시

```http
GET /orders?page=0&size=3
```

### 응답 데이터

* 주문 ID
* 상품 ID
* 상품명
* 주문 수량

```json
{
  "content": [
    {
      "orderId": 1,
      "productId": 1,
      "productName": "제주순수녹차",
      "quantity": 6
    },
    {
      "orderId": 2,
      "productId": 2,
      "productName": "제주삼다영귤티",
      "quantity": 5
    },
    {
      "orderId": 3,
      "productId": 6,
      "productName": "피치파파야블랙티",
      "quantity": 6
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 3,
    "sort": {
      ...
    },
    ...
  },
  "totalElements": 3,
  "totalPages": 1,
  "last": true,
  "size": 3,
  "number": 0,
  "sort": {
    ...
  },
  "numberOfElements": 3,
  "first": true,
  "empty": false
}
```


---

## N+1 문제 해결

주문 목록 조회 시 주문마다 상품 정보를 개별 조회하는 N+1 문제가 발생하지 않도록 개선했습니다.

### N+1 문제 해결을 위한 적용 방법

주문 목록을 조회할 때 `Order` 엔티티와 연관된 `Product` 엔티티의 정보를 함께 사용하고 있습니다. 기본적으로 `@ManyToOne(fetch = FetchType.LAZY)` 설정에서는 주문 데이터를 조회한 후, 각 주문마다 상품 정보를 추가로 조회하는 쿼리가 실행될 수 있습니다. 이러한 현상을 N+1 문제라고 하며, 조회 대상 데이터가 많아질수록 성능 저하가 발생할 수 있습니다.

이를 해결하기 위해 Spring Data JPA의 `@EntityGraph`를 적용했습니다.

```java
@EntityGraph(attributePaths = "product")
Page<Order> findAll(Pageable pageable);
```

`attributePaths = "product"` 설정을 통해 주문 조회 시 연관된 상품 정보도 함께 조회하도록 지정했습니다. 그 결과 JPA가 내부적으로 JOIN 쿼리를 생성하여 주문과 상품 데이터를 한 번의 조회로 가져옵니다.


실행 시에는 아래와 같이 주문과 상품 정보를 함께 조회하는 SQL이 생성됩니다.


### 실행되는 쿼리

```sql
select
    o.id,
    o.product_id,
    p.id,
    p.name,
    p.price,
    p.stock,
    o.quantity
from orders o
join products p
    on p.id = o.product_id
```


