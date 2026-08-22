Focus Tracker

Full-stack productivity tracker (Spring Boot, React, PostgreSQL) for managing tasks and goals across CAT prep, job search, and reading — with REST APIs and a multi-layer test suite (JUnit, Pytest, Selenium).

Problem statement

Juggling CAT preparation, job search, and reading habits across scattered notes and apps makes it hard to see, at a glance, what needs attention today and how much progress is being made toward each goal. Focus Tracker centralizes this into a single dashboard: today's tasks, goal progress, a weekly timetable, and quick-access resource links — with in-dashboard notifications for anything due.

This project also serves as a hands-on learning ground for Spring Boot, React, and Selenium.

Core features
Dashboard — today's tasks, goal progress, recently completed items
Tasks — CRUD with category, due date, priority, and status
Goals — weekly/monthly targets per category with progress tracking
Weekly timetable — interactive grid to assign time blocks to categories
Links — save resources per category
In-dashboard notifications — browser alerts for today's/overdue tasks
Tech stack
Layer	Tech
Backend	Java 17, Spring Boot 3, Maven
Database	H2 (dev) / PostgreSQL (prod)
ORM	Spring Data JPA / Hibernate
Frontend	React (Vite)
Backend tests	JUnit 5, Mockito, Spring Boot Test
API tests	Pytest, requests
E2E tests	Selenium Java, JUnit
CI	GitHub Actions



Project structure
focus-tracker/
├── backend/       # Spring Boot API
├── frontend/      # React app
├── api-tests/     # Pytest API test suite
├── e2e-tests/     # Selenium Java E2E suite
└── README.md
