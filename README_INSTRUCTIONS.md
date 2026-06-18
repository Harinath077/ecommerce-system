# 📑 Ecommerce Microservices — Progress & Next Steps

This document summarizes what has been built so far in the `ecommerce-system` and provides step-by-step instructions on what you need to do next to connect the services.

---

## 🏁 Part 1: What has been done so far

### 1. Project Setup
- Structured as a monorepo with two independent microservices:
  - **`order-service`** (runs on Port `8081` with database `order_db`)
  - **`inventory-service`** (runs on Port `8082` with database `inventory_db`)
- Both services utilize **Java 21**, **Spring Boot 4.0.6**, **Spring Data JPA**, and **PostgreSQL**.

### 2. Completed Features
* **Order Service (`order-service`):**
  * `Order` entity with Lombok, JPA annotations, and `OrderStatus` enum.
  * REST API controller supporting `POST /orders` (create) and `GET /orders/{id}` (fetch).
  * Idempotency checks to prevent duplicate orders using an `idempotencyKey` lookup.
  * Global exception handling and unit tests (5/5 tests passing).
* **Inventory Service (`inventory-service`):**
  * `Inventory` entity with JPA hooks (`@PrePersist`, `@PreUpdate`) for automated creation and update timestamps.
  * REST API controller exposing `POST /inventory` (add stock), `GET /inventory/{productId}` (view stock), `POST /inventory/reserve` (reserve stock), and `POST /inventory/release` (release stock).
  * Unit tests verifying stock reservation and exception branches (9/9 tests passing).
* **Feign Client Config & Compatibility Fix:**
  * Enabled OpenFeign in `order-service` using `@EnableFeignClients` on the application entry class.
  * Upgraded `spring-cloud.version` to **`2025.1.0`** to resolve startup compatibility crashes (like `NoClassDefFoundError: WebServerInitializedEvent`) with Spring Boot 4.x.
  * Created `InventoryClient.java` (Feign interface) and `ReserveStockRequest.java` properties placeholder.

---

## 🛠️ Part 2: Step-by-Step Instructions (What to do next)

Follow these instructions to implement synchronous stock reservation when an order is created.

### Step 1: Create the Custom Exception
Create a new Java class `InventoryReservationException.java` inside `order-service/src/main/java/com/ecommerce/order_service/exception/` to encapsulate errors returned from the Feign call:

```java
package com.ecommerce.order_service.exception;

import org.springframework.http.HttpStatus;

public class InventoryReservationException extends RuntimeException {
    private final HttpStatus status;

    public InventoryReservationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
```

### Step 2: Register the Exception in the Global Handler
Add a handler method to `GlobalExceptionHandler.java` in `order-service` to cleanly catch and serialize this exception to REST clients:

```java
@ExceptionHandler(InventoryReservationException.class)
public ResponseEntity<Map<String, String>> handleInventoryReservation(
        InventoryReservationException ex) {

    Map<String, String> error = new HashMap<>();
    error.put("message", ex.getMessage());

    return new ResponseEntity<>(error, ex.getStatus());
}
```

### Step 3: Inject the Feign Client & Call It
In `OrderServiceImpl.java`:
1. Declare `private final InventoryClient inventoryClient;` as a dependency (Lombok `@RequiredArgsConstructor` will automatically inject it).
2. Inside `createOrder(CreateOrderRequest request)`, call the inventory service *before* persisting the order:

```java
// 1. Reserve stock first
try {
    inventoryClient.reserveStock(new ReserveStockRequest(
            request.getProductId(),
            request.getQuantity(),
            request.getIdempotencyKey()
    ));
} catch (FeignException ex) {
    HttpStatus status = HttpStatus.resolve(ex.status());
    if (status == null) {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    String message = ex.contentUTF8();
    if (message == null || message.trim().isEmpty()) {
        message = "Failed to reserve stock: " + ex.getMessage();
    }
    throw new InventoryReservationException(message, status);
}

// 2. Persist order once reservation is successful
Order order = Order.builder()
        .userId(request.getUserId())
        .productId(request.getProductId())
        .quantity(request.getQuantity())
        .status(OrderStatus.CREATED)
        .idempotencyKey(request.getIdempotencyKey())
        .build();

Order saveOrder = orderRepository.save(order);
```

### Step 4: Update Unit Tests
In `OrderServiceImplTest.java`:
1. Add `@Mock private InventoryClient inventoryClient;` to mock the Feign Client.
2. Update the existing test `shouldCreateOrderSuccessfully()` to stub the Feign call:
   ```java
   when(inventoryClient.reserveStock(any(ReserveStockRequest.class)))
       .thenReturn(ResponseEntity.ok().build());
   ```
3. Add a new test verification to check that when the stock reservation fails, the order is not saved:
   ```java
   @Test
   void shouldFailToCreateOrderWhenStockReservationFails() {
       CreateOrderRequest request = new CreateOrderRequest();
       request.setUserId(1L);
       request.setProductId(100L);
       request.setQuantity(2);
       request.setIdempotencyKey("abc123");

       when(orderRepository.findByIdempotencyKey("abc123"))
               .thenReturn(Optional.empty());

       feign.Response response = feign.Response.builder()
               .status(400)
               .reason("Bad Request")
               .request(feign.Request.create(feign.Request.HttpMethod.POST, "", new java.util.HashMap<>(), feign.Request.Body.empty(), null))
               .body("Insufficient stock", java.nio.charset.StandardCharsets.UTF_8)
               .build();
       feign.FeignException feignException = feign.FeignException.errorStatus("reserveStock", response);

       when(inventoryClient.reserveStock(any(ReserveStockRequest.class)))
               .thenThrow(feignException);

       InventoryReservationException exception = assertThrows(
               InventoryReservationException.class,
               () -> orderService.createOrder(request)
       );

       assertEquals("Insufficient stock", exception.getMessage());
       assertEquals(org.springframework.http.HttpStatus.BAD_REQUEST, exception.getStatus());

       verify(inventoryClient).reserveStock(any(ReserveStockRequest.class));
       verify(orderRepository, never()).save(any(Order.class));
   }
   ```

### Step 5: Test Execution
Verify all changes compile and test suites pass:
```bash
cd order-service
.\mvnw clean test
```
