# GETIT - P2P Rental Platform

Quickstart

- Prereqs: Java 21
- Build: `./mvnw -q -DskipTests package` or `mvn -q -DskipTests package`
- Run: `./mvnw spring-boot:run`

Default admin: admin@getit.app / Admin@123

ENV

- GETIT_DB_USER, GETIT_DB_PASSWORD
- GETIT_JWT_SECRET (32+ chars recommended)

API Highlights

- POST /api/auth/register
- POST /api/auth/login
- GET /api/items/home
- GET /api/items/search?text=&category=
- GET /api/items/nearest?lat=&lng=&radiusKm=&limit=
- POST /api/items (auth)
- PUT /api/items/admin/{id}/approve (admin)