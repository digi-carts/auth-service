# Changelog

## [1.0.0] - 2026-08-29

### Features
- seed e2e test user account (Role.user) on startup
- add GET/PATCH /me, social/firebase login, admin stats and customer management endpoints
- add AdminMgmtController with full admin/superadmin/customer management
- add auth endpoints (login, register, refresh) and fix controller path to /api/auth
- add AuthController with login, merchant-register, refresh, admin-mgmt endpoints
- add JavaDoc, health aliases, and component tests

### Bug Fixes
- role check before validation, address default endpoint, exception handler
- role check before validation, address default endpoint, IllegalArgumentException handler
- enforce SUPERADMIN role on admin-mgmt endpoints
- use BCryptPasswordEncoder directly — no PasswordEncoder bean in context
- add DataSeeder to ensure e2e test users exist on startup
- map IllegalStateException to 409 Conflict (duplicate email on register)
- remove redundant CORS config — gateway handles CORS
- update UserServiceTest to use UUID for repository mock calls
- remove liquibase default-schema to allow fresh DB bootstrap
- update controller @RequestMapping paths to match gateway routes
- add validCheckSum=ANY and runAlways to 001-create-schema changeset
- limit HikariCP pool to 2 connections (db-f1-micro max 25 total)
- disable Hibernate validation (Liquibase owns schema, uuid vs String mismatch)
- set liquibase-schema=public so schema is created before tracking tables
- add Cloud SQL postgres-socket-factory for Cloud Run connectivity

### Documentation
- add complete project documentation

### CI/Build
- retrigger prod deploy
- retry after secret fix
- trigger fresh build after secret setup
- trigger first dev build
- use separate GCP project IDs for dev (digi-carts-dev) and prod (digi-carts)