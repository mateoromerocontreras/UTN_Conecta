## Phase 1 — Backend Guardrails & Technical Debt Elimination

### Immediate Tasks (Active Milestone)
- [x] **Milestone 1: Generic Response Envelope**
  - [x] Implement `ApiResponse<T>` record in `com.seminario.pasantias.response`.
  - [x] Ensure 100% JSON contract compatibility (`codigo`, `mensaje`, `data`) with frontend and tests.
- [x] **Milestone 2: Domain Exception Hierarchy**
  - [x] Implement `ResourceNotFoundException` (HTTP 404).
  - [x] Implement `BadRequestException` (HTTP 400).
  - [x] Implement `ForbiddenOperationException` (HTTP 403).
- [x] **Milestone 3: Centralized Global Exception Handler**
  - [x] Create `GlobalExceptionHandler` with `@RestControllerAdvice`.
  - [x] Map `SecurityException` / `AccessDeniedException` -> 403 Forbidden.
  - [x] Map `IllegalArgumentException` / `BadRequestException` -> 400 Bad Request.
  - [x] Map `ResourceNotFoundException` -> 404 Not Found.
  - [x] Map `MethodArgumentNotValidException` -> 400 Bad Request with field errors.
  - [x] Map general fallback `Exception` -> 500 Internal Server Error.
  - [x] Implement and pass unit test suite (`GlobalExceptionHandlerTest`: 5/5 tests passing).
- [ ] **Milestone 4: Refactor `PasantiaController` (Case Study)**
  - [ ] Remove manual `try-catch` blocks and `Map<String, Object>` construction.
  - [ ] Update endpoint signatures to `ResponseEntity<ApiResponse<T>>` (or direct DTOs for public GETs).
  - [ ] Verify using `./mvnw test -Dtest=PasantiasIT` (all TS-01..TS-06 tests must pass).
- [ ] **Milestone 5: Practice — Refactor `PostulacionController`**
  - [ ] Apply the same pattern to `PostulacionController`.
  - [ ] Verify using `./mvnw test -Dtest=PostulacionServiceTest,PostulacionServiceIT,PasantiasIT`.

---

## 🏗️ High-Level Project Roadmap

### Phase 0: Baseline Stabilization
- [x] Verify Java 25 and Maven wrapper environment (`BUILD SUCCESS`).
- [ ] Clean up phantom docs referenced in `README.md` (`SECURITY.md`, `ENDPOINTS_SECURITY.md`).
- [ ] Setup GitHub Actions CI workflow (`mvn test` and `npm run build`).

### Phase 1: Backend Foundation & Testing Safety Net
- [ ] **API Standardization & Error Handling** (Milestones 1–5 above).
- [ ] **Security & Route Alignment**:
  - [ ] Reconcile path prefixes (`/pasantias` vs `/api/pasantias`) in `SecurityConfig.java`.
  - [ ] Consolidate role checks and method security (`@PreAuthorize`).
- [ ] **Database Migrations (Flyway)**:
  - [ ] Add `flyway-core` and `flyway-mysql` dependencies to `pom.xml`.
  - [ ] Migrate raw schema dump into versioned `V1__init_schema.sql`.
- [ ] **Business Logic Tests**:
  - [ ] Write unit test suite for `PasantiaService` state machine (`PENDIENTE_DE_APROBACION -> PUBLICADA -> FINALIZADA`).

### Phase 2: Frontend Modernization & Centralization
- [ ] Centralized API client (`frontend/src/api/client.js`) with automatic JWT interceptor and 401 handling.
- [ ] `AuthContext` & `useAuth` hook with protected route wrapper (`<ProtectedRoute />`).
- [ ] Install and configure Vitest + React Testing Library.
- [ ] Add unit and component tests for core authentication and application flows.

### Phase 3: Living Documentation & API Contract
- [ ] Enrich OpenAPI / Swagger documentation annotations on all refactored controllers.
- [ ] Update root `README.md` with verified quickstart instructions and architecture diagrams.

### Phase 4: Job Board Feature Extensions
- [ ] ATS (Applicant Tracking System) candidate pipeline for companies (`Postulado` -> `En Revisión` -> `Entrevista` -> `Oferta`).
- [ ] Student CV upload and PDF preview.
- [ ] Enhanced job search & multi-facet filtering (remote, career, salary range).

---

## 📝 Session Log & Notes

- **2026-09-05**:
  - Environment verified: Java 25 (Temurin-25.0.4+7) + Maven 3.9 wrapper. Baseline tests passed (`EstudianteServiceTest`: 19/19 passed).
  - Drafted comprehensive 4-phase technical debt reduction roadmap and tutor implementation plan.
  - Implemented `ApiResponse<T>` record in `com.seminario.pasantias.response`.
  - Implemented domain exceptions (`ResourceNotFoundException`, `BadRequestException`, `ForbiddenOperationException`) in `com.seminario.pasantias.exception`.
  - Implemented `GlobalExceptionHandler` (`@RestControllerAdvice`).
  - Implemented `GlobalExceptionHandlerTest` (5/5 unit tests passed).
- **2026-09-06**:
  - Registered `springboot_tutor` subagent based on `.agents/agents/springboot_tutor/agent.md`.
  - Initialized `ROADMAP.md` as the single source of truth and state tracking file.
