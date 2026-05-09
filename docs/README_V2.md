# 🚀 Ecommerce System — V2 (Future Upgrades)

> Advanced features planned after V1 core system is stable and complete.

---

## 🏗️ Target Architecture (V2)

```
Client
  │
  ▼
API Gateway (Spring Cloud Gateway)
  │
  ├──▶ Order Service        ──▶ Kafka (order.created event)
  │                                    │
  ├──▶ Inventory Service    ◀──────────┘ (consumes event)
  │
  ├──▶ Payment Service      ──▶ Kafka (payment.processed event)
  │
  ├──▶ Notification Service ◀── Kafka (listens to all events)
  │
  └──▶ User Service
            │
            └──▶ Redis (session/token cache)

All services register with:
  └──▶ Eureka Service Discovery
```

---

## 📦 Planned New Services

| Service | Port | Purpose |
|---|---|---|
| API Gateway | `8080` | Single entry point, routing, auth filter |
| Eureka Server | `8761` | Service discovery & registration |
| User Service | `8084` | Auth, JWT token generation |
| Notification Service | `8085` | Email/SMS on order events |
| Payment Service | `8083` | Payment processing |

---

## ⚙️ Advanced Features Planned

### 🔀 Event-Driven Architecture (Kafka)
- `order.created` event → triggers inventory deduction
- `payment.processed` event → triggers order status update
- `order.failed` event → triggers inventory rollback

### 🔐 Security Upgrades
- JWT-based authentication
- Role-based access control (RBAC)
- API Gateway auth filter

### 📊 Observability
- Distributed tracing (Micrometer + Zipkin)
- Centralized logging (ELK Stack)
- Metrics dashboard (Prometheus + Grafana)

### 🐳 DevOps
- Docker + Docker Compose for all services
- Kubernetes deployment configs
- CI/CD pipeline (GitHub Actions)

### ⚡ Performance
- Redis caching for inventory stock checks
- Database connection pooling tuning
- Async processing with CompletableFuture

---

## 🧰 Additional Tech Stack (V2)

| Technology | Purpose |
|---|---|
| Apache Kafka | Async event messaging |
| Redis | Caching |
| Spring Cloud Gateway | API Gateway |
| Spring Security + JWT | Authentication |
| Eureka | Service discovery |
| Docker / Kubernetes | Containerization |
| Zipkin | Distributed tracing |
| Prometheus + Grafana | Monitoring |
| GitHub Actions | CI/CD |

---

## 📅 V2 Milestone Plan

| Milestone | Description |
|---|---|
| M1 | Add Docker support to all V1 services |
| M2 | Setup Eureka service discovery |
| M3 | Setup API Gateway with routing |
| M4 | Add Kafka event bus (order → inventory) |
| M5 | Add JWT auth + User Service |
| M6 | Add Redis caching to inventory |
| M7 | Add Notification Service |
| M8 | Add observability (tracing + metrics) |
| M9 | Full CI/CD pipeline |
| M10 | Kubernetes deployment |

---

> ⚠️ V2 development begins only after V1 core system is fully stable, tested, and documented.
