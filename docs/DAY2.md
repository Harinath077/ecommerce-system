# 📅 DAY 2 — Progress Summary
**Date:** 09 May 2026  
**Project:** ecommerce-system (Microservices)

---

## ✅ What Was Completed Today

---

### 1. 🛠️ Order Service — Bugs Fixed & Code Cleaned

| Fix | File | Detail |
|---|---|---|
| Fixed field naming bug | `Order.java` | `private Long Id` → `private Long id` (lowercase) |
| Fixed test builder calls | `OrderServiceImplTest.java` | `.Id(1L)` → `.id(1L)` (3 places) |
| Removed unused imports | `OrderController.java` | Removed dead `Order` and `OrderRepository` imports |
| Fixed typo in exception message | `OrderServiceImpl.java` | `"requset"` → `"request"` |
| Added missing app name | `application.yaml` | Added `spring.application.name: order-service` |
| Replaced fake test dependencies | `pom.xml` | 4 non-existent `*-test` starters → `spring-boot-starter-test` |
| Secured DB credentials | `application.yaml` | Hardcoded `password: AdminRoot` → `${DB_PASSWORD:AdminRoot}` |

---

### 2. 🏗️ Inventory Service — Foundation Setup

| Task | Status | Detail |
|---|---|---|
| Generated via Spring Initializr | ✅ Done | Same tech stack as order-service |
| `pom.xml` validated | ✅ Done | Java 21, Spring Boot 4.0.6, correct dependencies |
| `application.yaml` configured | ✅ Done | Port `8082`, `inventory_db`, DB credentials via env vars |
| Replaced fake test dependencies | ✅ Done | `spring-boot-starter-test` only |
| App runs successfully | ✅ Done | `Started InventoryServiceApplication in ~3.5s` |
| DB connected | ✅ Done | HikariPool connected to `inventory_db` (PostgreSQL 18.2) |

---

### 3. 📁 Project Structure

```
ecommerce-system/
├── order-service/                  ✅ Complete foundation
│   ├── src/main/java/
│   │   └── com/ecommerce/order_service/
│   │       ├── OrderServiceApplication.java
│   │       ├── controller/OrderController.java
│   │       ├── dto/CreateOrderRequest.java
│   │       ├── dto/OrderResponse.java
│   │       ├── entity/Order.java
│   │       ├── enums/OrderStatus.java
│   │       ├── exception/DuplicateOrderException.java
│   │       ├── exception/GlobalExceptionHandler.java
│   │       ├── exception/OrderNotFoundException.java
│   │       ├── repository/OrderRepository.java
│   │       ├── service/OrderService.java
│   │       └── service/OrderServiceImpl.java
│   └── src/main/resources/application.yaml
│
├── inventory-service/              🔄 Foundation done, APIs pending
│   ├── src/main/java/
│   │   └── com/ecommerce/inventory_service/
│   │       └── InventoryServiceApplication.java
│   └── src/main/resources/application.yaml
│
├── docs/
│   ├── README_V1.md               ✅ Core roadmap
│   ├── README_V2.md               ✅ Future upgrades plan
│   └── DAY2.md                    ✅ This file
│
├── README.md
└── .gitignore
```

---

### 4. 🔐 Security

| Action | Status |
|---|---|
| Removed hardcoded `username: postgres` from both services | ✅ Done |
| Removed hardcoded `password: AdminRoot` from both services | ✅ Done |
| Replaced with `${DB_USERNAME:postgres}` / `${DB_PASSWORD:AdminRoot}` pattern | ✅ Done |

---

### 5. 📤 GitHub — Commit History

```
06b8a05  Fix: Correct Order.id field casing and replace invalid test dependencies
1ed0493  Docs: Added V1 core roadmap and V2 future upgrades documentation
acf5dd9  Security: Replace hardcoded DB credentials with environment variables
c277818  Day 2: Added inventory service foundation
1972df8  Day 1: Setup order service foundation and APIs
1f09daa  Initial project setup
```

---

## 🔲 Pending for Next Session (DAY 3)

| Task | Service |
|---|---|
| Create `entity/Inventory.java` | inventory-service |
| Create `repository/InventoryRepository.java` | inventory-service |
| Create `service/InventoryService.java` (interface) | inventory-service |
| Create `service/InventoryServiceImpl.java` | inventory-service |
| Create `controller/InventoryController.java` | inventory-service |
| Create `exception/` classes | inventory-service |
| Write unit tests | inventory-service |
| Fix Maven/IntelliJ sync (blue icon) | inventory-service |

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
