# Vendue - Live eAuction Backend 🔨

Welcome to the backend repository for **Vendue**, a real-time, premium eCommerce auction platform. This robust backend is built with modern Java ecosystem technologies, focused on high-concurrency bidding, seamless real-time websocket streams, and a secure stateless architecture.

---

## 🚀 Key Features

* **High-Concurrency Bidding System:** Uses **Java Virtual Threads** (`spring.threads.virtual.enabled=true`) combined with `CompletableFuture.runAsync` to process bid requests without blocking the container threads, ensuring maximum throughput during intense live auctions.
* **Real-Time Data Streams:** Leverages **STOMP over WebSockets** to broadcast successful bids globally (`/main/bid/response` and `/main/admin/bids`), instantly updating all connected clients and the Admin Dashboard.
* **Stateless JWT Security:** Implements rigorous Spring Security with JWT (JSON Web Tokens). Includes custom `JwtWebSocketInterceptor` to authorize and secure WebSocket subscriptions.
* **Role-Based Access Control (RBAC):** Distinct roles (`BUYER`, `SELLER`, `ADMIN`) securely control API access at the controller layer and strictly prevent buyers from creating listings or admins from bidding.
* **Dynamic CORS Configuration:** Easily manage cross-origin request environments via the `app.cors.allowed-origins` property injected directly into controllers and security configurations.
* **PostgreSQL Persistence:** Relational data integrity enforced via Hibernate/JPA connected to a PostgreSQL database.

---

## 🏗️ Technology Stack

* **Core Framework:** Spring Boot (Java)
* **Database:** PostgreSQL & Hibernate/JPA
* **Security:** Spring Security & JWT (io.jsonwebtoken)
* **Real-Time:** Spring WebSocket Message Broker (STOMP)
* **Build Tool:** Gradle

---

## ⚙️ Configuration (`application.properties`)

Ensure your environment is correctly configured before starting the application. A sample `application.properties` setup requires:

```properties
spring.application.name=e-auction-backend
server.port=19090

# Database Configuration
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://<YOUR_DB_HOST>:5432/app_db
spring.datasource.username=<YOUR_DB_USERNAME>
spring.datasource.password=<YOUR_DB_PASSWORD>

# JPA & Hibernate
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update

# Security & Secrets
jwt.secret=<YOUR_BASE64_ENCODED_JWT_SECRET>
app.cors.allowed-origins=http://localhost:3000,http://localhost:4200

# Concurrency
spring.threads.virtual.enabled=true
```

---

## 🚦 Getting Started

### Prerequisites
- **Java 21+** (Required for Virtual Threads support)
- **PostgreSQL** running locally or remotely
- **Gradle** (or use the provided Gradle wrapper)

### Running the Application

1. Clone the repository and navigate into the backend directory.
2. Ensure your database is running and `application.properties` is configured correctly.
3. Start the Spring Boot application using Gradle:

```bash
./gradlew bootRun
```

The server will start on port `19090` (unless configured otherwise).

---

## 🔌 API Overview

### Public Endpoints (`/api/pre-auth/**`)
- `POST /api/pre-auth/user/register` - Create a new user (`BUYER` or `SELLER`).
- `POST /api/pre-auth/user/login` - Authenticate and receive a JWT.
- `GET /api/pre-auth/listing/all` - Fetch all active and closed listings.
- `GET /api/pre-auth/categories/all` - Fetch all auction categories.

### Protected Endpoints (`/api/**`)
- **Headers Required:** `Authorization: Bearer <JWT_TOKEN>`
- `GET /api/listing/all` - Authenticated listing retrieval.
- `POST /api/listing/save` - Create a new auction listing (`SELLER` or `ADMIN` only).
- `POST /api/bid/place` - Place a bid (via REST, though websockets are preferred).

### WebSocket Endpoints (`ws://localhost:19090/websoc`)
- **Connection Header:** `Authorization: Bearer <JWT_TOKEN>`
- **Publish Bid:** Send `BidDTO` to `/auc/websoc/biding`
- **Subscribe (Global Bids):** Listen to `/main/bid/response`
- **Subscribe (Admin Dashboard):** Listen to `/main/admin/bids`
- **Subscribe (Personal Errors):** Listen to `/user/queue/errors`

---

## 🛡️ Architecture & Flow

1. **The Bid Request:** A user submits a bid via WebSocket. 
2. **Asynchronous Processing:** The `WebSocketController` delegates the bid validation and database operations to a `CompletableFuture` running on a Virtual Thread.
3. **Validation (`BidServiceImpl`):** The system checks minimum increments, auction start/end times, and verifies the user is not an Admin.
4. **Broadcast:** If successful, the new bid state is broadcasted back over STOMP to all active marketplace clients and the Admin Dashboard simultaneously.

---

Made with ☕ and Virtual Threads.
