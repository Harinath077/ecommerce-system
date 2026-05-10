# 📅 DAY 3 — Progress Summary
**Date:** 10 May 2026  
**Project:** ecommerce-system (Microservices)

---

## ✅ What Was Completed Today

---

### 1. 🗃️ Inventory Service — Entity & Repository Layer

| Task | File | Detail |
|---|---|---|
| Created `Inventory.java` entity | `entity/Inventory.java` | `@Entity` with `id`, `productId`, `availableQuantity`, `reservedQuantity`, `createdAt`, `updatedAt` |
| Created `InventoryRepository.java` | `repository/InventoryRepository.java` | Extends `JpaRepository<Inventory, Long>` with `findByProductId()` custom query |
| Fixed unused import | `entity/Inventory.java` | Removed stray `import jakarta.persistence.criteria.CriteriaBuilder` |

---

### 2. 📁 Updated Project Structure

```
ecommerce-system/
├── order-service/                  ✅ Complete (entity, repo, service, controller, tests)
│   └── src/main/java/
│       └── com/ecommerce/order_service/
│           ├── OrderServiceApplication.java
│           ├── controller/OrderController.java
│           ├── dto/CreateOrderRequest.java
│           ├── dto/OrderResponse.java
│           ├── entity/Order.java
│           ├── enums/OrderStatus.java
│           ├── exception/DuplicateOrderException.java
│           ├── exception/GlobalExceptionHandler.java
│           ├── exception/OrderNotFoundException.java
│           ├── repository/OrderRepository.java
│           ├── service/OrderService.java
│           └── service/OrderServiceImpl.java
│
├── inventory-service/              🔄 Entity & Repo done — Service & Controller pending
│   └── src/main/java/
│       └── com/ecommerce/inventory_service/
│           ├── InventoryServiceApplication.java
│           ├── entity/Inventory.java             ✅ NEW
│           └── repository/InventoryRepository.java ✅ NEW
│
├── docs/
│   ├── README_V1.md               ✅ Core roadmap
│   ├── README_V2.md               ✅ Future upgrades plan
│   ├── DAY2.md                    ✅ Day 2 summary
│   └── DAY3.md                    ✅ This file
│
├── README.md
└── .gitignore
```

---

### 3. 📤 GitHub — Commit History

```
<day3-hash>  Day 3: Add Inventory entity, repository, and Day 3 docs
06b8a05      Fix: Correct Order.id field casing and replace invalid test dependencies
1ed0493      Docs: Added V1 core roadmap and V2 future upgrades documentation
acf5dd9      Security: Replace hardcoded DB credentials with environment variables
c277818      Day 2: Added inventory service foundation
1972df8      Day 1: Setup order service foundation and APIs
1f09daa      Initial project setup
```

---

## 🔲 Pending for Next Session (DAY 4)

| Task | Service |
|---|---|
| Create `service/InventoryService.java` (interface) | inventory-service |
| Create `service/InventoryServiceImpl.java` | inventory-service |
| Create `dto/InventoryRequest.java` & `InventoryResponse.java` | inventory-service |
| Create `controller/InventoryController.java` | inventory-service |
| Create `exception/` classes (InventoryNotFoundException, etc.) | inventory-service |
| Write unit tests for InventoryServiceImpl | inventory-service |
| Add `@PrePersist` / `@PreUpdate` lifecycle hooks in `Inventory.java` | inventory-service |

---

## 🧰 Tech Stack Confirmed Working

| Technology | Version | Status |
|---|---|---|
| Java | 21.0.1 | ✅ Running |
| Spring Boot | 4.0.6 | ✅ Running |
| PostgreSQL | 18.2 | ✅ Connected |
| Hibernate ORM | 7.2.12.Final | ✅ Active |
| Tomcat | 11.0.21 | ✅ Running |
| Lombok | Latest | ✅ Configured |
| Maven | 3.x | ✅ Working |
