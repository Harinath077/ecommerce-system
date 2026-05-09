# 🛒 Ecommerce System — V1 (Core Build Roadmap)

> Current active development phase. Building the core microservices foundation.

---

## 🏗️ Architecture Overview

```
Client
  │
  ├──▶ Order Service (Port: 8081)
  │         │
  │         └──▶ PostgreSQL (order_db)
  │
  └──▶ Inventory Service (Port: 8082)
            │
            └──▶ PostgreSQL (inventory_db)
```

---

## ✅ Services Being Built

### 1. Order Service
- **Port:** `8081`
- **Database:** `order_db` (PostgreSQL)
- **Status:** ✅ Foundation complete

| Feature | Status |
|---|---|
| Project setup (Spring Initializr) | ✅ Done |
| Entity (`Order`) | ✅ Done |
| Repository (`OrderRepository`) | ✅ Done |
| Service Interface + Impl | ✅ Done |
| REST Controller (POST + GET) | ✅ Done |
| Idempotency key (duplicate prevention) | ✅ Done |
| Exception handling (Global Handler) | ✅ Done |
| Unit Tests | ✅ Done |
| DB credentials secured via env vars | ✅ Done |

---

### 2. Inventory Service
- **Port:** `8082`
- **Database:** `inventory_db` (PostgreSQL)
- **Status:** 🔄 In Progress

| Feature | Status |
|---|---|
| Project setup (Spring Initializr) | ✅ Done |
| Entity (`Inventory`) | 🔲 Pending |
| Repository (`InventoryRepository`) | 🔲 Pending |
| Service Interface + Impl | 🔲 Pending |
| REST Controller | 🔲 Pending |
| Exception handling | 🔲 Pending |
| Unit Tests | 🔲 Pending |

---

### 3. Payment Service
- **Port:** `8083` *(planned)*
- **Status:** 🔲 Not started

---

## 🧰 Tech Stack (V1)

| Technology | Purpose |
|---|---|
| Java 21 | Language |
| Spring Boot 4.0.6 | Framework |
| Spring Data JPA | ORM |
| PostgreSQL | Database |
| Lombok | Boilerplate reduction |
| Maven | Build tool |
| Spring Actuator | Health monitoring |
| Spring Validation | Request validation |

---

## 📅 Commit Strategy

| Day | Commit | Description |
|---|---|---|
| — | `Initial project setup` | README + .gitignore |
| Day 1 | `Day 1: Setup order service foundation and APIs` | Full order-service |
| Day 2 | `Day 2: Added inventory service foundation` | Inventory base structure |
| Day 3 | *(upcoming)* | Inventory entity, repo, service, controller |
| Day 4 | *(upcoming)* | Payment service foundation |

---

## 🔐 Security

- All database credentials are managed via environment variables
- No hardcoded secrets in codebase
- Pattern: `${DB_PASSWORD:localFallback}`
