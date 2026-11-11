# Civic Pulse Backend & Database Service

The Civic Pulse backend service provides secure REST APIs, role-based authorization, grievance tracking workflows, and PostgreSQL database models via Prisma ORM.

## Tech Stack
- **Runtime:** Node.js (v20+)
- **Language:** TypeScript
- **Framework:** Express.js
- **Database:** PostgreSQL
- **ORM:** Prisma
- **Security:** Helmet, CORS, JWT authentication, Bcrypt password hashing

## Getting Started

### 1. Install Dependencies
```bash
npm install
```

### 2. Configure Environment Variables
Copy `.env.example` to `.env` and configure your database connection:
```bash
cp .env.example .env
```

### 3. Run Prisma Migrations
```bash
npm run prisma:generate
npm run prisma:migrate
```

### 4. Start Development Server
```bash
npm run dev
```

The server will listen at `http://localhost:5000`.

## API Endpoints
- `POST /api/v1/auth/login` - Citizen/official sign in
- `POST /api/v1/auth/register` - New citizen registration
- `GET /api/v1/complaints` - List complaints with category & status filters
- `POST /api/v1/complaints` - Submit a new grievance
- `PATCH /api/v1/complaints/:id/status` - Municipal official status update
- `GET /api/v1/projects` - Public works and infrastructure projects
- `GET /api/v1/representatives` - Elected officials and SLA metrics
- `POST /api/v1/assistant/query` - Civic assistant AI inquiries
